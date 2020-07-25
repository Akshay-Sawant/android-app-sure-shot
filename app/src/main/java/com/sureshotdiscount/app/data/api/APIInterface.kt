package com.sureshotdiscount.app.data.api

import com.sureshotdiscount.app.data.model.LoggedInUser
import com.sureshotdiscount.app.data.model.response.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

import com.sureshotdiscount.app.data.api.APIClient.API_VERSION
import com.sureshotdiscount.app.ui.dth.DTHModel
import com.sureshotdiscount.app.ui.mobile.MobileRechargeModel
import com.sureshotdiscount.app.ui.plans.PlansModel
import com.sureshotdiscount.app.ui.rechargeHistory.RechargeHistoryModel
import com.sureshotdiscount.app.ui.referralslist.LevelsModel
import com.sureshotdiscount.app.ui.referralslist.ReferralsListModel
import com.sureshotdiscount.app.ui.subscriptionplan.SubscriptionPlanModel

interface APIInterface {

    @FormUrlEncoded
    @POST("$API_VERSION/AndroidLogError.php")
    fun logNetworkError(@Field("error") error: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("$API_VERSION/AppConfig.php")
    fun getAppConfig(@Field("version") version: Int): Call<AppConfigResponse>

    /*Sign Up*/
    @FormUrlEncoded
    @POST("$API_VERSION/signup.php")
    fun requestSignUpOTP(
        @Field("name") name: String,
        @Field("emailId") emailId: String,
        @Field("mobileNumber") mobileNumber: String,
        @Field("password") password: String,
        @Field("referralId") referralId: String
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$API_VERSION/VerifySignUpOTP.php")
    fun verifySignUpOTP(
        @Field("mobileNumber") mobileNumber: String,
        @Field("otp") otp: String
    ): Call<LoggedInUser>
    /*Sign Up*/

    /*Sign In*/
    @FormUrlEncoded
    @POST("$API_VERSION/RequestLoginOTP.php")
    fun requestSignInOTP(
        @Field("mobileNumber") mobileNumber: String,
        @Field("password") password: String
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$API_VERSION/Login.php")
    fun verifySignInOTP(
        @Field("mobileNumber") mobileNumber: String,
        @Field("otp") otp: String
    ): Call<LoggedInUser>

    @FormUrlEncoded
    @POST("$API_VERSION/Logout.php")
    fun doLogout(@Field("userLoginToken") userLoginToken: String): Call<APIActionResponse>
    /*Sign In*/

    @Deprecated("to be removed")
    @FormUrlEncoded
    @POST("$API_VERSION/SendInvoiceEmail.php")
    fun sendInvoiceEmail(
        @Field("userLoginToken") userLoginToken: String,
        @Field("orderId") orderId: String
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$API_VERSION/MobileRechargeCompany.php")
    fun getMobileRechargeCompany(@Field("userLoginToken") userLoginToken: String): Call<List<MobileRechargeModel>>

    @FormUrlEncoded
    @POST("$API_VERSION/DTHCompany.php")
    fun getDTHCompany(@Field("userLoginToken") userLoginToken: String): Call<List<DTHModel>>

    @FormUrlEncoded
    @POST("$API_VERSION/RechargeHistory.php")
    fun getRechargeHistory(@Field("userLoginToken") userLoginToken: String): Call<List<RechargeHistoryModel>>

    @FormUrlEncoded
    @POST("$API_VERSION/ContactUs.php")
    fun contactUs(
        @Field("userLoginToken") userLoginToken: String,
        @Field("message") message: String
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$API_VERSION/SubscriptionPlan.php")
    fun getSubscriptionPlan(@Field("userLoginToken") userLoginToken: String): Call<SubscriptionPlanModel>

    @FormUrlEncoded
    @POST("$API_VERSION/Plans.php")
    fun getPlans(
        @Field("userLoginToken") userLoginToken: String,
        @Field("companyCode") companyCode: String,
        @Field("circleCode") circleCode: String
    ): Call<List<PlansModel>>

    @FormUrlEncoded
    @POST("$API_VERSION/referralsList.php")
    fun referralsList(@Field("userLoginToken") userLoginToken: String): Call<ReferralsListModel>

    @FormUrlEncoded
    @POST("$API_VERSION/levelsDetails.php")
    fun levelsDetails(
        @Field("userLoginToken") userLoginToken: String,
        @Field("levelId") levelId: String
    ): Call<LevelsModel>
}