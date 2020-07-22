package com.sureshotdiscount.app.ui.rechargedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.utils.others.ValidationUtils
import de.hdodenhof.circleimageview.CircleImageView

/**
 * A simple [Fragment] subclass.
 */
class RechargeDetailsFragment : Fragment(R.layout.fragment_recharge_details), View.OnClickListener {

    private lateinit var mCircleImageViewRechargeDetailsCompanyLogo: CircleImageView
    private lateinit var mTextViewRechargeDetailsCompanyName: TextView
    private lateinit var mTextViewRechargeDetailsChange: TextView

    private lateinit var mTextViewRechargeDetailsRechargeType: TextView
    private lateinit var mTextViewRechargeDetailsChangeMethod: TextView
    private lateinit var mTextViewRechargeDetailsMobileNumber: TextView

    private lateinit var mTextInputLayoutRechargeDetailsEnterAmount: TextInputLayout
    private lateinit var mTextInputEditTextRechargeDetailsEnterAmount: TextInputEditText
    private lateinit var mTextViewRechargeDetailsSeePlan: TextView

    private lateinit var mButtonRechargeDetailsProceed: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCircleImageViewRechargeDetailsCompanyLogo =
            view.findViewById(R.id.circleImageViewRechargeDetailsCompanyLogo)
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

        mTextInputLayoutRechargeDetailsEnterAmount =
            view.findViewById(R.id.textInputLayoutRechargeDetailsEnterAmount)
        mTextInputEditTextRechargeDetailsEnterAmount =
            view.findViewById(R.id.textInputEditTextRechargeDetailsEnterAmount)
        mTextInputEditTextRechargeDetailsEnterAmount.isEnabled = false

        mTextViewRechargeDetailsSeePlan =
            view.findViewById(R.id.textViewRechargeDetailsSeePlan)
        mTextViewRechargeDetailsSeePlan.setOnClickListener(this@RechargeDetailsFragment)

        mButtonRechargeDetailsProceed = view.findViewById(R.id.buttonRechargeDetailsProceed)
        mButtonRechargeDetailsProceed.setOnClickListener(this@RechargeDetailsFragment)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textViewRechargeDetailsChange -> view?.let {
                Navigation.findNavController(it).popBackStack(R.id.dashboardFragment, false)
            }
            R.id.textViewRechargeDetailsChangeMethod -> view?.let {
                Navigation.findNavController(it).popBackStack()
            }
            R.id.buttonRechargeDetailsProceed -> view?.let {
//                isRechargeDetailsValidated()
                Navigation.findNavController(it)
                    .navigate(R.id.action_rechargeDetails_to_paymentSuccessful)
            }
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