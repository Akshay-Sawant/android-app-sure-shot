package com.sureshotdiscount.app.ui.forgotpassword

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.data.model.response.APIActionResponse
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password), View.OnClickListener {

    private lateinit var mTextInputLayoutForgotPasswordMobileNumber: TextInputLayout
    private lateinit var mTextInputEditTextForgotPasswordMobileNumber: TextInputEditText

    private lateinit var mButtonForgotPasswordGetOTP: Button

    private lateinit var mContentLoadingProgressBarForgotPassword: ContentLoadingProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextInputLayoutForgotPasswordMobileNumber =
            view.findViewById(R.id.textInputLayoutForgotPasswordMobileNumber)
        mTextInputEditTextForgotPasswordMobileNumber =
            view.findViewById(R.id.textInputEditTextForgotPasswordMobileNumer)

        mButtonForgotPasswordGetOTP = view.findViewById(R.id.buttonForgotPasswordGetOTP)
        mButtonForgotPasswordGetOTP.setOnClickListener(this@ForgotPasswordFragment)

        mContentLoadingProgressBarForgotPassword =
            view.findViewById(R.id.contentLoadingProgressBarForgotPassword)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonForgotPasswordGetOTP -> isForgotPasswordValidated()
        }
    }

    private fun isForgotPasswordValidated() {
        when {
            !ValidationUtils.getValidationUtils().isInputEditTextMobileFunc(
                mTextInputLayoutForgotPasswordMobileNumber,
                mTextInputEditTextForgotPasswordMobileNumber,
                getString(R.string.text_error_mobile)
            ) -> return
            else -> {
                onClickGetOTP()
            }
        }
    }

    private fun onClickGetOTP() {
        context?.let {
            if (APIClient.isNetworkConnected(it)) {
                APIClient.apiInterface
                    .forgotPassword(
                        mTextInputEditTextForgotPasswordMobileNumber.text.toString().trim()
                    )
                    .enqueue(object : Callback<APIActionResponse> {
                        override fun onResponse(
                            call: Call<APIActionResponse>,
                            response: Response<APIActionResponse>
                        ) {
                            if (response.isSuccessful) {
                                val mApiActionResponse: APIActionResponse? = response.body()
                                mContentLoadingProgressBarForgotPassword.visibility = View.GONE

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
                                                it.dismiss()
                                                view?.let { it1 ->
                                                    Navigation.findNavController(it1)
                                                        .navigate(
                                                            ForgotPasswordFragmentDirections.actionForgotPasswordToCreateNewPassword(
                                                                mTextInputEditTextForgotPasswordMobileNumber.text.toString()
                                                                    .trim()
                                                            )
                                                        )
                                                }
                                                view?.let { it1 ->
                                                    ValidationUtils.getValidationUtils()
                                                        .hideKeyboardFunc(
                                                            it1
                                                        )
                                                }
                                                mTextInputEditTextForgotPasswordMobileNumber.text?.clear()
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
                                                it.dismiss()
                                                view?.let { it1 ->
                                                    ValidationUtils.getValidationUtils()
                                                        .hideKeyboardFunc(
                                                            it1
                                                        )
                                                }
                                            }
                                        )
                                    }
                                } else {
                                    ErrorUtils.logNetworkError(
                                        ServerInvalidResponseException.ERROR_200_BLANK_RESPONSE +
                                                "\nResponse: " + response.toString(),
                                        null
                                    )
                                    AlertDialogUtils.getInstance().displayInvalidResponseAlert(it)
                                }
                            }
                        }

                        override fun onFailure(call: Call<APIActionResponse>, t: Throwable) {
                            ErrorUtils.parseOnFailureException(
                                it,
                                call,
                                t
                            )
                            mContentLoadingProgressBarForgotPassword.visibility = View.GONE
                        }
                    })
            } else {
                mContentLoadingProgressBarForgotPassword.visibility = View.GONE
                AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
            }
        }
    }
}