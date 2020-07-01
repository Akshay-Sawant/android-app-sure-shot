package com.sureshots.app.network

/*import com.crashlytics.android.Crashlytics;*/
import com.sureshots.app.model.response.APIErrorResponse
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource

import java.io.IOException

/**
 * Created by Innovins-21 on 028, 28-Jun-18, 4:36 PM.
 * @author Innovins-21
 * @version 2.0.0
 * v1.0.0 - created error interceptor on 028, 28-Jun-18, 4:36 PM.
 * v2.0.0 - converted error interceptor to Kotlin on 031, 31-Jan-19, 1:02 PM
 */

internal class ErrorInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)

        if (response.isSuccessful) {
            return response
        } else if (response.code == 500) { // 500 Internal Server Error
            /**
             * A generic error message, given when an unexpected condition was
             * encountered and no more specific message is suitable.
             */
            /* mContext.getApplicationContext().startActivity(new Intent(mContext.getApplicationContext(),
                        ServerIsBrokenActivity.class)); */
            // 028, 28-Jun-18 log error, server crashed
            // 010, 10-Jul-18 done
            // 028, 28-Sep-18 updated to throw ServerInvalidResponseException.ERROR_500_RESPONSE
            val e = ServerInvalidResponseException(ServerInvalidResponseException.ERROR_500_RESPONSE)
            ErrorUtils.logNetworkError(ServerInvalidResponseException.ERROR_500_RESPONSE +
                                            " \nRequest: " + request.toString() +
                                            "\nResponse: " + response.toString(), e)
            /* Crashlytics.log(ServerInvalidResponseException.ERROR_500_RESPONSE +
                                            " \nRequest: " + request.toString() +
                                             "\nResponse: " + response.toString()); */
            throw e
        } else if (response.code == 401) { // 401 Unauthorized (RFC 7235)
            /**
             * Similar to 403 Forbidden, but specifically for use when authentication
             * is required and has failed or has not yet been provided.
             * The response must include a WWW-Authenticate header field containing a
             * challenge applicable to the requested resource. See Basic access
             * authentication and Digest access authentication.
             * 401 semantically means "unauthenticated",
             * i.e. the user does not have the necessary credentials.
             * Note: Some sites issue HTTP 401 when an IP address is banned from the
             * website (usually the website domain) and that specific address is
             * refused permission to access a website.
             */
            throw Server401ResponseException()
            /*} else if(response.code() == 403) { // 403 Forbidden */
            /**
             * The request was valid, but the server is refusing action.
             * The user might not have the necessary permissions for a resource,
             * or may need an account of some sort.
             */
        } else if (response.code == 400) { // Bad Request
            // https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/src/main/java/okhttp3/logging/HttpLoggingInterceptor.java#L218-L261
            val responseBody = response.body
            if (responseBody != null) {
                val source: BufferedSource = responseBody.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                val bufferCopy: Buffer = source.buffer().clone()
                /*
                 to read bufferCopy as String
                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                if (contentLength != 0) {
                    String s = bufferCopy.readString(charset);
                } */

                val responseBodyCopy = ResponseBody.create(responseBody.contentType(),
                    bufferCopy.size, bufferCopy)
                val apiErrorResponse: APIErrorResponse = ErrorUtils.parseError(responseBodyCopy)
                throw Server400ResponseException(apiErrorResponse)

                /* Handler handler = new Handler(mContext.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialogManager.getInstance()
                                .showAlertDialog(mContext, R.drawable.ic_warning_black_24dp,
                                        apiErrorResponse.getTitle(), apiErrorResponse.getMessage());
                    }
                }); */
            } else {
                // server returned 400 with a blank response :/
                // AlertDialogManager.getInstance().displayInvalidResponseAlert(mContext);
                // 029, 29-Jun-18 log error, 400 blank response
                // 010, 10-Jul-18 done
                val e = ServerInvalidResponseException(ServerInvalidResponseException.ERROR_400_BLANK_RESPONSE)
                ErrorUtils.logNetworkError(ServerInvalidResponseException.ERROR_400_BLANK_RESPONSE +
                                                    " \nRequest: " + request.toString() +
                                                     "\nResponse: " + response.toString(), e)
                throw e
            }
        } else {
            // unexpected status code returned
            // 029, 29-Jun-18 log error, unexpected status code
            // 010, 10-Jul-18 done
            val e = ServerInvalidResponseException(ServerInvalidResponseException.ERROR_UNEXPECTED_STATUS_CODE)
            ErrorUtils.logNetworkError(ServerInvalidResponseException.ERROR_UNEXPECTED_STATUS_CODE +
                                                " \nRequest: " + request.toString() +
                                                 "\nResponse: " + response.toString(), e)
            throw e
            /* Handler handler = new Handler(mContext.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    AlertDialogManager.getInstance()
                            .displayInvalidResponseAlert(mContext);// unexpected status code returned
                }
            }); */
        }
    }
}
