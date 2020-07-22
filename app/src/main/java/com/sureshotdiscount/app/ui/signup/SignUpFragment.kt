package com.sureshotdiscount.app.ui.signup

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
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

        mTextInputLayoutSignUpReferralId = view.findViewById(R.id.textInputLayoutSignUpReferralId)
        mTextInputEditTextSignUpReferralId =
            view.findViewById(R.id.textInputEditTextSignUpReferralId)

        mButtonContinue = view.findViewById(R.id.buttonContinue)
        mButtonContinue.setOnClickListener(this)

        mTextViewSignUpHaveAccount = view.findViewById(R.id.textViewSignUpHaveAccount)
        mTextViewSignUpHaveAccount.setOnClickListener(this@SignUpFragment)

        onDecorateText(
            getString(R.string.text_label_have_account),
            25,
            mTextViewSignUpHaveAccount,
            Color.BLUE
        )
    }

    override fun onClick(v: View?) {
        view?.let {
            when (v?.id) {
                R.id.buttonContinue -> isSignUpValidated()
                R.id.textViewSignUpHaveAccount -> Navigation.findNavController(it)
                    .navigate(R.id.action_signUp_to_signIn)
            }
        }
    }

    private fun isSignUpValidated() {
        context?.let {
            when {
                !ValidationUtils.getValidationUtils().isInputEditTextLengthFunc(
                    mTextInputLayoutMobileNumber,
                    mTextInputEditTextMobileNumber,
                    10,
                    getString(R.string.text_error_mobile)
                ) -> return
                else -> {
//                    onClickSignUp()
                    view?.let { it1 ->
                        Navigation.findNavController(it1)
                            .navigate(R.id.action_signUp_to_verifyOTP)
                    }
                }
            }
        }
    }

    private fun onClickSignUp() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .requestSignUpOTP(
                            mTextInputEditTextMobileNumber.text.toString().trim(),
                            mTextInputEditTextSignUpReferralId.text.toString().trim()
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
                            }
                        })
                }
                else -> {
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}