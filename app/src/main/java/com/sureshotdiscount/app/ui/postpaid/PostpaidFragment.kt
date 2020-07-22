package com.sureshotdiscount.app.ui.postpaid

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
class PostpaidFragment : Fragment(R.layout.fragment_postpaid), View.OnClickListener {

    private lateinit var mCircleImageViewPostPaid: CircleImageView
    private lateinit var mTextViewPostPaidCompanyName: TextView
    private lateinit var mTextViewPostPaidChange: TextView

    private lateinit var mTextInputLayoutPostPaidMobileNumber: TextInputLayout
    private lateinit var mTextInputEditTextPostPaidMobileNumber: TextInputEditText

    private lateinit var mButtonPostPaidProceed: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCircleImageViewPostPaid = view.findViewById(R.id.circleImageViewPostPaidCompanyLogo)
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