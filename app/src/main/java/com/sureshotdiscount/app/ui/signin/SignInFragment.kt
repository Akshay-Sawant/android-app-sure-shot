package com.sureshotdiscount.app.ui.signin

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
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

class SignInFragment : Fragment(R.layout.fragment_sign_in), View.OnClickListener {

    private lateinit var mTextInputLayoutSignInMobileNumber: TextInputLayout
    private lateinit var mTextInputEditTextSignInMobileNumber: TextInputEditText

    private lateinit var mTextInputLayoutSignInPassword: TextInputLayout
    private lateinit var mTextInputEditTextSignInPassword: TextInputEditText

    private lateinit var mButtonSignInContinue: Button

    private lateinit var mTextViewSignInNoAccount: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextInputLayoutSignInMobileNumber = view
            .findViewById(R.id.textInputLayoutSignInMobileNumber)
        mTextInputEditTextSignInMobileNumber = view
            .findViewById(R.id.textInputEditTextSignInMobileNumber)

        mTextInputLayoutSignInPassword = view.findViewById(R.id.textInputLayoutSignInPassword)
        mTextInputEditTextSignInPassword = view.findViewById(R.id.textInputEditTextSignInPassword)

        mButtonSignInContinue = view.findViewById(R.id.buttonSignInContinue)
        mButtonSignInContinue.setOnClickListener(this@SignInFragment)

        mTextViewSignInNoAccount = view.findViewById(R.id.textViewSignInNoAccount)
        mTextViewSignInNoAccount.setOnClickListener(this@SignInFragment)

        onDecorateText(
            getString(R.string.text_label_no_account),
            20,
            mTextViewSignInNoAccount,
            Color.BLUE
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonSignInContinue -> view?.let {
//                isSignInValidated()
                Navigation.findNavController(it)
                    .navigate(R.id.action_signIn_to_verifyOTP)
            }
            R.id.textViewSignInNoAccount -> {
                view?.let {
                    Navigation.findNavController(it)
                        .popBackStack()
                }
            }
        }
    }

    private fun isSignInValidated() {
        when {
            !ValidationUtils.getValidationUtils()
                .isInputEditTextMobileFunc(
                    mTextInputLayoutSignInMobileNumber,
                    mTextInputEditTextSignInMobileNumber,
                    getString(R.string.text_error_mobile)
                ) -> return
            !ValidationUtils.getValidationUtils()
                .isInputEditTextLengthFunc(
                    mTextInputLayoutSignInPassword,
                    mTextInputEditTextSignInPassword,
                    6,
                    getString(R.string.text_error_otp)
                ) -> return
            else -> {
                onClickSignIn()
            }
        }
    }

    private fun onClickSignIn() {
        context?.let {
            if (APIClient.isNetworkConnected(it)) {
                APIClient.apiInterface
                    .requestSignInOTP(
                        mTextInputEditTextSignInMobileNumber.text.toString().trim(),
                        mTextInputEditTextSignInPassword.text.toString().trim()
                    )
                    .enqueue(object : Callback<APIActionResponse> {
                        override fun onResponse(
                            call: Call<APIActionResponse>,
                            response: Response<APIActionResponse>
                        ) {
                            if (response.isSuccessful) {
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
                                                view?.let { it1 ->
                                                    Navigation.findNavController(it1)
                                                        .navigate(R.id.action_signIn_to_verifyOTP)
                                                }
                                                it.dismiss()
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
                        }
                    })
            } else {
                AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
            }
        }
    }
}