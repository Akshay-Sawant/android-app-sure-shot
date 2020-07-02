package com.sureshots.app.login

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.widget.Toast
/*import com.sureshots.app.LoginActivity*/
import com.sureshots.app.model.response.APIActionResponse
import com.sureshots.app.network.APIClient
import com.sureshots.app.network.ErrorUtils
import com.sureshots.app.network.ServerInvalidResponseException
/*import com.innovins.helperlibrary.constant.REQUEST_CODE_LOGIN_SUCCESS
import com.innovins.helperlibrary.helper.AlertDialogManager
import com.innovins.helperlibrary.helper.ProgressDialogManager*/
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginHelper(context: Context) {

    private val LOGIN_PREF_FILE: String = "LOGIN_PREF_FILE"

    private val LOGIN_USER_LOGIN_TOKEN_KEY = "loginToken"
    private val LOGIN_USER_ID_KEY = "userId"
    private val LOGIN_USERNAME_KEY = "userName"
    private val LOGIN_USER_FULL_NAME_KEY = "userFullName"
    // private val LOGIN_USER_FIRST_NAME_KEY = "userFirstName"
    // private val LOGIN_USER_LAST_NAME_KEY = "userLastName"

    private val LOGIN_USER_MOBILE_COUNTRY_CODE_KEY = "userMobileCountryCode"
    private val LOGIN_USER_MOBILE_KEY = "userMobile"
    private val LOGIN_IS_MOBILE_VERIFIED_KEY = "isMobileVerified"

    private val LOGIN_USER_EMAIL_KEY = "userEmail"
    private val LOGIN_IS_EMAIL_VERIFIED_KEY = "isEmailVerified"

    private val LOGIN_USER_PROFILE_IMAGE_KEY = "userProfileImage"
    private val LOGIN_USER_REFERENCE_CODE_KEY = "userReferenceCode"

    private val isSocialLoginKey = "isSocialLoginKey"

    private var mLoggedInUser: LoggedInUser

    init {
        val prefs = context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
        mLoggedInUser = LoggedInUser()
        mLoggedInUser.loginToken = prefs.getString(LOGIN_USER_LOGIN_TOKEN_KEY, "").toString()
        mLoggedInUser.userId = prefs.getString(LOGIN_USER_ID_KEY, "").toString()
        mLoggedInUser.userName = prefs.getString(LOGIN_USERNAME_KEY, "").toString()
        mLoggedInUser.fullName = prefs.getString(LOGIN_USER_FULL_NAME_KEY, "").toString()
        // mLoggedInUser.firstName = prefs.getString(LOGIN_USER_FIRST_NAME_KEY, "")
        // mLoggedInUser.lastName = prefs.getString(LOGIN_USER_LAST_NAME_KEY, "")
        mLoggedInUser.countryCode = prefs.getString(LOGIN_USER_MOBILE_COUNTRY_CODE_KEY, "").toString()
        mLoggedInUser.mobile = prefs.getString(LOGIN_USER_MOBILE_KEY, "").toString()
        mLoggedInUser.isMobileVerified = prefs.getBoolean(LOGIN_IS_MOBILE_VERIFIED_KEY, false)
        mLoggedInUser.email = prefs.getString(LOGIN_USER_EMAIL_KEY, "").toString()
        mLoggedInUser.isEmailVerified = prefs.getBoolean(LOGIN_IS_EMAIL_VERIFIED_KEY, false)
        mLoggedInUser.profileImage = prefs.getString(LOGIN_USER_PROFILE_IMAGE_KEY, "").toString()
        mLoggedInUser.referenceCode = prefs.getString(LOGIN_USER_REFERENCE_CODE_KEY, "").toString()

        mLoggedInUser.isSocialLogin = prefs.getBoolean(isSocialLoginKey, false)
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

            /*val intent = Intent(activity, OnBoardingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity.startActivity(intent)*/

           /* val intent = LoginActivity.newIntent(activity)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity.startActivityForResult(intent, REQUEST_CODE_LOGIN_SUCCESS)*/

            /*val intent = SelectUserTypeActivity.newIntent(activity)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity.startActivity(intent)*/
        }
    }

    fun getLoggedInUser(): LoggedInUser {
        return mLoggedInUser
    }

    /**
     * For logging in a user or updating a user profile on device
     */
    fun saveUpdatedLoggedInUser(context: Context): Boolean {
        val prefEditor = context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE).edit()
        prefEditor.putString(LOGIN_USER_LOGIN_TOKEN_KEY, mLoggedInUser.loginToken)
        prefEditor.putString(LOGIN_USER_ID_KEY, mLoggedInUser.userId)
        prefEditor.putString(LOGIN_USERNAME_KEY, mLoggedInUser.userName)
        prefEditor.putString(LOGIN_USER_FULL_NAME_KEY, mLoggedInUser.fullName)
        // prefEditor.putString(LOGIN_USER_SALUTATION_KEY, mLoggedInUser.salutation)
        // prefEditor.putString(LOGIN_USER_FIRST_NAME_KEY, mLoggedInUser.firstName)
        // prefEditor.putString(LOGIN_USER_LAST_NAME_KEY, mLoggedInUser.lastName)
        prefEditor.putString(LOGIN_USER_MOBILE_COUNTRY_CODE_KEY, mLoggedInUser.countryCode)
        prefEditor.putString(LOGIN_USER_MOBILE_KEY, mLoggedInUser.mobile)
        prefEditor.putBoolean(LOGIN_IS_MOBILE_VERIFIED_KEY, mLoggedInUser.isMobileVerified)
        prefEditor.putString(LOGIN_USER_EMAIL_KEY, mLoggedInUser.email)
        prefEditor.putBoolean(LOGIN_IS_EMAIL_VERIFIED_KEY, mLoggedInUser.isEmailVerified)
        prefEditor.putString(LOGIN_USER_PROFILE_IMAGE_KEY, mLoggedInUser.profileImage)
        prefEditor.putString(LOGIN_USER_REFERENCE_CODE_KEY, mLoggedInUser.referenceCode)

        prefEditor.putBoolean(isSocialLoginKey, mLoggedInUser.isSocialLogin)
        return prefEditor.commit()
    }

    /**
     * clears all login related data from device
     */
    private fun doLogout(context: Context){
        val prefEditor = context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE).edit()
        prefEditor.clear()
        prefEditor.apply()
    }

    /**
     * returns true if a login token is saved on device
     */
    fun isUserLogin(): Boolean = (mLoggedInUser.loginToken != "")

    fun requestLogout(context: Context){
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

    fun doServerLogout(context: Context){
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
                val call: Call<APIActionResponse> = APIClient.apiInterface.doLogout(mLoggedInUser.loginToken)
                call.enqueue(object: Callback<APIActionResponse> {

                    override fun onResponse(call: Call<APIActionResponse>, response: Response<APIActionResponse>) {
//                        ProgressDialogManager.instance.hideProgressDialog()

                        if(response.isSuccessful) {
                            val apiActionResponse: APIActionResponse  ?= response.body()
                            if(apiActionResponse != null){
                                if(apiActionResponse.isActionSuccess){
                                    doLogout(context)
                                    startLoginFlow(context as Activity)
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
                                        "\nResponse: " + response.toString(), null)
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