package com.sureshotdiscount.app.utils.others

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.sureshotdiscount.app.data.model.LoggedInUser

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
    private val RECHARGE_SUBSCRIPTION_ID = "rechargeSubscriptionId"
    private val RECHARGE_TYPE = "rechargeType"
    private val RECHARGE_CIRCLE_CODE = "rechargeCircleCode"
    private val RECHARGE_AMOUNT = "rechargeAmount"
    private val PLAN_ID = "plan_id"
    private val SUBSCRIPTION_DONE = "subscription_done"

    private val IS_MOBILE_RECHARGE = "isMobileRecharge"

    private val BALANCE_EARNINGS = "balanceEarnings"

    private val NAME_ON_ACCOUNT = "nameOnAccount"
    private val ACCOUNT_NUMBER = "accountNumber"
    private val IFSC_CODE = "ifscCode"
    private val BANK_NAME = "bankName"
    private val BRANCH_NAME = "branchName"

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
    fun doLogout(context: Context) {
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

    fun saveRechargeSubscriptionId(mRechargeSubscriptionId: String): Boolean {
        prefEditor.putString(RECHARGE_SUBSCRIPTION_ID, mRechargeSubscriptionId)
        return prefEditor.commit()
    }

    fun getRechargeSubscriptionId(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(RECHARGE_SUBSCRIPTION_ID, "")
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

    fun savePlanId(mPlanId: String): Boolean {
        prefEditor.putString(PLAN_ID, mPlanId)
        return prefEditor.commit()
    }

    fun getPlanId(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(PLAN_ID, "")
    }

    fun saveSubscriptionDone(mSubscriptionDone: Boolean): Boolean {
        prefEditor.putBoolean(SUBSCRIPTION_DONE, mSubscriptionDone)
        return prefEditor.commit()
    }

    fun getSubscriptionDone(context: Context): Boolean? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getBoolean(SUBSCRIPTION_DONE, false)
    }

    fun saveIsMobileRecharge(mIsMobileRecharge: Boolean): Boolean {
        prefEditor.putBoolean(IS_MOBILE_RECHARGE, mIsMobileRecharge)
        return prefEditor.commit()
    }

    fun getIsMobileRecharge(context: Context): Boolean {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getBoolean(IS_MOBILE_RECHARGE, false)
    }

    fun saveBalanceEarnings(mBalanceEarnings: Int): Boolean {
        prefEditor.putInt(BALANCE_EARNINGS, mBalanceEarnings)
        return prefEditor.commit()
    }

    fun getBalanceEarnings(context: Context): Int? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getInt(BALANCE_EARNINGS, 0)
    }

    fun saveBankDetails(
        mNameOnAccount: String,
        mAccountNumber: String,
        mIFSCCode: String,
        mBankName: String,
        mBranchAddress: String
    ): Boolean {
        prefEditor.putString(NAME_ON_ACCOUNT, mNameOnAccount)
        prefEditor.putString(ACCOUNT_NUMBER, mAccountNumber)
        prefEditor.putString(IFSC_CODE, mIFSCCode)
        prefEditor.putString(BANK_NAME, mBankName)
        prefEditor.putString(BRANCH_NAME, mBranchAddress)
        return prefEditor.commit()
    }

    fun getNameOnAccount(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(NAME_ON_ACCOUNT, "")
    }

    fun getAccountNumber(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(ACCOUNT_NUMBER, "")
    }

    fun getIFSCCode(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(IFSC_CODE, "")
    }

    fun getBankName(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(BANK_NAME, "")
    }

    fun getBranchName(context: Context): String? {
        return context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE)
            .getString(BRANCH_NAME, "")
    }

    /**
     * returns true if a login token is saved on device
     */
    fun isUserLogin(): Boolean = (mLoggedInUser.loginToken != "")
}