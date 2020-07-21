package com.sureshotdiscount.app.utils.others

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class ValidationUtils {

    companion object {
        private var VALIDATION_UTILS_INSTANCE: ValidationUtils? = null

        fun getValidationUtils(): ValidationUtils {
            synchronized(this@Companion) {
                var mValidationUtilsInstance = VALIDATION_UTILS_INSTANCE
                if (mValidationUtilsInstance == null) {
                    mValidationUtilsInstance = ValidationUtils()
                }
                return mValidationUtilsInstance
            }
        }
    }

    private var mValue: String? = ""
    private var mMatch: String? = ""
    private var mMatcher: String? = ""

    /**
     * Method to check mobile number
     * @param mTextInputLayout
     * @param mTextInputEditText
     * @param mNumLength
     * @param mMessage
     * @return
     */
    fun isInputEditTextLengthFunc(
        mTextInputLayout: TextInputLayout,
        mTextInputEditText: TextInputEditText,
        mNumLength: Int,
        mMessage: String
    ): Boolean {
        mValue = Objects.requireNonNull(mTextInputEditText.text).toString().trim()
        if (mValue?.length!! < mNumLength) {
            mTextInputLayout.error = mMessage
            hideKeyboardFunc(mTextInputEditText)
            return false
        } else {
            mTextInputLayout.isErrorEnabled = false
        }
        return true
    }

    fun isInputEditTextMobileFunc(
        mTextInputLayout: TextInputLayout,
        mTextInputEditText: TextInputEditText,
        mMessage: String
    ): Boolean {
        mValue = Objects.requireNonNull(mTextInputEditText.text).toString().trim()
        if (!Patterns.PHONE.matcher(mValue!!).matches()) {
            mTextInputLayout.error = mMessage
            hideKeyboardFunc(mTextInputEditText)
            return false
        } else {
            mTextInputLayout.isErrorEnabled = false
        }
        return true
    }

    /**
     * Method to check input fields are empty or not
     * @param mTextInputEditText
     * @param mTextInputLayout
     * @param mMessage
     * @return
     */
    fun isInputEditTextFilledFunc(
        mTextInputEditText: TextInputEditText,
        mTextInputLayout: TextInputLayout,
        mMessage: String
    ): Boolean {
        mValue = Objects.requireNonNull(mTextInputEditText.text).toString().trim()

        if (mValue?.isEmpty()!!) {
            mTextInputLayout.error = mMessage
            hideKeyboardFunc(mTextInputEditText)
            return false
        } else {
            mTextInputLayout.isErrorEnabled = false
        }

        return true
    }

    /**
     * Method to check input fields has valid email address or not
     * @param mTextInputEditText
     * @param mTextInputLayout
     * @param mMessage
     * @return
     */
    fun isInputEditTextEmailFunc(
        mTextInputEditText: TextInputEditText,
        mTextInputLayout: TextInputLayout,
        mMessage: String
    ): Boolean {
        mValue = Objects.requireNonNull(mTextInputEditText.text).toString().trim()

        if (mValue?.isEmpty()!! || !Patterns.EMAIL_ADDRESS.matcher(mValue).matches()) {
            mTextInputLayout.error = mMessage
            hideKeyboardFunc(mTextInputEditText)
            return false
        } else {
            mTextInputLayout.isErrorEnabled = false
        }

        return true
    }

    /**
     * Method to check whether input fields matches or not
     * @param mTextInputEditText1
     * @param mTextInputEditText2
     * @param mTextInputLayout
     * @param mMessage
     * @return
     */
    fun isInputEditTextMatches(
        mTextInputEditText1: TextInputEditText,
        mTextInputEditText2: TextInputEditText,
        mTextInputLayout: TextInputLayout,
        mMessage: String
    ): Boolean {
        mMatch = Objects.requireNonNull(mTextInputEditText1.text).toString().trim()
        mMatcher = Objects.requireNonNull(mTextInputEditText2.text).toString().trim()

        if (!mMatcher?.let { mMatch?.contentEquals(it) }!!) {
            mTextInputLayout.error = mMessage
            hideKeyboardFunc(mTextInputEditText2)
            return false
        } else {
            mTextInputLayout.isErrorEnabled = false
        }

        return true
    }

    /**
     * Method to check input fields are empty or not
     * @param mEditText
     * @param mLength
     * @param mMessage
     * @return
     */
    fun isEditTextFilledFunc(mEditText: EditText, mLength: Int, mMessage: String): Boolean {
        mValue = mEditText.text.toString().trim()

        when {
            mValue?.length!! < mLength -> {
                mEditText.error = mMessage
//                PopUpHelper(mContext).onShortToast(mMessage)
                hideKeyboardFunc(mEditText)
                return false
            }
        }
        return true
    }

    /**
     * Method to hide the keyboard
     * @param mView
     */
    fun hideKeyboardFunc(mView: View) {
        val mInputMethodManager =
            mView.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        mInputMethodManager.hideSoftInputFromWindow(
            mView.windowToken,
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
    }

    /*
    * OTP Field Validation
    * @param mEditText1
    * @param mEditText2
    * */
    fun focusChanger(mEditText1: EditText, mEditText2: EditText?) {
        mEditText1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                if (mEditText1.text.toString().length == 1) {
                    mEditText2?.requestFocus()
                }
            }

            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        mEditText2?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(mEditable: Editable?) {
                if (mEditText2.text.toString().isEmpty()) {
                    mEditText1.requestFocus()
                }
            }

            override fun beforeTextChanged(
                mCharSequence: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(mCharSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    fun isImageValidated(mUrl: String, mView: ViewGroup, mMsg: String): Boolean {
        if (mUrl.isEmpty()) {
            PopUpUtils.getPopUpUtils().onShortSnackbar(mView, mMsg)
            return false
        }
        return true
    }
}