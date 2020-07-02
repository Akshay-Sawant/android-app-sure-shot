package com.sureshots.app.ui.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sureshots.app.R

class SignInFragment : Fragment(R.layout.fragment_sign_in), View.OnClickListener {

    private lateinit var mTextInputLayoutSignInMobileNumber: TextInputLayout
    private lateinit var mTextInputEditTextSignInMobileNumber: TextInputEditText

    private lateinit var mButtonSignInContinue: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextInputLayoutSignInMobileNumber = view
            .findViewById(R.id.textInputLayoutSignInMobileNumber)
        mTextInputEditTextSignInMobileNumber = view
            .findViewById(R.id.textInputEditTextSignInMobileNumber)

        mButtonSignInContinue = view.findViewById(R.id.buttonSignInContinue)
        mButtonSignInContinue.setOnClickListener(this@SignInFragment)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonSignInContinue -> {

            }
        }
    }
}