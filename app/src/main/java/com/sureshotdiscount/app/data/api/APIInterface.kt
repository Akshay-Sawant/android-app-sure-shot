package com.sureshotdiscount.app.data.api

import com.sureshotdiscount.app.data.model.LoggedInUser
import com.sureshotdiscount.app.data.model.response.APIActionResponse
import com.sureshotdiscount.app.data.model.response.AppConfigResponse
import com.sureshotdiscount.app.ui.aboutus.AboutUsModel
import com.sureshotdiscount.app.ui.dth.DTHModel
import com.sureshotdiscount.app.ui.mobile.MobileRechargeModel
import com.sureshotdiscount.app.ui.plans.PlansModel
import com.sureshotdiscount.app.ui.privacypolicy.PrivacyAndPolicyModel
import com.sureshotdiscount.app.ui.recharge.CircleModel
import com.sureshotdiscount.app.ui.rechargeHistory.RechargeHistoryModel
import com.sureshotdiscount.app.ui.rechargedetails.InitiateRechargeModel
import com.sureshotdiscount.app.ui.referandearn.KYCModel
import com.sureshotdiscount.app.ui.referralslist.LevelsModel
import com.sureshotdiscount.app.ui.referralslist.ReferralsListModel
import com.sureshotdiscount.app.ui.signin.SignInModel
import com.sureshotdiscount.app.ui.subscriptionplan.BenefitsOfSubscriptionModel
import com.sureshotdiscount.app.ui.subscriptionplan.PaymentResultModel
import com.sureshotdiscount.app.ui.subscriptionplan.SubscriptionPlanModel
import com.sureshotdiscount.app.utils.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @FormUrlEncoded
    @POST("$AUTH/AndroidLogError.php")
    fun logNetworkError(@Field("error") error: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("$API_VERSION/AppConfig.php")
    fun getAppConfig(@Field("version") version: Int): Call<AppConfigResponse>

    /*Sign Up*/
    @FormUrlEncoded
    @POST("$AUTH/signup.php")
    fun requestSignUpOTP(
        @Field("name") name: String,
        @Field("emailId") emailId: String,
        @Field("mobileNumber") mobileNumber: String,
        @Field("password") password: String,
        @Field("referralId") referralId: String
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$AUTH/verify_otp.php")
    fun verifyOTP(
        @Field("mobileNumber") mobileNumber: String,
        @Field("otp") otp: String
    ): Call<LoggedInUser>
    /*Sign Up*/

    /*Sign In*/
    @FormUrlEncoded
    @POST("$AUTH/authenticate.php")
    fun requestSignInOTP(
        @Field("mobileNumber") mobileNumber: String,
        @Field("password") password: String
    ): Call<SignInModel>

    @FormUrlEncoded
    @POST("$AUTH/resend_otp.php")
    fun resendOTP(@Field("mobileNumber") mobileNumber: String): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$AUTH/logout.php")
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
    @POST("$RECHARGE_DATA/operator_list.php")
    fun getMobileRechargeCompany(@Field("userLoginToken") userLoginToken: String): Call<MobileRechargeModel>

    @FormUrlEncoded
    @POST("$D2H_DATA/operator_list.php")
    fun getDTHCompany(@Field("userLoginToken") userLoginToken: String): Call<DTHModel>

    @FormUrlEncoded
    @POST("$RECHARGE_DATA/recharge_history.php")
    fun getRechargeHistory(@Field("userLoginToken") userLoginToken: String): Call<RechargeHistoryModel>

    @FormUrlEncoded
    @POST("$CONTACT_US/contact_us_api.php")
    fun contactUs(
        @Field("userLoginToken") userLoginToken: String,
        @Field("message") message: String
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$SUBSCRIPTION/get_my_subscription.php")
    fun getSubscriptionPlan(@Field("userLoginToken") userLoginToken: String): Call<SubscriptionPlanModel>

    @FormUrlEncoded
    @POST("$SUBSCRIPTION/payment_success.php")
    fun paymentResult(
        @Field("userLoginToken") userLoginToken: String,
        @Field("response") response: String,
        @Field("responseText") responseText: String,
        @Field("unique_ref_id") unique_ref_id: String
    ): Call<PaymentResultModel>

    @FormUrlEncoded
    @POST("$SUBSCRIPTION/initiate_subscription.php")
    fun initiateSubscription(@Field("userLoginToken") userLoginToken: String): Call<BenefitsOfSubscriptionModel>

    @FormUrlEncoded
    @POST("$RECHARGE_DATA/get_plans.php")
    fun getPlans(
        @Field("userLoginToken") userLoginToken: String,
        @Field("companyCode") companyCode: String,
        @Field("circleCode") circleCode: String
    ): Call<PlansModel>

    @FormUrlEncoded
    @POST("$RECHARGE_DATA/initiate_recharge.php")
    fun initiateMobileRecharge(
        @Field("userLoginToken") userLoginToken: String,
        @Field("operator_code") operator_code: String,
        @Field("circle_code") circle_code: String,
        @Field("consumer_no") consumer_no: String,
        @Field("amount") amount: String,
        @Field("plan_id") plan_id: String
    ): Call<InitiateRechargeModel>

    @FormUrlEncoded
    @POST("$RECHARGE_DATA/payment_success.php")
    fun mobileRechargePaymentResult(
        @Field("userLoginToken") userLoginToken: String,
        @Field("response") response: String,
        @Field("responseText") responseText: String,
        @Field("unique_ref_id") unique_ref_id: String,
        @Field("orderId") orderId: String
    ): Call<PaymentResultModel>

    @FormUrlEncoded
    @POST("$D2H_DATA/initiate_recharge.php")
    fun initiateD2HRecharge(
        @Field("userLoginToken") userLoginToken: String,
        @Field("operator_code") operator_code: String,
        @Field("consumer_no") consumer_no: String,
        @Field("amount") amount: String
    ): Call<InitiateRechargeModel>

    @FormUrlEncoded
    @POST("$D2H_DATA/payment_success.php")
    fun d2hRechargePaymentResult(
        @Field("userLoginToken") userLoginToken: String,
        @Field("response") response: String,
        @Field("responseText") responseText: String,
        @Field("unique_ref_id") unique_ref_id: String,
        @Field("orderId") orderId: String
    ): Call<PaymentResultModel>

    @FormUrlEncoded
    @POST("$SUBSCRIPTION/get_my_level_list.php")
    fun referralsList(@Field("userLoginToken") userLoginToken: String): Call<ReferralsListModel>

    @FormUrlEncoded
    @POST("$SUBSCRIPTION/get_level_details.php")
    fun levelsDetails(
        @Field("userLoginToken") userLoginToken: String,
        @Field("levelId") levelId: Int
    ): Call<LevelsModel>

    @FormUrlEncoded
    @POST("$RECHARGE_DATA/circle_list.php")
    fun getCircleList(@Field("userLoginToken") userLoginToken: String): Call<CircleModel>

    @FormUrlEncoded
    @POST("$REFERRAL/withdraw_payment.php")
    fun paytmWithdraw(
        @Field("userLoginToken") userLoginToken: String,
        @Field("flag") flag: String,
        @Field("mobileNumber") mobileNumber: String,
        @Field("amountToBeWithdrawn") amountToBeWithdrawn: String
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$REFERRAL/withdraw_payment.php")
    fun bankWithdraw(
        @Field("userLoginToken") userLoginToken: String,
        @Field("flag") flag: String,
        @Field("amountToBeWithdrawn") amountToBeWithdrawn: String,
        @Field("nameOnAccount") nameOnAccount: String,
        @Field("accountNumber") accountNumber: String,
        @Field("ifscCode") ifscCode: String,
        @Field("bankName") bankName: String,
        @Field("bankBranch") bankBranch: String
    ): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$AUTH/forgot_password.php")
    fun forgotPassword(@Field("mobileNumber") mobileNumber: String): Call<APIActionResponse>

    @FormUrlEncoded
    @POST("$AUTH/create_new_password.php")
    fun createNewPassword(
        @Field("otp") otp: String,
        @Field("newPassword") password: String,
        @Field("mobileNumber") mobileNumber: String
    ): Call<APIActionResponse>

    @Multipart
    @POST("$SUBSCRIPTION/fill_kyc.php")
    fun fillKyc(
        @Part("userLoginToken") userLoginToken: RequestBody,
        @Part("name") name: RequestBody,
        @Part("emailId") emailId: RequestBody,
        @Part("address") address: RequestBody,
        @Part("panNo") panNo: RequestBody/*,
        @Part addressProof: MultipartBody.Part?,
        @Part panCard: MultipartBody.Part?*/
    ): Call<KYCModel>

    @FormUrlEncoded
    @POST("$CMS/ppolicy.php")
    fun privacyPolicy(@Field("userLoginToken") userLoginToken: String): Call<PrivacyAndPolicyModel>

    @FormUrlEncoded
    @POST("$CMS/aboutus.php")
    fun aboutUs(@Field("userLoginToken") userLoginToken: String): Call<AboutUsModel>
}