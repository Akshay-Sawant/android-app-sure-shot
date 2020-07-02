package com.sureshots.app.network

/*import com.crashlytics.android.Crashlytics;*/
import com.sureshots.app.data.api.APIClient
import com.sureshots.app.data.model.response.APIErrorResponse
/*import com.innovins.helperlibrary.helper.AlertDialogManager
import com.innovins.helperlibrary.helper.LoadingViewManager*/
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.io.IOException

/**
 * Created by Innovins-21 on 028, 28-Jun-18, 5:14 PM.
 * @version 2.0.0
 */

object ErrorUtils {
    internal fun parseError(response: ResponseBody): APIErrorResponse {
        val converter = APIClient.retrofitInstance
                        .responseBodyConverter<APIErrorResponse>(APIErrorResponse::class.java, arrayOfNulls(0))

        val apiErrorResponse: APIErrorResponse
        try {
            apiErrorResponse = converter.convert(response)!!
        } catch (e: IOException) {
            return APIErrorResponse()
        }

        return apiErrorResponse
    }

    /*fun parseOnFailureException(
        context: Context,
        call: Call<*>,
        t: Throwable,
        loadingViewManager: LoadingViewManager?
    ) {
        if (t is Server400ResponseException) {
            val apiErrorResponse = t.apiErrorResponse as APIErrorResponse
            if (loadingViewManager != null) {
                loadingViewManager.showErrorView(apiErrorResponse.title, apiErrorResponse.message)
            } else {
                AlertDialogManager.instance
                    .showAlertDialog(
                        context, R.drawable.ic_warning_black_24dp,
                        apiErrorResponse.title, apiErrorResponse.message
                    )
            }
        } else if (t is Server401ResponseException) {
            if (context is Activity) {
                LoginHelper.startLoginFlow(context)
            } else {
                Toast.makeText(context,"Please login to continue!", Toast.LENGTH_SHORT).show()
            }
        } else if (t is ServerInvalidResponseException) {
            if (IS_DEBUG_ON) {
                t.printStackTrace()
            }

            if (loadingViewManager != null) {
                loadingViewManager.showErrorView(
                    context.getString(R.string.alert_connection_status_not_ok_title),
                    context.getString(R.string.alert_connection_status_not_ok_message)
                )
            } else {
                AlertDialogManager.instance.displayInvalidResponseAlert(context)
            }
        } else if (t is JsonEncodingException) {
            // conversion issue! big problems, malformed JSON :(
            if (IS_DEBUG_ON) {
                t.printStackTrace()
            }

            // 027, 27-Jun-18 log error, JSON mismatch!
            // 010, 10-Jul-18 done
            ErrorUtils.logNetworkError(
                "JSONMismatch " + t.message +
                        "\n\nRequest: " + call.request().toString(), t
            )
            *//* todo: uncomment later
            Crashlytics.log("JSONMismatch " + t.getMessage() +
                    "\n\nRequest: " + call.request().toString());*//*

            if (loadingViewManager != null) {
                loadingViewManager.showErrorView(
                    context.getString(R.string.alert_connection_status_not_ok_title),
                    context.getString(R.string.alert_connection_status_not_ok_message)
                )
            } else {
                AlertDialogManager.instance.displayInvalidResponseAlert(context)
            }
        } else if (t is JsonDataException) {
            // conversion issue! big problems, malformed JSON :(
            if (IS_DEBUG_ON) {
                t.printStackTrace()
            }

            ErrorUtils.logNetworkError(
                "JSONMismatch " + t.message +
                        "\n\nRequest: " + call.request().toString(), t
            )
            *//*  todo: uncomment later
            Crashlytics.log("JSONMismatch " + t.getMessage() +
                    "\n\nRequest: " + call.request().toString());*//*

            if (loadingViewManager != null) {
                loadingViewManager.showErrorView(
                    context.getString(R.string.alert_connection_status_not_ok_title),
                    context.getString(R.string.alert_connection_status_not_ok_message)
                )
            } else {
                AlertDialogManager.instance.displayInvalidResponseAlert(context)
            }
        } else if (t is IOException) {
            // this is an actual network failure :( inform the user and possibly retry
            if (loadingViewManager != null) {
                loadingViewManager.showErrorView(
                    context.getString(R.string.alert_connection_lost_title),
                    context.getString(R.string.alert_connection_lost_message)
                )
            } else {
                AlertDialogManager.instance.displayConnectionLostAlert(context)
            }
        } else {
            // unknown error :/
            if (IS_DEBUG_ON) {
                t.printStackTrace()
            }

            ErrorUtils.logNetworkError("UnhandledError \n\nRequest: " + call.request().toString(), t)

            if (loadingViewManager != null) {
                loadingViewManager.showErrorView(
                    context.getString(R.string.alert_connection_status_not_ok_title),
                    context.getString(R.string.alert_connection_status_not_ok_message)
                )
            } else {
                AlertDialogManager.instance.displayInvalidResponseAlert(context)// malformed JSON :(
            }
        }

    }*/

    fun logNetworkError(message: String, e: Throwable?) {
        val call = APIClient.apiInterface.logNetworkError(message)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                /*if(response.isSuccessful()) {

                }*/
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })
    }
}
