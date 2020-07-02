package com.sureshots.app.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
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

    private lateinit var mButtonContinue: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTextInputLayoutMobileNumber = view.findViewById(R.id.textInputLayoutMobileNumber)
        mTextInputEditTextMobileNumber = view.findViewById(R.id.textInputEditTextMobileNumber)

        mButtonContinue = view.findViewById(R.id.buttonContinue)
        mButtonContinue.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonContinue -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_signUp_to_verifyOTP)
            }
        }
    }
}
