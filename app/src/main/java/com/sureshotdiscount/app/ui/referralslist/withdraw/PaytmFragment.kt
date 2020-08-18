package com.sureshotdiscount.app.ui.referralslist.withdraw

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
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

class PaytmFragment : Fragment(R.layout.fragment_paytm), View.OnClickListener {

    private lateinit var mTextViewPaytmBalanceEarnings: TextView

    private lateinit var mTextInputLayoutPaytmEnterWithdrawalAmount: TextInputLayout
    private lateinit var mTextInputEditTextPaytmEnterWithdrawalAmount: TextInputEditText

    private lateinit var mButtonPaytmWithdraw: Button
    private lateinit var mContentLoadingProgressBarPaytm: ContentLoadingProgressBar

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewPaytmBalanceEarnings = view.findViewById(R.id.textViewPaytmBalanceEarnings)

        mTextInputLayoutPaytmEnterWithdrawalAmount =
            view.findViewById(R.id.textInputLayoutPaytmEnterWithdrawalAmount)
        mTextInputEditTextPaytmEnterWithdrawalAmount =
            view.findViewById(R.id.textInputEditTextPaytmEnterWithdrawalAmount)

        mButtonPaytmWithdraw = view.findViewById(R.id.buttonPaytmWithdraw)
        mButtonPaytmWithdraw.setOnClickListener(this@PaytmFragment)

        mContentLoadingProgressBarPaytm = view.findViewById(R.id.contentLoadingProgressBarPaytm)
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
            mTextViewPaytmBalanceEarnings.text = getString(
                R.string.text_label_rupees,
                mSharedPreferenceUtils.getBalanceEarnings(it).toString()
            )
        }
        mContentLoadingProgressBarPaytm.hide()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonPaytmWithdraw -> isPaytmValidated()
        }
    }

    private fun isPaytmValidated() {
        when {
            !ValidationUtils.getValidationUtils().isInputEditTextFilledFunc(
                mTextInputEditTextPaytmEnterWithdrawalAmount,
                mTextInputLayoutPaytmEnterWithdrawalAmount,
                getString(R.string.text_error_empty_field)
            ) -> return
            else -> {
//                onClickPaytmWithdraw()
            }
        }
    }

    private fun onClickPaytmWithdraw() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .paytmWithdraw(
                            mSharedPreferenceUtils.getLoggedInUser().loginToken,
                            mSharedPreferenceUtils.getLoggedInUser().mobileNumber,
                            mTextInputEditTextPaytmEnterWithdrawalAmount.text.toString().trim()
                        ).enqueue(object : Callback<APIActionResponse> {
                            override fun onResponse(
                                call: Call<APIActionResponse>,
                                response: Response<APIActionResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val mAPIActionResponse: APIActionResponse? =
                                        response.body()
                                    mContentLoadingProgressBarPaytm.hide()

                                    if (mAPIActionResponse != null) {
                                        if (mAPIActionResponse.isActionSuccess) {
                                            AlertDialogUtils.getInstance().showAlert(
                                                it,
                                                R.drawable.ic_check_circle_black,
                                                mAPIActionResponse.title,
                                                mAPIActionResponse.message,
                                                getString(android.R.string.ok),
                                                null,
                                                DialogInterface.OnDismissListener {
                                                    it.dismiss()
                                                    mTextInputEditTextPaytmEnterWithdrawalAmount.text?.clear()
                                                }
                                            )
                                        } else {
                                            AlertDialogUtils.getInstance().showAlert(
                                                it,
                                                R.drawable.ic_warning_black,
                                                mAPIActionResponse.title,
                                                mAPIActionResponse.message,
                                                getString(android.R.string.ok),
                                                null,
                                                DialogInterface.OnDismissListener {
                                                    mTextInputEditTextPaytmEnterWithdrawalAmount.text?.clear()
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

                            override fun onFailure(
                                call: Call<APIActionResponse>,
                                t: Throwable
                            ) {
                                ErrorUtils.parseOnFailureException(
                                    it,
                                    call,
                                    t
                                )
                                mContentLoadingProgressBarPaytm.hide()
                            }
                        })
                }
                else -> {
                    mContentLoadingProgressBarPaytm.hide()
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}