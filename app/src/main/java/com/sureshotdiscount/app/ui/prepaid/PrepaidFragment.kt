package com.sureshotdiscount.app.ui.prepaid

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
class PrepaidFragment : Fragment(R.layout.fragment_prepaid), View.OnClickListener {

    private lateinit var mCircleImageViewPrepaidCompanyLogo: CircleImageView
    private lateinit var mTextViewPrepaidCompanyName: TextView
    private lateinit var mTextViewPrepaidChange: TextView

    private lateinit var mTextInputLayoutPrepaidMobileNumber: TextInputLayout
    private lateinit var mTextInputEditTextPrepaidMobileNumber: TextInputEditText

    private lateinit var mButtonPrepaidProceed: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCircleImageViewPrepaidCompanyLogo =
            view.findViewById(R.id.circleImageViewPrepaidCompanyLogo)
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textViewPrepaidChange -> view?.let {
                Navigation.findNavController(it).popBackStack()
            }
            R.id.buttonPrepaidProceed -> view?.let {
//                isPrepaidValidated()
                Navigation.findNavController(it)
                    .navigate(R.id.action_recharge_to_rechargeDetails)
            }
        }
    }

    /*private fun onLoadPrepaid() {
        Glide.with(this@PrepaidFragment)
            .load("")
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(mCircleImageViewPrepaidCompanyLogo)
        mTextViewPrepaidCompanyName.text
    }*/

    private fun isPrepaidValidated() {
        when {
            !ValidationUtils.getValidationUtils()
                .isInputEditTextMobileFunc(
                    mTextInputLayoutPrepaidMobileNumber,
                    mTextInputEditTextPrepaidMobileNumber,
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
