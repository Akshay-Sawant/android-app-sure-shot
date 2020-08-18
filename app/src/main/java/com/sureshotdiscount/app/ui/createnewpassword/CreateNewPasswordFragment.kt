package com.sureshotdiscount.app.ui.createnewpassword

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
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

class CreateNewPasswordFragment : Fragment(R.layout.fragment_create_new_password),
    View.OnClickListener {

    private lateinit var mTextInputLayoutCreateNewPasswordEnterOTP: TextInputLayout
    private lateinit var mTextInputEditTextCreateNewPasswordEnterOTP: TextInputEditText

    private lateinit var mTextInputLayoutCreateNewPasswordEnterNewPassword: TextInputLayout
    private lateinit var mTextInputEditTextCreateNewPasswordEnterNewPassword: TextInputEditText

    private lateinit var mTextInputLayoutCreateNewPasswordConfirmNewPassword: TextInputLayout
    private lateinit var mTextInputEditTextCreateNewPasswordConfirmNewPassword: TextInputEditText

    private lateinit var mButtonCreateNewPassword: Button

    private lateinit var mContentLoadingProgressBarCreateNewPasssword: ContentLoadingProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextInputLayoutCreateNewPasswordEnterOTP =
            view.findViewById(R.id.textInputLayoutCreateNewPasswordEnterOTP)
        mTextInputEditTextCreateNewPasswordEnterOTP =
            view.findViewById(R.id.textInputEditTextCreateNewPasswordEnterOTP)

        mTextInputLayoutCreateNewPasswordEnterNewPassword =
            view.findViewById(R.id.textInputLayoutCreateNewPasswordEnterNewPassword)
        mTextInputEditTextCreateNewPasswordEnterNewPassword =
            view.findViewById(R.id.textInputEditTextCreateNewPasswordEnterNewPassword)

        mTextInputLayoutCreateNewPasswordConfirmNewPassword =
            view.findViewById(R.id.textInputLayoutCreateNewPasswordConfirmNewPassword)
        mTextInputEditTextCreateNewPasswordConfirmNewPassword =
            view.findViewById(R.id.textInputEditTextCreateNewPasswordConfirmNewPassword)

        mButtonCreateNewPassword = view.findViewById(R.id.buttonCreateNewPassword)
        mButtonCreateNewPassword.setOnClickListener(this@CreateNewPasswordFragment)

        mContentLoadingProgressBarCreateNewPasssword =
            view.findViewById(R.id.contentLoadingProgressBarCreateNewPassword)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonCreateNewPassword -> {

            }
        }
    }

    private fun isCreateNewPasswordValidated() {
        when {
            !ValidationUtils.getValidationUtils().isInputEditTextLengthFunc(
                mTextInputLayoutCreateNewPasswordEnterOTP,
                mTextInputEditTextCreateNewPasswordEnterOTP,
                4,
                getString(R.string.text_error_otp)
            ) -> return
            !ValidationUtils.getValidationUtils().isInputEditTextLengthFunc(
                mTextInputLayoutCreateNewPasswordEnterNewPassword,
                mTextInputEditTextCreateNewPasswordEnterNewPassword,
                6,
                getString(R.string.text_error_password)
            ) -> return
            !ValidationUtils.getValidationUtils().isInputEditTextMatches(
                mTextInputEditTextCreateNewPasswordEnterNewPassword,
                mTextInputEditTextCreateNewPasswordConfirmNewPassword,
                mTextInputLayoutCreateNewPasswordConfirmNewPassword,
                getString(R.string.text_error_password_match)
            ) -> return
            else -> {
//                onClickCreateNewPassword()
            }
        }
    }

    private fun onClearCreateNewPassword() {
        mTextInputEditTextCreateNewPasswordEnterOTP.text?.clear()
        mTextInputEditTextCreateNewPasswordEnterNewPassword.text?.clear()
        mTextInputEditTextCreateNewPasswordConfirmNewPassword.text?.clear()
    }

    private fun onClickCreateNewPassword() {
        context?.let {
            if (APIClient.isNetworkConnected(it)) {
                APIClient.apiInterface
                    .createNewPassword(
                        "",
                        mTextInputEditTextCreateNewPasswordEnterOTP.text.toString().trim(),
                        mTextInputEditTextCreateNewPasswordConfirmNewPassword.text.toString().trim()
                    )
                    .enqueue(object : Callback<APIActionResponse> {
                        override fun onResponse(
                            call: Call<APIActionResponse>,
                            response: Response<APIActionResponse>
                        ) {
                            if (response.isSuccessful) {
                                val mApiActionResponse: APIActionResponse? = response.body()
                                mContentLoadingProgressBarCreateNewPasssword.visibility = View.GONE

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
                                                onClearCreateNewPassword()
                                                view?.let { it1 ->
                                                    ValidationUtils.getValidationUtils()
                                                        .hideKeyboardFunc(
                                                            it1
                                                        )
                                                }
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
                                                onClearCreateNewPassword()
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
                            mContentLoadingProgressBarCreateNewPasssword.visibility = View.GONE
                        }
                    })
            } else {
                mContentLoadingProgressBarCreateNewPasssword.visibility = View.GONE
                AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
            }
        }
    }
}