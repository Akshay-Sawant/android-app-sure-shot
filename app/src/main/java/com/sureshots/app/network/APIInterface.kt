package com.sureshots.app.network

import com.sureshots.app.login.LoggedInUser
import com.sureshots.app.model.*
import com.sureshots.app.model.response.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

import com.sureshots.app.network.APIClient.API_VERSION
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface APIInterface {

    companion object {

    }

    /*@Deprecated("Test api")
    @GET("$API_VERSION/sample.php")
    fun getSample(@Query("id") cityID: Int, @Query("APPID") appID: String): Call<APIErrorResponse>*/

    @FormUrlEncoded
    @POST("$API_VERSION/AndroidLogError.php")
    fun logNetworkError(@Field("error") error: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("$API_VERSION/UpdateLanguagePreference.php")
    fun updateLanguagePreference(
        @Field("userLoginToken") userLoginToken: String,
        @Field("language") language: String // language code - en, hi
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$API_VERSION/AppConfig.php")
    fun getAppConfig(@Field("version") version: Int): Call<AppConfigResponse>

    /*Register*/
    @FormUrlEncoded
    @POST("$API_VERSION/SignUp.php")
    fun doSignUp(@Field("mobile") mobile: String
    ): Call<LoggedInUser>

    @FormUrlEncoded
    @POST("$API_VERSION/RequestSignUpOTP.php")
    fun requestSignUpOTP(@Field("mobile") mobile: String): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$API_VERSION/VerifySignUpOTP.php")
    fun verifySignUpOTP(@Field("mobile") mobile: String,
                        @Field("otp") otp: String): Call<LoggedInUser>

    /*Login*/
    @FormUrlEncoded
    @POST("$API_VERSION/RequestLoginOTP.php")
    fun requestLoginOTP(@Field("mobile") mobile: String): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$API_VERSION/Login.php")
    fun doLogin(
        @Field("userName") username: String,
        @Field("password") password: String,
        @Field("FCMToken") FCMToken: String?,
        @Field("lang") language: String?
    ): Call<LoggedInUser>

    @FormUrlEncoded
    @POST("$API_VERSION/Logout.php")
    fun doLogout(@Field("userLoginToken") userLoginToken: String): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$API_VERSION/RefreshFCMToken.php")
    fun saveFCMToken(
        @Field("userLoginToken") userLoginToken: String,
        @Field("FCMToken") FCMToken: String
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$API_VERSION/ForgotPassword.php")
    fun forgotPassword(@Field("mobile") mobile: String): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$API_VERSION/ResetPassword.php")
    fun resetPassword(@Field("mobile") mobile: String,
                      @Field("otp") otp: String,
                      @Field("newPassword") newPassword: String,
                      @Field("reTypedNewPassword") reTypeNewPassword: String): Call<APIActionResponse>
    /*Login*/


    @Deprecated("to be removed")
    @FormUrlEncoded
    @POST("$API_VERSION/SendInvoiceEmail.php")
    fun sendInvoiceEmail(@Field("userLoginToken") userLoginToken: String,
                         @Field("orderId") orderId: String): Call<APIActionResponse>

}