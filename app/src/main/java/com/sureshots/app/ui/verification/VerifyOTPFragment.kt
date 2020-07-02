package com.sureshots.app.ui.verification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import com.sureshots.app.R

/**
 * A simple [Fragment] subclass.
 */
class VerifyOTPFragment : Fragment(R.layout.fragment_verify_o_t_p), View.OnClickListener {

    private lateinit var mTextViewMobile: TextView

    private lateinit var mEditTextOTPOne: EditText
    private lateinit var mEditTextOTPTwo: EditText
    private lateinit var mEditTextOTPThree: EditText
    private lateinit var mEditTextOTPFour: EditText

    private lateinit var mButtonOTPVerifyAndProceed: Button

    private lateinit var mTextViewOTPTime: TextView
    private lateinit var mTextViewResend: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewMobile = view.findViewById(R.id.textViewMobile)

        mEditTextOTPOne = view.findViewById(R.id.editTextOTPOne)
        mEditTextOTPTwo = view.findViewById(R.id.editTextOTPTwo)
        mEditTextOTPThree = view.findViewById(R.id.editTextOTPThree)
        mEditTextOTPFour = view.findViewById(R.id.editTextOTPFour)

        mButtonOTPVerifyAndProceed = view.findViewById(R.id.buttonOTPVerifyAndProceed)
        mButtonOTPVerifyAndProceed.setOnClickListener(this@VerifyOTPFragment)

        mTextViewOTPTime = view.findViewById(R.id.textViewOTPTime)

        mTextViewResend = view.findViewById(R.id.textViewResend)
        mTextViewResend.setOnClickListener(this@VerifyOTPFragment)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonOTPVerifyAndProceed -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_verifyOTP_to_dashboard)
            }
        }
    }
}