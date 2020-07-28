package com.sureshotdiscount.app.ui.prepaid

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

class PrepaidFragment : Fragment(R.layout.fragment_prepaid), View.OnClickListener {

    private lateinit var mAppCompatImageViewPrepaidCompanyLogo: AppCompatImageView
    private lateinit var mTextViewPrepaidCompanyName: TextView
    private lateinit var mTextViewPrepaidChange: TextView

    private lateinit var mTextInputLayoutPrepaidMobileNumber: TextInputLayout
    private lateinit var mTextInputEditTextPrepaidMobileNumber: TextInputEditText

    private lateinit var mButtonPrepaidProceed: Button

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAppCompatImageViewPrepaidCompanyLogo =
            view.findViewById(R.id.appCompatImageViewPrepaidCompanyLogo)
        mTextViewPrepaidCompanyName = view.findViewById(R.id.textViewPrepaidCompanyName)
        mTextViewPrepaidChange = view.findViewById(R.id.textViewPrepaidChange)
        mTextViewPrepaidChange.setOnClickListener(this@PrepaidFragment)

        mTextInputLayoutPrepaidMobileNumber =
            view.findViewById(R.id.textInputLayoutPrepaidMobileNumber)
        mTextInputEditTextPrepaidMobileNumber =
            view.findViewById(R.id.textInputEditTextPrepaidMobileNumber)

        mButtonPrepaidProceed = view.findViewById(R.id.buttonPrepaidProceed)
        mButtonPrepaidProceed.setOnClickListener(this@PrepaidFragment)
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
            R.id.textViewPrepaidChange -> view?.let {
                Navigation.findNavController(it).popBackStack()
            }
            R.id.buttonPrepaidProceed -> isPrepaidValidated()
        }
    }

    private fun onLoadCompanyDetails() {
        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)

            Glide.with(this@PrepaidFragment)
                .load(mSharedPreferenceUtils.getRechargeCompanyLogo(it))
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(mAppCompatImageViewPrepaidCompanyLogo)
            mTextViewPrepaidCompanyName.text = mSharedPreferenceUtils.getRechargeCompanyName(it)
        }
    }

    private fun isPrepaidValidated() {
        when {
            !ValidationUtils.getValidationUtils()
                .isInputEditTextMobileFunc(
                    mTextInputLayoutPrepaidMobileNumber,
                    mTextInputEditTextPrepaidMobileNumber,
                    getString(R.string.text_error_mobile)
                ) -> return
            else -> {
                mSharedPreferenceUtils.saveRechargeMobileNumber(
                    mTextInputEditTextPrepaidMobileNumber.text.toString().trim()
                )
                mSharedPreferenceUtils.saveRechargeType("Prepaid")
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_recharge_to_rechargeDetails)
                }
            }
        }
    }
}