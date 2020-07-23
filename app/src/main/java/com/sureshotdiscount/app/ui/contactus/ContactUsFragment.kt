package com.sureshotdiscount.app.ui.contactus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.utils.others.ValidationUtils

class ContactUsFragment : Fragment(R.layout.fragment_contact_us), View.OnClickListener {

    private lateinit var mTextInputLayoutContactUsMessage: TextInputLayout
    private lateinit var mTextInputEditTextContactUsMessage: TextInputEditText

    private lateinit var mButtonContactUsSubmit: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextInputLayoutContactUsMessage = view.findViewById(R.id.textInputLayoutContactUsMessage)
        mTextInputEditTextContactUsMessage =
            view.findViewById(R.id.textInputEditTextContactUsMessage)

        mButtonContactUsSubmit = view.findViewById(R.id.buttonContactUsSubmit)
        mButtonContactUsSubmit.setOnClickListener(this@ContactUsFragment)
    }

    override fun onResume() {
        super.onResume()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
    }

    override fun onDetach() {
        super.onDetach()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonContactUsSubmit -> {
//                isContactUsValidated()
            }
        }
    }

    private fun isContactUsValidated() {
        when {
            !ValidationUtils.getValidationUtils().isInputEditTextFilledFunc(
                mTextInputEditTextContactUsMessage,
                mTextInputLayoutContactUsMessage,
                getString(R.string.text_error_empty_field)
            ) -> return
            else -> {

            }
        }
    }
}