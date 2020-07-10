package com.sureshots.app.data.api

import com.sureshots.app.data.model.LoggedInUser
import com.sureshots.app.data.model.response.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

import com.sureshots.app.data.api.APIClient.API_VERSION
import com.sureshots.app.data.model.DTHCompanyModel
import com.sureshots.app.data.model.SimCompanyModel

interface APIInterface {

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
    fun doSignUp(
        @Field("mobile") mobile: String
    ): Call<LoggedInUser>

    @FormUrlEncoded
    @POST("$API_VERSION/RequestSignUpOTP.php")
    fun requestSignUpOTP(
        @Field("mobile") mobile: String,
        @Field("referralId") referralId: String
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$API_VERSION/VerifySignUpOTP.php")
    fun verifySignUpOTP(
        @Field("mobile") mobile: String,
        @Field("otp") otp: String
    ): Call<LoggedInUser>

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
    fun resetPassword(
        @Field("mobile") mobile: String,
        @Field("otp") otp: String,
        @Field("newPassword") newPassword: String,
        @Field("reTypedNewPassword") reTypeNewPassword: String
    ): Call<APIActionResponse>
    /*Login*/


    @Deprecated("to be removed")
    @FormUrlEncoded
    @POST("$API_VERSION/SendInvoiceEmail.php")
    fun sendInvoiceEmail(
        @Field("userLoginToken") userLoginToken: String,
        @Field("orderId") orderId: String
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$API_VERSION/SimCompany.php")
    fun getSimCompany(@Field("userLoginToken") userLoginToken: String): Call<List<SimCompanyModel>>

    @FormUrlEncoded
    @POST("$API_VERSION/DTHCompany.php")
    fun getDTHCompany(@Field("userLoginToken") userLoginToken: String): Call<List<DTHCompanyModel>>

    /*Prepaid Recharge APIs*/
    @FormUrlEncoded
    @POST("/getServiceData.go")
    fun getCircleList(
        @Field("goid") goid: String,
        @Field("apikey") apikey: String,
        @Field("rtype") rtype: String,
        @Field("type") type: String
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("/getServiceData.go")
    fun getOperatorList(
        @Field("goid") goid: String,
        @Field("apikey") apikey: String,
        @Field("rtype") rtype: String,
        @Field("service_family") service_family: String
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("/getMsisdnInfo.go")
    fun getMsisdnInfo(
        @Field("goid") goid: String,
        @Field("apikey") apikey: String,
        @Field("rtype") rtype: String,
        @Field("service_family") service_family: String,
        @Field("msisdn") msisdn: String
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("/getRechargePlan.go")
    fun getRechargePlan(
        @Field("goid") goid: String,
        @Field("apikey") apikey: String,
        @Field("rtype") rtype: String,
        @Field("service_family") service_family: String,
        @Field("operator_code") operator_code: String,
        @Field("circle_code") circle_code: String
    ): Call<APIActionResponse>
}