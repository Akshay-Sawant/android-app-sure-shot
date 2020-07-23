package com.sureshotdiscount.app.ui.contactus

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.data.model.response.APIActionResponse
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactUsFragment : Fragment(R.layout.fragment_contact_us), View.OnClickListener {

    private lateinit var mTextInputLayoutContactUsMessage: TextInputLayout
    private lateinit var mTextInputEditTextContactUsMessage: TextInputEditText

    private lateinit var mButtonContactUsSubmit: Button

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextInputLayoutContactUsMessage = view.findViewById(R.id.textInputLayoutContactUsMessage)
        mTextInputEditTextContactUsMessage =
            view.findViewById(R.id.textInputEditTextContactUsMessage)

        mButtonContactUsSubmit = view.findViewById(R.id.buttonContactUsSubmit)
        mButtonContactUsSubmit.setOnClickListener(this@ContactUsFragment)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
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
                onClickContactUs()
            }
        }
    }

    private fun onClickContactUs() {
        context?.let {
            if (APIClient.isNetworkConnected(it)) {
                APIClient.apiInterface
                    .contactUs(
                        mSharedPreferenceUtils.getLoggedInUser().loginToken,
                        mTextInputEditTextContactUsMessage.text.toString().trim()
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
                                                mTextInputEditTextContactUsMessage.text?.clear()
                                                view?.let {
                                                    ValidationUtils.getValidationUtils()
                                                        .hideKeyboardFunc(it)
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