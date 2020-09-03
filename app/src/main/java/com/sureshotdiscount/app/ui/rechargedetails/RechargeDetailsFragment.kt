package com.sureshotdiscount.app.ui.rechargedetails

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.easebuzz.payment.kit.PWECouponsActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import datamodels.PWEStaticDataModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.text.NumberFormat
import java.util.*
import kotlin.properties.Delegates

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RechargeDetailsFragment : Fragment(R.layout.fragment_recharge_details), View.OnClickListener {

    private lateinit var mAppCompatImageViewRechargeDetailsCompanyLogo: AppCompatImageView
    private lateinit var mTextViewRechargeDetailsCompanyName: TextView
    private lateinit var mTextViewRechargeDetailsChange: TextView

    private lateinit var mTextViewRechargeDetailsRechargeType: TextView
    private lateinit var mTextViewRechargeDetailsChangeMethod: TextView
    private lateinit var mTextViewRechargeDetailsMobileNumber: TextView

    private lateinit var mTextViewRechargeDetailsSubscriptionIdLabel: TextView
    private lateinit var mTextViewRechargeDetailsSubscriptionId: TextView
    private lateinit var mTextViewRechargeSubscriptionIdChange: TextView
    private lateinit var mViewRechargeDetailsDividerSubscriptionId: View

    private lateinit var mTextInputLayoutRechargeDetailsEnterAmount: TextInputLayout
    private lateinit var mTextInputEditTextRechargeDetailsEnterAmount: TextInputEditText
    private lateinit var mTextViewRechargeDetailsSeePlan: TextView

    private lateinit var mButtonRechargeDetailsProceed: Button

    private lateinit var mContentLoadingProgressBarRechargeDetails: ContentLoadingProgressBar

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils
    private var mIsMobileRecharge by Delegates.notNull<Boolean>()

    private lateinit var mSalt: String
    private lateinit var merchant_trxnId: String
    private lateinit var merchant_payment_amount: String
    private lateinit var merchant_productInfo: String
    private lateinit var customer_firstName: String
    private lateinit var customer_email_id: String
    private lateinit var customer_phone: String
    private lateinit var merchant_key: String
    private lateinit var hash: String
    private lateinit var customers_unique_id: String
    private lateinit var payment_mode: String
    private lateinit var udf1: String
    private lateinit var udf2: String
    private lateinit var udf3: String
    private lateinit var udf4: String
    private lateinit var udf5: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAppCompatImageViewRechargeDetailsCompanyLogo =
            view.findViewById(R.id.appCompatImageViewRechargeDetailsCompanyLogo)
        mTextViewRechargeDetailsCompanyName =
            view.findViewById(R.id.textViewRechargeDetailsCompanyName)
        mTextViewRechargeDetailsChange = view.findViewById(R.id.textViewRechargeDetailsChange)
        mTextViewRechargeDetailsChange.setOnClickListener(this@RechargeDetailsFragment)

        mTextViewRechargeDetailsRechargeType =
            view.findViewById(R.id.textViewRechargeDetailsRechargeType)
        mTextViewRechargeDetailsChangeMethod =
            view.findViewById(R.id.textViewRechargeDetailsChangeMethod)
        mTextViewRechargeDetailsChangeMethod.setOnClickListener(this@RechargeDetailsFragment)
        mTextViewRechargeDetailsMobileNumber =
            view.findViewById(R.id.textViewRechargeDetailsMobileNumber)

        mTextViewRechargeDetailsSubscriptionIdLabel =
            view.findViewById(R.id.textViewRechargeDetailsSubscriptionIdLabel)
        mTextViewRechargeDetailsSubscriptionId =
            view.findViewById(R.id.textViewRechargeDetailsSubscriptionId)
        mTextViewRechargeSubscriptionIdChange =
            view.findViewById(R.id.textViewRechargeDetailsSubscriptionIdChange)
        mTextViewRechargeSubscriptionIdChange.setOnClickListener(this@RechargeDetailsFragment)
        mViewRechargeDetailsDividerSubscriptionId =
            view.findViewById(R.id.viewRechargeDetailsDividerSubscriptionId)

        mTextInputLayoutRechargeDetailsEnterAmount =
            view.findViewById(R.id.textInputLayoutRechargeDetailsEnterAmount)
        mTextInputEditTextRechargeDetailsEnterAmount =
            view.findViewById(R.id.textInputEditTextRechargeDetailsEnterAmount)

        mTextViewRechargeDetailsSeePlan =
            view.findViewById(R.id.textViewRechargeDetailsSeePlan)
        mTextViewRechargeDetailsSeePlan.setOnClickListener(this@RechargeDetailsFragment)

        mButtonRechargeDetailsProceed = view.findViewById(R.id.buttonRechargeDetailsProceed)
        mButtonRechargeDetailsProceed.setOnClickListener(this@RechargeDetailsFragment)

        mContentLoadingProgressBarRechargeDetails =
            view.findViewById(R.id.contentLoadingProgressBarRechargeDetails)
    }

    override fun onResume() {
        super.onResume()
        onLoadCompanyDetails()
    }

    override fun onDetach() {
        super.onDetach()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textViewRechargeDetailsChange -> view?.let {
                Navigation.findNavController(it).popBackStack(R.id.myAccountFragment, false)
            }
            R.id.textViewRechargeDetailsChangeMethod,
            R.id.textViewRechargeDetailsSubscriptionIdChange -> view?.let {
                Navigation.findNavController(it).popBackStack()
            }
            R.id.textViewRechargeDetailsSeePlan -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_rechargeDetails_to_plans)
            }
            R.id.buttonRechargeDetailsProceed -> isRechargeDetailsValidated()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == PWEStaticDataModel.PWE_REQUEST_CODE) {
                try {
                    val result = data.getStringExtra("result")
                    val payment_response = data.getStringExtra("payment_response")
                    when (result) {
                        PWEStaticDataModel.TXN_SUCCESS_CODE -> paymentResult(
                            getString(R.string.text_label_success),
                            "Payment Successful",
                            customers_unique_id
                        )
                        PWEStaticDataModel.TXN_FAILED_CODE,
                        PWEStaticDataModel.TXN_ERROR_NO_RETRY_CODE,
                        PWEStaticDataModel.TXN_ERROR_RETRY_FAILED_CODE -> paymentResult(
                            getString(R.string.text_label_failure),
                            "Payment Failed!",
                            customers_unique_id
                        )
                        PWEStaticDataModel.TXN_INVALID_INPUT_DATA_CODE -> paymentResult(
                            getString(R.string.text_label_failure),
                            "Payment Failed! Invalid input data.",
                            customers_unique_id
                        )
                        PWEStaticDataModel.TXN_TIMEOUT_CODE -> paymentResult(
                            getString(R.string.text_label_failure),
                            "Sessiom Timeout!",
                            customers_unique_id
                        )
                        PWEStaticDataModel.TXN_USERCANCELLED_CODE,
                        PWEStaticDataModel.TXN_BACKPRESSED_CODE,
                        PWEStaticDataModel.TXN_BANK_BACK_PRESSED_CODE -> paymentResult(
                            getString(R.string.text_label_failure),
                            "Transaction Cancelled!",
                            customers_unique_id
                        )
                        PWEStaticDataModel.TXN_ERROR_SERVER_ERROR_CODE -> paymentResult(
                            getString(R.string.text_label_failure),
                            "An error occured at our server!",
                            customers_unique_id
                        )
                        PWEStaticDataModel.TXN_ERROR_TXN_NOT_ALLOWED_CODE -> paymentResult(
                            getString(R.string.text_label_failure),
                            "There seems problem, transaction not allowed from your bank!",
                            customers_unique_id
                        )
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getSHA512(input: String): String {
        val md: MessageDigest = MessageDigest.getInstance("SHA-512")
        val messageDigest = md.digest(input.toByteArray())
        val no = BigInteger(1, messageDigest)
        var hashtext: String = no.toString(16)

        while (hashtext.length < 32) {
            hashtext = "0$hashtext"
        }

        return hashtext
    }

    private fun onLoadCompanyDetails() {
        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
            mIsMobileRecharge = mSharedPreferenceUtils.getIsMobileRecharge(it)

            Glide.with(it)
                .load(mSharedPreferenceUtils.getRechargeCompanyLogo(it))
                .placeholder(R.drawable.ic_launcher_background)
                .into(mAppCompatImageViewRechargeDetailsCompanyLogo)
            mTextViewRechargeDetailsCompanyName.text =
                mSharedPreferenceUtils.getRechargeCompanyName(it)
            mTextViewRechargeDetailsMobileNumber.text =
                mSharedPreferenceUtils.getRechargeMobileNumber(it)

            when {
                mIsMobileRecharge -> {
                    mTextViewRechargeDetailsSubscriptionIdLabel.visibility = View.GONE
                    mTextViewRechargeDetailsSubscriptionId.visibility = View.GONE
                    mTextViewRechargeSubscriptionIdChange.visibility = View.GONE
                    mViewRechargeDetailsDividerSubscriptionId.visibility = View.GONE

                    mTextInputEditTextRechargeDetailsEnterAmount.isEnabled = false
                    mTextViewRechargeDetailsSeePlan.visibility = View.VISIBLE
                }
                else -> {
                    mTextViewRechargeDetailsSubscriptionIdLabel.visibility = View.VISIBLE
                    mTextViewRechargeDetailsSubscriptionId.visibility = View.VISIBLE
                    mTextViewRechargeSubscriptionIdChange.visibility = View.VISIBLE
                    mViewRechargeDetailsDividerSubscriptionId.visibility = View.VISIBLE

                    mTextViewRechargeDetailsSubscriptionId.text =
                        mSharedPreferenceUtils.getRechargeSubscriptionId(it)

                    mTextInputEditTextRechargeDetailsEnterAmount.isEnabled = true
                    mTextViewRechargeDetailsSeePlan.visibility = View.GONE
                }
            }
            /*mTextInputEditTextRechargeDetailsEnterAmount.setText(
                getString(
                    R.string.text_label_rupees,
                    mSharedPreferenceUtils.getRechargeAmount(it)
                ), TextView.BufferType.EDITABLE
            )*/
            mTextInputEditTextRechargeDetailsEnterAmount.setText(
                mSharedPreferenceUtils.getRechargeAmount(
                    it
                ), TextView.BufferType.EDITABLE
            )
        }
    }

    private fun isRechargeDetailsValidated() {
        when {
            !ValidationUtils.getValidationUtils()
                .isInputEditTextFilledFunc(
                    mTextInputEditTextRechargeDetailsEnterAmount,
                    mTextInputLayoutRechargeDetailsEnterAmount,
                    getString(R.string.text_error_empty_field)
                ) -> return
            else -> {
                mContentLoadingProgressBarRechargeDetails.show()
                onClickRechargeDetails()
            }
        }
    }

    private fun onClickRechargeDetails() {
        if (mIsMobileRecharge) {
            onClickProceedToRechargeMobile()
        } else {
            onClickProceedToRechargeD2H()
        }
    }

    private fun onClickProceedToRechargeMobile() {
        context?.let {
            if (APIClient.isNetworkConnected(it)) {
                APIClient.apiInterface
                    .initiateMobileRecharge(
                        mSharedPreferenceUtils.getLoggedInUser().loginToken,
                        mSharedPreferenceUtils.getRechargeCompanyCode(it)!!,
                        mSharedPreferenceUtils.getRechargeCircleCode(it)!!,
                        mSharedPreferenceUtils.getRechargeMobileNumber(it)!!,
                        mTextInputEditTextRechargeDetailsEnterAmount.text.toString().trim(),
                        mSharedPreferenceUtils.getPlanId(it)!!
                    )
                    .enqueue(object : Callback<InitiateRechargeModel> {
                        override fun onResponse(
                            call: Call<InitiateRechargeModel>,
                            response: Response<InitiateRechargeModel>
                        ) {
                            if (response.isSuccessful) {
                                val mInitiateRechargeModel: InitiateRechargeModel? =
                                    response.body()
                                mContentLoadingProgressBarRechargeDetails.hide()

                                if (mInitiateRechargeModel != null) {
                                    if (mInitiateRechargeModel.mStatus) {

                                        mSalt =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mSalt
                                        merchant_trxnId =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mMerchantTrxnId
                                        merchant_payment_amount =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mMerchantPaymentAmount
                                        merchant_productInfo =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mMerchantProductInfo
                                        customer_firstName =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mCustomerFirstName
                                        customer_email_id =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mCustomerEmailId
                                        customer_phone =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.customer_phone
                                        merchant_key =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mMerchantKey
                                        customers_unique_id =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mCustomersUniqueId
                                        payment_mode = "test"
                                        udf1 = ""
                                        udf2 = ""
                                        udf3 = ""
                                        udf4 = ""
                                        udf5 = ""

                                        val hash_string =
                                            "$merchant_key|$merchant_trxnId|${merchant_payment_amount.toDouble()}|$merchant_productInfo|$customer_firstName|$customer_email_id|$udf1|$udf2|$udf3|$udf4|$udf5||||||$mSalt|$merchant_key"
                                        hash = getSHA512(hash_string)

                                        val intentProceed =
                                            Intent(context, PWECouponsActivity::class.java)
                                        intentProceed.flags =
                                            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT // This is mandatory flag

                                        intentProceed.putExtra("txnid", merchant_trxnId)
                                        intentProceed.putExtra(
                                            "amount",
                                            merchant_payment_amount.toDouble()
                                        )
                                        intentProceed.putExtra("productinfo", merchant_productInfo)
                                        intentProceed.putExtra("firstname", customer_firstName)
                                        intentProceed.putExtra("email", customer_email_id)
                                        intentProceed.putExtra("phone", customer_phone)
                                        intentProceed.putExtra("key", merchant_key)
                                        intentProceed.putExtra("udf1", udf1)
                                        intentProceed.putExtra("udf2", udf2)
                                        intentProceed.putExtra("udf3", udf3)
                                        intentProceed.putExtra("udf4", udf4)
                                        intentProceed.putExtra("udf5", udf5)
                                        intentProceed.putExtra("hash", hash)
                                        intentProceed.putExtra("unique_id", customers_unique_id)
                                        intentProceed.putExtra("pay_mode", payment_mode)
                                        startActivityForResult(
                                            intentProceed,
                                            PWEStaticDataModel.PWE_REQUEST_CODE
                                        )

                                    } else {
                                        AlertDialogUtils.getInstance().showAlert(
                                            it,
                                            R.drawable.ic_warning_black,
                                            mInitiateRechargeModel.mTitle,
                                            mInitiateRechargeModel.mMessage,
                                            getString(android.R.string.ok),
                                            null,
                                            DialogInterface.OnDismissListener {
                                                view?.let { it1 ->
                                                    ValidationUtils.getValidationUtils()
                                                        .hideKeyboardFunc(it1)
                                                }
                                                it.dismiss()
                                            }
                                        )
                                    }
                                } else {
                                    ErrorUtils.logNetworkError(
                                        ServerInvalidResponseException.ERROR_200_BLANK_RESPONSE +
                                                "\nResponse: " + response.toString(),
                                        null
                                    )
                                    AlertDialogUtils.getInstance()
                                        .displayInvalidResponseAlert(it)
                                }
                            }
                        }

                        override fun onFailure(
                            call: Call<InitiateRechargeModel>,
                            t: Throwable
                        ) {
                            ErrorUtils.parseOnFailureException(
                                it,
                                call,
                                t
                            )
                            mContentLoadingProgressBarRechargeDetails.hide()
                        }
                    })
            } else {
                mContentLoadingProgressBarRechargeDetails.hide()
                AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
            }
        }
    }

    private fun onClickProceedToRechargeD2H() {
        context?.let {
            if (APIClient.isNetworkConnected(it)) {
                APIClient.apiInterface
                    .initiateD2HRecharge(
                        mSharedPreferenceUtils.getLoggedInUser().loginToken,
                        mSharedPreferenceUtils.getRechargeCompanyCode(it)!!,
                        mSharedPreferenceUtils.getRechargeSubscriptionId(it)!!,
                        mTextInputEditTextRechargeDetailsEnterAmount.text.toString().trim()
                    )
                    .enqueue(object : Callback<InitiateRechargeModel> {
                        override fun onResponse(
                            call: Call<InitiateRechargeModel>,
                            response: Response<InitiateRechargeModel>
                        ) {
                            if (response.isSuccessful) {
                                val mInitiateRechargeModel: InitiateRechargeModel? =
                                    response.body()
                                mContentLoadingProgressBarRechargeDetails.hide()

                                if (mInitiateRechargeModel != null) {
                                    if (mInitiateRechargeModel.mStatus) {

                                        mSalt =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mSalt
                                        merchant_trxnId =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mMerchantTrxnId
                                        merchant_payment_amount =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mMerchantPaymentAmount
                                        merchant_productInfo =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mMerchantProductInfo
                                        customer_firstName =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mCustomerFirstName
                                        customer_email_id =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mCustomerEmailId
                                        customer_phone =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.customer_phone
                                        merchant_key =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mMerchantKey
                                        customers_unique_id =
                                            mInitiateRechargeModel.mInitiateRechargeDetailsModel.mCustomersUniqueId
                                        payment_mode = "test"
                                        udf1 = ""
                                        udf2 = ""
                                        udf3 = ""
                                        udf4 = ""
                                        udf5 = ""

                                        val hash_string =
                                            "$merchant_key|$merchant_trxnId|${merchant_payment_amount.toDouble()}|$merchant_productInfo|$customer_firstName|$customer_email_id|$udf1|$udf2|$udf3|$udf4|$udf5||||||$mSalt|$merchant_key"
                                        hash = getSHA512(hash_string)

                                        val intentProceed =
                                            Intent(context, PWECouponsActivity::class.java)
                                        intentProceed.flags =
                                            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT // This is mandatory flag

                                        intentProceed.putExtra("txnid", merchant_trxnId)
                                        intentProceed.putExtra(
                                            "amount",
                                            merchant_payment_amount.toDouble()
                                        )
                                        intentProceed.putExtra("productinfo", merchant_productInfo)
                                        intentProceed.putExtra("firstname", customer_firstName)
                                        intentProceed.putExtra("email", customer_email_id)
                                        intentProceed.putExtra("phone", customer_phone)
                                        intentProceed.putExtra("key", merchant_key)
                                        intentProceed.putExtra("udf1", udf1)
                                        intentProceed.putExtra("udf2", udf2)
                                        intentProceed.putExtra("udf3", udf3)
                                        intentProceed.putExtra("udf4", udf4)
                                        intentProceed.putExtra("udf5", udf5)
                                        intentProceed.putExtra("hash", hash)
                                        intentProceed.putExtra("unique_id", customers_unique_id)
                                        intentProceed.putExtra("pay_mode", payment_mode)
                                        startActivityForResult(
                                            intentProceed,
                                            PWEStaticDataModel.PWE_REQUEST_CODE
                                        )

                                    } else {
                                        AlertDialogUtils.getInstance().showAlert(
                                            it,
                                            R.drawable.ic_warning_black,
                                            mInitiateRechargeModel.mTitle,
                                            mInitiateRechargeModel.mMessage,
                                            getString(android.R.string.ok),
                                            null,
                                            DialogInterface.OnDismissListener {
                                                view?.let { it1 ->
                                                    ValidationUtils.getValidationUtils()
                                                        .hideKeyboardFunc(it1)
                                                }
                                                it.dismiss()
                                            }
                                        )
                                    }
                                } else {
                                    ErrorUtils.logNetworkError(
                                        ServerInvalidResponseException.ERROR_200_BLANK_RESPONSE +
                                                "\nResponse: " + response.toString(),
                                        null
                                    )
                                    AlertDialogUtils.getInstance()
                                        .displayInvalidResponseAlert(it)
                                }
                            }
                        }

                        override fun onFailure(
                            call: Call<InitiateRechargeModel>,
                            t: Throwable
                        ) {
                            ErrorUtils.parseOnFailureException(
                                it,
                                call,
                                t
                            )
                            mContentLoadingProgressBarRechargeDetails.hide()
                        }
                    })
            } else {
                mContentLoadingProgressBarRechargeDetails.hide()
                AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
            }
        }
    }

    private fun paymentResult(
        mResponse: String,
        mResponseText: String,
        mUniqueReferenceId: String
    ) {

    }
}