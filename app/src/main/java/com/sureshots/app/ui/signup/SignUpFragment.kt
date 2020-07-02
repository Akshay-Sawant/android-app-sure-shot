package com.sureshots.app.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sureshots.app.R

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment(R.layout.fragment_sign_up), View.OnClickListener {

    private lateinit var mTextInputLayoutMobileNumber: TextInputLayout
    private lateinit var mTextInputEditTextMobileNumber: TextInputEditText

    private lateinit var mTextInputLayoutSignUpReferralId: TextInputLayout
    private lateinit var mTextInputEditTextSignUpReferralId: TextInputEditText

    private lateinit var mButtonContinue: Button

    private lateinit var mTextViewSignUpHaveAccount: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextInputLayoutMobileNumber = view.findViewById(R.id.textInputLayoutMobileNumber)
        mTextInputEditTextMobileNumber = view.findViewById(R.id.textInputEditTextMobileNumber)

        mTextInputLayoutSignUpReferralId = view.findViewById(R.id.textInputLayoutReferralsId)
        mTextInputEditTextSignUpReferralId =
            view.findViewById(R.id.textInputEditTextSignUpReferralId)

        mButtonContinue = view.findViewById(R.id.buttonContinue)
        mButtonContinue.setOnClickListener(this)

        mTextViewSignUpHaveAccount = view.findViewById(R.id.textViewSignUpHaveAccount)
        mTextViewSignUpHaveAccount.setOnClickListener(this@SignUpFragment)
    }

    override fun onClick(v: View?) {
        view?.let {
            when (v?.id) {
                R.id.buttonContinue -> Navigation.findNavController(it)
                    .navigate(R.id.action_signUp_to_verifyOTP)
                R.id.textViewSignUpHaveAccount -> Navigation.findNavController(it)
                    .navigate(R.id.action_signUp_to_signIn)
            }
        }
    }
}