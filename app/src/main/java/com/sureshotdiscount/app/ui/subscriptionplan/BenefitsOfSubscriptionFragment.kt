package com.sureshotdiscount.app.ui.subscriptionplan

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.easebuzz.payment.kit.PWECouponsActivity
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import datamodels.PWEStaticDataModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest

@Suppress("DEPRECATION")
class BenefitsOfSubscriptionFragment : Fragment(R.layout.fragment_benefits_of_subscription),
    View.OnClickListener {

    private lateinit var mTextViewBenefitsOfSubscriptionMessage: TextView
    private lateinit var mWebViewBenefitsOfSubscription: WebView
    private lateinit var mButtonBenefitsOfSubscriptionPayToSubscribeNow: Button

    private lateinit var mContentLoadingProgressBarBenefitsOfSubscription: ContentLoadingProgressBar

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

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
    private lateinit var mSalt: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewBenefitsOfSubscriptionMessage =
            view.findViewById(R.id.textViewBenefitsOfSubscriptionMessage)

        mWebViewBenefitsOfSubscription =
            view.findViewById(R.id.webViewBenefitsOfSubscription)
        mWebViewBenefitsOfSubscription.settings.lightTouchEnabled = true
        mWebViewBenefitsOfSubscription.settings.javaScriptEnabled = true
        mWebViewBenefitsOfSubscription.settings.setGeolocationEnabled(true)
        mWebViewBenefitsOfSubscription.isSoundEffectsEnabled = true

        mButtonBenefitsOfSubscriptionPayToSubscribeNow =
            view.findViewById(R.id.buttonBenefitsOfSubscriptionPayToSubscribeNow)
        mButtonBenefitsOfSubscriptionPayToSubscribeNow.setOnClickListener(this)

        mContentLoadingProgressBarBenefitsOfSubscription =
            view.findViewById(R.id.contentLoadingProgressBarBenefitsOfSubscription)
    }

    override fun onResume() {
        super.onResume()
        mContentLoadingProgressBarBenefitsOfSubscription.show()
        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
        onLoadWebView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonBenefitsOfSubscriptionPayToSubscribeNow -> view?.let {
                onClickPayToSubscribe()
            }
        }
    }

    private fun onLoadWebView() {
        context?.let {
            mWebViewBenefitsOfSubscription.webViewClient = object : WebViewClient() {

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    mWebViewBenefitsOfSubscription.loadData(
                        getString(R.string.text_error_unable_to_load),
                        "text/html",
                        "UTF-8"
                    )
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url?.startsWith("tel")!!) {
                        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(url)))
                        return true
                    } else if (url.startsWith("mailto")) {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "message/rfc822"
                        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(url.substring(7, url.length)))
                        startActivity(Intent.createChooser(intent, "Send Email"))
                        return true
                    } else if (url.startsWith("http") || url.startsWith("https")) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(Intent.createChooser(intent, "Open web page with"))
                        return true
                    } else {
                        return super.shouldOverrideUrlLoading(view, url)
                    }
                }
            }
            onLoadBenefitOfSubscription()
        }
    }

    private fun onLoadBenefitOfSubscription() {
        context?.let {
            if (APIClient.isNetworkConnected(it)) {
                APIClient.apiInterface
                    .getSubscriptionPlan(
                        mSharedPreferenceUtils.getLoggedInUser().loginToken
                    )
                    .enqueue(object : Callback<SubscriptionPlanModel> {
                        override fun onResponse(
                            call: Call<SubscriptionPlanModel>,
                            response: Response<SubscriptionPlanModel>
                        ) {
                            if (response.isSuccessful) {
                                val mSubscriptionPlanModel: SubscriptionPlanModel? = response.body()
                                mContentLoadingProgressBarBenefitsOfSubscription.hide()

                                if (mSubscriptionPlanModel != null) {
                                    if (mSubscriptionPlanModel.mStatus) {
                                        mTextViewBenefitsOfSubscriptionMessage.visibility =
                                            View.VISIBLE
                                        mTextViewBenefitsOfSubscriptionMessage.text =
                                            mSubscriptionPlanModel.mMessage
                                        mWebViewBenefitsOfSubscription.visibility = View.GONE
                                        mButtonBenefitsOfSubscriptionPayToSubscribeNow.visibility =
                                            View.GONE
                                    } else {
                                        mTextViewBenefitsOfSubscriptionMessage.visibility =
                                            View.GONE
                                        mWebViewBenefitsOfSubscription.visibility = View.VISIBLE
                                        mButtonBenefitsOfSubscriptionPayToSubscribeNow.visibility =
                                            View.VISIBLE

                                        mWebViewBenefitsOfSubscription.loadUrl(
                                            mSubscriptionPlanModel.mLink
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

                        override fun onFailure(call: Call<SubscriptionPlanModel>, t: Throwable) {
                            ErrorUtils.parseOnFailureException(
                                it,
                                call,
                                t
                            )
                            mContentLoadingProgressBarBenefitsOfSubscription.hide()
                        }
                    })
            } else {
                mContentLoadingProgressBarBenefitsOfSubscription.hide()
                AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
            }
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
                        /*{
                        payment_response?.let {
                            val mainObject = JSONObject(payment_response)
                            val pay_txnid = mainObject.getString("txnid")
                            val pay_firstname = mainObject.getString("firstname")
                            val pay_status = mainObject.getString("status")
                            val pay_phone = mainObject.getString("phone")
                            val pay_amount = mainObject.getString("amount")
                            view?.let {
                                Navigation.findNavController(it)
                                    .navigate(
                                        BenefitsOfSubscriptionFragmentDirections.actionBenefitsOfSubscriptionToPaymentSuccessful(
                                            true,
                                            "Payment Successful!",
                                            "Subscription",
                                            "8433644897",
                                            "SUB-VUD22QED01202009231728HDZ"
                                        )
                                    )
                            }
                        }
                    }*/
                        PWEStaticDataModel.TXN_FAILED_CODE,
                        PWEStaticDataModel.TXN_ERROR_NO_RETRY_CODE,
                        PWEStaticDataModel.TXN_ERROR_RETRY_FAILED_CODE -> paymentResult(
                            getString(R.string.text_label_failure),
                            "Payment Failed!",
                            customers_unique_id
                        )/*Toast.makeText(
                            context,
                            "Payment Failed!",
                            Toast.LENGTH_SHORT
                        ).show()*/
                        PWEStaticDataModel.TXN_INVALID_INPUT_DATA_CODE -> paymentResult(
                            getString(R.string.text_label_failure),
                            "Payment Failed! Invalid input data.",
                            customers_unique_id
                        )
                        /*Toast.makeText(
                        context,
                        "Payment Failed, Invalid input data",
                        Toast.LENGTH_SHORT
                    ).show()*/
                        PWEStaticDataModel.TXN_TIMEOUT_CODE -> paymentResult(
                            getString(R.string.text_label_failure),
                            "Sessiom Timeout!",
                            customers_unique_id
                        )
                        /*Toast.makeText(
                        context,
                        "Sessiom Timeout!",
                        Toast.LENGTH_SHORT
                    ).show()*/
                        PWEStaticDataModel.TXN_USERCANCELLED_CODE,
                        PWEStaticDataModel.TXN_BACKPRESSED_CODE,
                        PWEStaticDataModel.TXN_BANK_BACK_PRESSED_CODE -> paymentResult(
                            getString(R.string.text_label_failure),
                            "Transaction Cancelled!",
                            customers_unique_id
                        )
                        /*Toast.makeText(
                        context,
                        "Transaction Cancelled!",
                        Toast.LENGTH_SHORT
                    ).show()*/
                        PWEStaticDataModel.TXN_ERROR_SERVER_ERROR_CODE -> paymentResult(
                            getString(R.string.text_label_failure),
                            "An error occured at our server!",
                            customers_unique_id
                        )
                        /*Toast.makeText(
                        context,
                        "An error occured at our server!",
                        Toast.LENGTH_SHORT
                    ).show()*/
                        PWEStaticDataModel.TXN_ERROR_TXN_NOT_ALLOWED_CODE -> paymentResult(
                            getString(R.string.text_label_failure),
                            "There seems problem, transaction not allowed from your bank!",
                            customers_unique_id
                        )
                        /*Toast.makeText(
                            context,
                            "There seems problem, transaction not allowed from your bank!",
                            Toast.LENGTH_SHORT
                        ).show()*/
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

        // Convert byte array into signum representation
        val no = BigInteger(1, messageDigest)

        // Convert message digest into hex value
        var hashtext: String = no.toString(16)

        // Add preceding 0s to make it 32 bit
        while (hashtext.length < 32) {
            hashtext = "0$hashtext"
        }

        // return the HashText
        return hashtext
    }

    private fun onClickPayToSubscribe() {
        context?.let {
            if (APIClient.isNetworkConnected(it)) {
                APIClient.apiInterface
                    .initiateSubscription(
                        mSharedPreferenceUtils.getLoggedInUser().loginToken
                    )
                    .enqueue(object : Callback<BenefitsOfSubscriptionModel> {
                        override fun onResponse(
                            call: Call<BenefitsOfSubscriptionModel>,
                            response: Response<BenefitsOfSubscriptionModel>
                        ) {
                            if (response.isSuccessful) {
                                val mBenefitsOfSubscriptionModel: BenefitsOfSubscriptionModel? =
                                    response.body()
                                mContentLoadingProgressBarBenefitsOfSubscription.hide()

                                if (mBenefitsOfSubscriptionModel != null) {
                                    if (mBenefitsOfSubscriptionModel.mStatus) {

                                        mSalt = mBenefitsOfSubscriptionModel.mData.mSalt
                                        merchant_trxnId =
                                            mBenefitsOfSubscriptionModel.mData.mMerchantTrxnId
                                        merchant_payment_amount =
                                            mBenefitsOfSubscriptionModel.mData.mMerchantPaymentAmount
                                        merchant_productInfo =
                                            mBenefitsOfSubscriptionModel.mData.mMerchantProductInfo
                                        customer_firstName =
                                            mBenefitsOfSubscriptionModel.mData.mCustomerFirstName
                                        customer_email_id =
                                            mBenefitsOfSubscriptionModel.mData.mCustomerEmailId
                                        customer_phone =
                                            mBenefitsOfSubscriptionModel.mData.customer_phone
                                        merchant_key =
                                            mBenefitsOfSubscriptionModel.mData.mMerchantKey
                                        customers_unique_id =
                                            mBenefitsOfSubscriptionModel.mData.mCustomersUniqueId
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
                                            mBenefitsOfSubscriptionModel.mTitle,
                                            mBenefitsOfSubscriptionModel.message,
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
                            call: Call<BenefitsOfSubscriptionModel>,
                            t: Throwable
                        ) {
                            ErrorUtils.parseOnFailureException(
                                it,
                                call,
                                t
                            )
                            mContentLoadingProgressBarBenefitsOfSubscription.hide()
                        }
                    })
            } else {
                mContentLoadingProgressBarBenefitsOfSubscription.hide()
                AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
            }
        }
    }

    private fun paymentResult(
        mResponse: String,
        mResponseText: String,
        mUniqueReferenceId: String
    ) {
        context?.let {
            if (APIClient.isNetworkConnected(it)) {
                APIClient.apiInterface
                    .paymentResult(
                        mSharedPreferenceUtils.getLoggedInUser().loginToken,
                        mResponse,
                        mResponseText,
                        mUniqueReferenceId
                    )
                    .enqueue(object : Callback<PaymentResultModel> {
                        override fun onResponse(
                            call: Call<PaymentResultModel>,
                            response: Response<PaymentResultModel>
                        ) {
                            if (response.isSuccessful) {
                                val mPaymentResultModel: PaymentResultModel? = response.body()
                                mContentLoadingProgressBarBenefitsOfSubscription.hide()

                                if (mPaymentResultModel != null) {
                                    if (mPaymentResultModel.mStatus) {
                                        view?.let {
                                            Navigation.findNavController(it)
                                                .navigate(
                                                    BenefitsOfSubscriptionFragmentDirections.actionBenefitsOfSubscriptionToPaymentSuccessful(
                                                        mPaymentResultModel.mPaymentResultDetailsModel.mPaymentStatus,
                                                        mResponseText,
                                                        mPaymentResultModel.mPaymentResultDetailsModel.mRechargeFor,
                                                        mPaymentResultModel.mPaymentResultDetailsModel.mMobileNumber,
                                                        mPaymentResultModel.mPaymentResultDetailsModel.mOrderId
                                                    )
                                                )
                                        }
                                    } else {
                                        AlertDialogUtils.getInstance().showAlert(
                                            it,
                                            R.drawable.ic_warning_black,
                                            mPaymentResultModel.mTitle,
                                            mPaymentResultModel.mMessage,
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

                        override fun onFailure(call: Call<PaymentResultModel>, t: Throwable) {
                            ErrorUtils.parseOnFailureException(
                                it,
                                call,
                                t
                            )
                            mContentLoadingProgressBarBenefitsOfSubscription.hide()
                        }
                    })
            } else {
                mContentLoadingProgressBarBenefitsOfSubscription.hide()
                AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
            }
        }
    }
}