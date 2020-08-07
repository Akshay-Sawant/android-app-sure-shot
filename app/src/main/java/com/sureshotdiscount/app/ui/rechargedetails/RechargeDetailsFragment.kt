package com.sureshotdiscount.app.ui.rechargedetails

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.easebuzz.payment.kit.PWECouponsActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import datamodels.PWEStaticDataModel
import org.json.JSONObject
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.properties.Delegates

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

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils
    private var mIsMobileRecharge by Delegates.notNull<Boolean>()

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
    }

    override fun onResume() {
        super.onResume()
        onLoadCompanyDetails()
        onLoadPaymentGatewayDetails()
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
                        PWEStaticDataModel.TXN_SUCCESS_CODE -> {
                            payment_response?.let {
                                val mainObject = JSONObject(payment_response)
                                val pay_txnid = mainObject.getString("txnid")
                                val pay_firstname = mainObject.getString("firstname")
                                val pay_status = mainObject.getString("status")
                                val pay_phone = mainObject.getString("phone")
                                val pay_amount = mainObject.getString("amount")
                                /*startActivity(
                                    PaymentSuccessActivity.newIntent(
                                        this,
                                        pay_txnid,
                                        pay_firstname,
                                        pay_status,
                                        pay_phone,
                                        pay_amount
                                    )
                                )*/
                                Toast.makeText(
                                    context,
                                    "$pay_txnid $pay_firstname $pay_status $pay_phone $pay_amount",
                                    Toast.LENGTH_SHORT
                                ).show()
                                view?.let {
                                    Navigation.findNavController(it)
                                        .navigate(R.id.action_rechargeDetails_to_paymentSuccessful)
                                }
                            }
                        }
                        PWEStaticDataModel.TXN_FAILED_CODE,
                        PWEStaticDataModel.TXN_ERROR_NO_RETRY_CODE,
                        PWEStaticDataModel.TXN_ERROR_RETRY_FAILED_CODE -> Toast.makeText(
                            context,
                            "Payment Failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                        PWEStaticDataModel.TXN_INVALID_INPUT_DATA_CODE -> Toast.makeText(
                            context,
                            "Payment Failed, Invalid input data",
                            Toast.LENGTH_SHORT
                        ).show()
                        PWEStaticDataModel.TXN_TIMEOUT_CODE -> Toast.makeText(
                            context,
                            "Sessiom Timeout!",
                            Toast.LENGTH_SHORT
                        ).show()
                        PWEStaticDataModel.TXN_USERCANCELLED_CODE,
                        PWEStaticDataModel.TXN_BACKPRESSED_CODE,
                        PWEStaticDataModel.TXN_BANK_BACK_PRESSED_CODE -> Toast.makeText(
                            context,
                            "Transaction Cancelled!",
                            Toast.LENGTH_SHORT
                        ).show()
                        PWEStaticDataModel.TXN_ERROR_SERVER_ERROR_CODE -> Toast.makeText(
                            context,
                            "An error occured at our server!",
                            Toast.LENGTH_SHORT
                        ).show()
                        PWEStaticDataModel.TXN_ERROR_TXN_NOT_ALLOWED_CODE -> Toast.makeText(
                            context,
                            "There seems problem, transaction not allowed from your bank!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onLoadPaymentGatewayDetails() {
        val salt = "DAH88E3UWQ"
        merchant_trxnId = "1001"
        merchant_payment_amount = "100"
        merchant_productInfo = "Headphones"
        customer_firstName = "Suraj"
        customer_email_id = "suraj@innovins.com"
        customer_phone = "9970783832"
        merchant_key = "2PBP7IABZ2"
        customers_unique_id = "199SS"
        payment_mode = "test"
        udf1 = ""
        udf2 = ""
        udf3 = ""
        udf4 = ""
        udf5 = ""
        val hash_string =
            "$merchant_key|$merchant_trxnId|${merchant_payment_amount.toDouble()}|$merchant_productInfo|$customer_firstName|$customer_email_id|$udf1|$udf2|$udf3|$udf4|$udf5||||||$salt|$merchant_key"
        hash = getSHA512(hash_string)
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
            mTextInputEditTextRechargeDetailsEnterAmount.setText(
                getString(
                    R.string.text_label_rupees,
                    mSharedPreferenceUtils.getRechargeAmount(it)
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
                /*view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_rechargeDetails_to_paymentSuccessful)
                }*/
                onClickRechargeDetails()
            }
        }
    }

    private fun onClickRechargeDetails() {
        val intentProceed =
            Intent(context, PWECouponsActivity::class.java)
        intentProceed.flags =
            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT // This is mandatory flag

        intentProceed.putExtra("txnid", merchant_trxnId)
        intentProceed.putExtra("amount", merchant_payment_amount.toDouble())
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
        startActivityForResult(intentProceed, PWEStaticDataModel.PWE_REQUEST_CODE)
    }
}