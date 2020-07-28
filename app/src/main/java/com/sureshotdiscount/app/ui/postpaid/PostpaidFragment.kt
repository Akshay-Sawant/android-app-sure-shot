package com.sureshotdiscount.app.ui.postpaid

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

class PostpaidFragment : Fragment(R.layout.fragment_postpaid), View.OnClickListener {

    private lateinit var mAppCompatImageViewPostPaidCompanyLogo: AppCompatImageView
    private lateinit var mTextViewPostPaidCompanyName: TextView
    private lateinit var mTextViewPostPaidChange: TextView

    private lateinit var mTextInputLayoutPostPaidMobileNumber: TextInputLayout
    private lateinit var mTextInputEditTextPostPaidMobileNumber: TextInputEditText

    private lateinit var mButtonPostPaidProceed: Button
    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAppCompatImageViewPostPaidCompanyLogo =
            view.findViewById(R.id.appCompatImageViewPostPaidCompanyLogo)
        mTextViewPostPaidCompanyName = view.findViewById(R.id.textViewPostPaidCompanyName)
        mTextViewPostPaidChange = view.findViewById(R.id.textViewPostPaidChange)
        mTextViewPostPaidChange.setOnClickListener(this@PostpaidFragment)

        mTextInputLayoutPostPaidMobileNumber =
            view.findViewById(R.id.textInputLayoutPostPaidMobileNumber)
        mTextInputEditTextPostPaidMobileNumber =
            view.findViewById(R.id.textInputEditTextPostPaidMobileNumber)

        mButtonPostPaidProceed = view.findViewById(R.id.buttonPostPaidProceed)
        mButtonPostPaidProceed.setOnClickListener(this@PostpaidFragment)
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
            R.id.textViewPostPaidChange -> view?.let {
                Navigation.findNavController(it).popBackStack()
            }
            R.id.buttonPostPaidProceed -> view?.let {
//                isPostpaidValidated()
                Navigation.findNavController(it)
                    .navigate(R.id.action_recharge_to_rechargeDetails)
            }
        }
    }

    private fun onLoadCompanyDetails() {
        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)

            Glide.with(it)
                .load(mSharedPreferenceUtils.getRechargeCompanyLogo(it))
                .placeholder(R.drawable.launcher_white)
                .into(mAppCompatImageViewPostPaidCompanyLogo)
            mTextViewPostPaidCompanyName.text = mSharedPreferenceUtils.getRechargeCompanyName(it)
        }
    }

    private fun isPostpaidValidated() {
        when {
            !ValidationUtils.getValidationUtils()
                .isInputEditTextMobileFunc(
                    mTextInputLayoutPostPaidMobileNumber,
                    mTextInputEditTextPostPaidMobileNumber,
                    getString(R.string.text_error_mobile)
                ) -> return
            else -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_recharge_to_rechargeDetails)
                }
            }
        }
    }
}