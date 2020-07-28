package com.sureshotdiscount.app.utils.others

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.sureshotdiscount.app.data.model.response.APIActionResponse
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.data.model.LoggedInUser
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SharedPreferenceUtils(context: Context) {

    private val LOGIN_PREF_FILE: String = "LOGIN_PREF_FILE"

    private val LOGIN_USER_LOGIN_TOKEN_KEY = "loginToken"
    private val LOGIN_USER_ID_KEY = "id"
    private val LOGIN_USER_NAME_KEY = "name"
    private val LOGIN_USER_MOBILE_NUMBER_KEY = "mobileNumber"
    private val LOGIN_USER_EMAIL_ID_KEY = "emailId"
    private val LOGIN_USER_REFERRAL_ID_KEY = "referralId"

    private val RECHARGE_COMPANY_ID = "rechargeCompanyId"
    private val RECHARGE_COMPANY_CODE = "rechargeCompanyCode"
    private val RECHARGE_COMPANY_NAME = "rechargeCompanyName"
    private val RECHARGE_COMPANY_LOGO = "rechargeCompanyLogo"
    private val RECHARGE_MOBILE_NUMBER = "rechargeMobileNumber"
    private val RECHARGE_TYPE = "rechargeType"
    private val RECHARGE_CIRCLE_CODE = "rechargeCircleCode"
    private val RECHARGE_AMOUNT = "rechargeAmount"

    private var mLoggedInUser: LoggedInUser

    private var prefEditor =
        context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE).edit()

    init {
        val prefs = context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
        mLoggedInUser = LoggedInUser()
        mLoggedInUser.loginToken = prefs.getString(LOGIN_USER_LOGIN_TOKEN_KEY, "").toString()
        mLoggedInUser.id = prefs.getString(LOGIN_USER_ID_KEY, "").toString()
        mLoggedInUser.name = prefs.getString(LOGIN_USER_NAME_KEY, "").toString()
        mLoggedInUser.mobileNumber = prefs.getString(LOGIN_USER_MOBILE_NUMBER_KEY, "").toString()
        mLoggedInUser.emailid = prefs.getString(LOGIN_USER_EMAIL_ID_KEY, "").toString()
        mLoggedInUser.referralid = prefs.getString(LOGIN_USER_REFERRAL_ID_KEY, "").toString()
    }

    constructor(context: Context, loggedInUser: LoggedInUser) : this(context) {
        mLoggedInUser = loggedInUser
    }

    companion object {
        fun startLoginFlow(activity: Activity) {
            val toast = Toast.makeText(activity, "Please login to continue!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            activity.finish()
        }
    }

    fun getLoggedInUser(): LoggedInUser {
        return mLoggedInUser
    }

    /**
     * For logging in a user or updating a user profile on device
     */
    fun saveUpdatedLoggedInUser(context: Context): Boolean {

        prefEditor.putString(LOGIN_USER_LOGIN_TOKEN_KEY, mLoggedInUser.loginToken)
        prefEditor.putString(LOGIN_USER_ID_KEY, mLoggedInUser.id)
        prefEditor.putString(LOGIN_USER_NAME_KEY, mLoggedInUser.name)
        prefEditor.putString(LOGIN_USER_MOBILE_NUMBER_KEY, mLoggedInUser.mobileNumber)
        prefEditor.putString(LOGIN_USER_EMAIL_ID_KEY, mLoggedInUser.emailid)
        prefEditor.putString(LOGIN_USER_REFERRAL_ID_KEY, mLoggedInUser.referralid)
        return prefEditor.commit()
    }

    /**
     * clears all login related data from device
     */
    private fun doLogout(context: Context) {
        val prefEditor = context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE).edit()
        prefEditor.clear()
        prefEditor.apply()
    }

    fun saveRechargeDetails(
        mRechargeCompanyId: String,
        mRechargeCompanyCode: String,
        mRechargeCompanyName: String,
        mRechargeCompanyLogo: String
    ): Boolean {
        prefEditor.putString(RECHARGE_COMPANY_ID, mRechargeCompanyId)
        prefEditor.putString(RECHARGE_COMPANY_CODE, mRechargeCompanyCode)
        prefEditor.putString(RECHARGE_COMPANY_NAME, mRechargeCompanyName)
        prefEditor.putString(RECHARGE_COMPANY_LOGO, mRechargeCompanyLogo)
        return prefEditor.commit()
    }

    fun getRechargeId(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(RECHARGE_COMPANY_ID, "")
    }

    fun getRechargeCompanyCode(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(RECHARGE_COMPANY_CODE, "")
    }

    fun getRechargeCompanyName(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(RECHARGE_COMPANY_NAME, "")
    }

    fun getRechargeCompanyLogo(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(RECHARGE_COMPANY_LOGO, "")
    }

    fun saveRechargeMobileNumber(mMobileNumber: String): Boolean {
        prefEditor.putString(RECHARGE_MOBILE_NUMBER, mMobileNumber)
        return prefEditor.commit()
    }

    fun getRechargeMobileNumber(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(RECHARGE_MOBILE_NUMBER, "")
    }

    fun saveRechargeType(mRechargeType: String): Boolean {
        prefEditor.putString(RECHARGE_TYPE, mRechargeType)
        return prefEditor.commit()
    }

    fun getRechargeType(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(RECHARGE_TYPE, "")
    }

    fun saveRechargeCircleCode(mRechargeCircleCode: String): Boolean {
        prefEditor.putString(RECHARGE_CIRCLE_CODE, mRechargeCircleCode)
        return prefEditor.commit()
    }

    fun getRechargeCircleCode(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(RECHARGE_CIRCLE_CODE, "")
    }

    fun saveRechargeAmount(mRechargeAmount: String): Boolean {
        prefEditor.putString(RECHARGE_AMOUNT, mRechargeAmount)
        return prefEditor.commit()
    }

    fun getRechargeAmount(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(RECHARGE_AMOUNT, "0")
    }

    /**
     * returns true if a login token is saved on device
     */
    fun isUserLogin(): Boolean = (mLoggedInUser.loginToken != "")

    fun requestLogout(context: Context) {
        /*AlertDialogManager.instance.showAlertDialog(context,
            R.drawable.ic_warning_black_24dp,
            context.getString(R.string.alert_logout_heads_up_title),
            context.getString(R.string.alert_logout_heads_up_message),
            context.getString(R.string.action_logout),
            DialogInterface.OnClickListener { dialogInterface, i ->
                doServerLogout(context)
            },
            context.getString(android.R.string.no), null);*/
    }

    fun doServerLogout(context: Context) {
        // doLogout(context)
        // startLoginFlow(context as Activity)
        if (!APIClient.isNetworkConnected(context)) {
//            AlertDialogManager.instance.displayNoConnectionAlert(context)
            return
        }

        if (!APIClient.isNetworkConnected(context)) {
//                    AlertDialogManager.instance.displayNoConnectionAlert(context)
            return
        }

//                ProgressDialogManager.instance.showProgressDialog(context, "Logging out...")
        val call: Call<APIActionResponse> =
            APIClient.apiInterface.doLogout(mLoggedInUser.loginToken)
        call.enqueue(object : Callback<APIActionResponse> {

            override fun onResponse(
                call: Call<APIActionResponse>,
                response: Response<APIActionResponse>
            ) {
//                        ProgressDialogManager.instance.hideProgressDialog()

                if (response.isSuccessful) {
                    val apiActionResponse: APIActionResponse? = response.body()
                    if (apiActionResponse != null) {
                        if (apiActionResponse.isActionSuccess) {
                            doLogout(context)
                            startLoginFlow(
                                context as Activity
                            )
                        } else {
                            /*AlertDialogManager.instance.showAlertDialog(context,
                                                            R.drawable.ic_warning_black_24dp,
                                                            apiActionResponse.title,
                                                            apiActionResponse.message)*/
                        }
                    } else {
                        // server returned 200 with a blank response :/
                        ErrorUtils.logNetworkError(
                            ServerInvalidResponseException.ERROR_200_BLANK_RESPONSE +
                                    "\nResponse: " + response.toString(), null
                        )
//                                AlertDialogManager.instance.displayInvalidResponseAlert(context);
                        // server returned 200 with a blank response :/
                    }
                }
            }

            override fun onFailure(call: Call<APIActionResponse>, t: Throwable) {
                // progressDialog.dismiss();
//                        ProgressDialogManager.instance.hideProgressDialog()
//                        ErrorUtils.parseOnFailureException(context, call, t, null)
            }
        })
    }
}