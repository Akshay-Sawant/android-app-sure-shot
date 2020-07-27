package com.sureshotdiscount.app.ui.signup

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.data.model.response.APIActionResponse
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.onDecorateText
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment(R.layout.fragment_sign_up), View.OnClickListener {

    private lateinit var mTextInputLayoutSignUpName: TextInputLayout
    private lateinit var mTextInputEditTextSignUpName: TextInputEditText

    private lateinit var mTextInputLayoutSignUpEmailId: TextInputLayout
    private lateinit var mTextInputEditTextSignUpEmailId: TextInputEditText

    private lateinit var mTextInputLayoutSignUpMobileNumber: TextInputLayout
    private lateinit var mTextInputEditTextSignUpMobileNumber: TextInputEditText

    private lateinit var mTextInputLayoutSignUpPassword: TextInputLayout
    private lateinit var mTextInputEditTextSignUpPassword: TextInputEditText

    private lateinit var mTextInputLayoutSignUpConfirmPassword: TextInputLayout
    private lateinit var mTextInputEditTextSignUpConfirmPassword: TextInputEditText

    private lateinit var mTextInputLayoutSignUpReferralId: TextInputLayout
    private lateinit var mTextInputEditTextSignUpReferralId: TextInputEditText

    private lateinit var mButtonSignUpContinue: Button

    private lateinit var mTextViewSignUpHaveAccount: TextView

    private lateinit var mContentLoadingProgressBar: ContentLoadingProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextInputLayoutSignUpName = view.findViewById(R.id.textInputLayoutSignUpName)
        mTextInputEditTextSignUpName = view.findViewById(R.id.textInputEditTextSignUpName)

        mTextInputLayoutSignUpEmailId = view.findViewById(R.id.textInputLayoutSignUpEmailId)
        mTextInputEditTextSignUpEmailId = view.findViewById(R.id.textInputEditTextSignUpEmailId)

        mTextInputLayoutSignUpMobileNumber =
            view.findViewById(R.id.textInputLayoutSignUpMobileNumber)
        mTextInputEditTextSignUpMobileNumber =
            view.findViewById(R.id.textInputEditTextSignUpMobileNumber)

        mTextInputLayoutSignUpPassword = view.findViewById(R.id.textInputLayoutSignUpPassword)
        mTextInputEditTextSignUpPassword = view.findViewById(R.id.textInputEditTextSignUpPassword)

        mTextInputLayoutSignUpConfirmPassword =
            view.findViewById(R.id.textInputLayoutSignUpConfirmPassword)
        mTextInputEditTextSignUpConfirmPassword =
            view.findViewById(R.id.textInputEditTextSignUpConfirmPassword)

        mTextInputLayoutSignUpReferralId = view.findViewById(R.id.textInputLayoutSignUpReferralId)
        mTextInputEditTextSignUpReferralId =
            view.findViewById(R.id.textInputEditTextSignUpReferralId)

        mButtonSignUpContinue = view.findViewById(R.id.buttonSignUpContinue)
        mButtonSignUpContinue.setOnClickListener(this)

        mTextViewSignUpHaveAccount = view.findViewById(R.id.textViewSignUpHaveAccount)
        mTextViewSignUpHaveAccount.setOnClickListener(this@SignUpFragment)

        mContentLoadingProgressBar = view.findViewById(R.id.contentLoadingProgressBarSignUp)

        onDecorateText(
            getString(R.string.text_label_have_account),
            25,
            mTextViewSignUpHaveAccount,
            Color.BLUE
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonSignUpContinue -> isSignUpValidated()
            R.id.textViewSignUpHaveAccount -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_signUp_to_signIn)
            }
        }
    }

    private fun isSignUpValidated() {
        context?.let {
            when {
                !ValidationUtils.getValidationUtils().isInputEditTextFilledFunc(
                    mTextInputEditTextSignUpName,
                    mTextInputLayoutSignUpName,
                    getString(R.string.text_error_name)
                ) -> return
                !ValidationUtils.getValidationUtils().isInputEditTextEmailFunc(
                    mTextInputEditTextSignUpEmailId,
                    mTextInputLayoutSignUpEmailId,
                    getString(R.string.text_error_email_id)
                ) -> return
                !ValidationUtils.getValidationUtils().isInputEditTextMobileFunc(
                    mTextInputLayoutSignUpMobileNumber,
                    mTextInputEditTextSignUpMobileNumber,
                    getString(R.string.text_error_mobile)
                ) -> return
                !ValidationUtils.getValidationUtils().isInputEditTextLengthFunc(
                    mTextInputLayoutSignUpPassword,
                    mTextInputEditTextSignUpPassword,
                    6,
                    getString(R.string.text_error_password)
                ) -> return
                !ValidationUtils.getValidationUtils().isInputEditTextMatches(
                    mTextInputEditTextSignUpPassword,
                    mTextInputEditTextSignUpConfirmPassword,
                    mTextInputLayoutSignUpConfirmPassword,
                    getString(R.string.text_error_password_match)
                ) -> return
                else -> {
                    mContentLoadingProgressBar.visibility = View.VISIBLE
                    onClickSignUp()
                }
            }
        }
    }

    private fun onClearData() {
        mTextInputEditTextSignUpName.text?.clear()
        mTextInputEditTextSignUpEmailId.text?.clear()
        mTextInputEditTextSignUpMobileNumber.text?.clear()
        mTextInputEditTextSignUpPassword.text?.clear()
        mTextInputEditTextSignUpConfirmPassword.text?.clear()
        mTextInputEditTextSignUpReferralId.text?.clear()
    }

    private fun onClickSignUp() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .requestSignUpOTP(
                            mTextInputEditTextSignUpName.text.toString().trim(),
                            mTextInputEditTextSignUpEmailId.text.toString().trim(),
                            mTextInputEditTextSignUpMobileNumber.text.toString().trim(),
                            mTextInputEditTextSignUpPassword.text.toString().trim(),
                            mTextInputEditTextSignUpReferralId.text.toString().trim()
                        )
                        .enqueue(object : Callback<APIActionResponse> {
                            override fun onResponse(
                                call: Call<APIActionResponse>,
                                response: Response<APIActionResponse>
                            ) {
                                if (response.isSuccessful) {
                                    mContentLoadingProgressBar.visibility = View.GONE
                                    val mApiActionResponse: APIActionResponse? = response.body()

                                    if (mApiActionResponse != null) {
                                        if (mApiActionResponse.isActionSuccess) {
                                            AlertDialogUtils.getInstance().showAlert(
                                                it,
                                                R.drawable.ic_check_circle_black,
                                                mApiActionResponse.title,
                                                mApiActionResponse.message,
                                                getString(android.R.string.ok),
                                                null,
                                                DialogInterface.OnDismissListener {
                                                    val mSignUpAction =
                                                        SignUpFragmentDirections.actionSignUpToVerifyOTP(
                                                            mTextInputEditTextSignUpMobileNumber.text.toString()
                                                                .trim()
                                                        )
                                                    view?.let { it1 ->
                                                        Navigation.findNavController(it1)
                                                            .navigate(mSignUpAction)
                                                    }
                                                    it.dismiss()
                                                    onClearData()
                                                }
                                            )
                                        } else {
                                            AlertDialogUtils.getInstance().showAlert(
                                                it,
                                                R.drawable.ic_warning_black,
                                                mApiActionResponse.title,
                                                mApiActionResponse.message,
                                                getString(android.R.string.ok),
                                                null,
                                                DialogInterface.OnDismissListener {
                                                    onClearData()
                                                    it.dismiss()
                                                }
                                            )
                                        }
                                    } else {
                                        ErrorUtils.logNetworkError(
                                            ServerInvalidResponseException.ERROR_200_BLANK_RESPONSE +
                                                    "\nResponse: " + response.toString(),
                                            null
                                        )
                                        AlertDialogUtils.getInstance()
                                            .displayInvalidResponseAlert(it)
                                    }
                                }
                            }

                            override fun onFailure(call: Call<APIActionResponse>, t: Throwable) {
                                ErrorUtils.parseOnFailureException(
                                    it,
                                    call,
                                    t
                                )
                                mContentLoadingProgressBar.visibility = View.GONE
                            }
                        })
                }
                else -> {
                    mContentLoadingProgressBar.visibility = View.GONE
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}