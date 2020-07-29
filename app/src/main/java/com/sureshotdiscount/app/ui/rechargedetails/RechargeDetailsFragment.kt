package com.sureshotdiscount.app.ui.rechargedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
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
                .isInputEditTextMobileFunc(
                    mTextInputLayoutRechargeDetailsEnterAmount,
                    mTextInputEditTextRechargeDetailsEnterAmount,
                    getString(R.string.text_error_empty_field)
                ) -> return
            else -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_rechargeDetails_to_paymentSuccessful)
                }
            }
        }
    }
}