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

class BankFragment : Fragment(R.layout.fragment_bank), View.OnClickListener {

    private lateinit var mTextViewBankBalanceEarnings: TextView

    private lateinit var mTextInputLayoutBankEnterWithdrawalAmount: TextInputLayout
    private lateinit var mTextInputEditTextBankEnterWithdrawalAmount: TextInputEditText

    private lateinit var mTextInputLayoutBankNamOnAccount: TextInputLayout
    private lateinit var mTextInputEditTextBankNameOnAccount: TextInputEditText

    private lateinit var mTextInputLayoutBankAccountNumber: TextInputLayout
    private lateinit var mTextInputEditTextBankAccountNumber: TextInputEditText

    private lateinit var mTextInputLayoutBankIFSCCode: TextInputLayout
    private lateinit var mTextInputEditTextBankIFSCCode: TextInputEditText

    private lateinit var mTextInputLayoutBankName: TextInputLayout
    private lateinit var mTextInputEditTextBankName: TextInputEditText

    private lateinit var mTextInputLayoutBankBranchAddress: TextInputLayout
    private lateinit var mTextInputEditTextBankBranchAddress: TextInputEditText

    private lateinit var mButtonBankSaveBankDetailsAndWithdraw: Button

    private lateinit var mContextLoadingProgressBarBank: ContentLoadingProgressBar

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils
    private var mBalanceEarnings: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewBankBalanceEarnings = view.findViewById(R.id.textViewBankBalanceEarnings)

        mTextInputLayoutBankEnterWithdrawalAmount =
            view.findViewById(R.id.textInputLayoutBankEnterWithdrawalAmount)
        mTextInputEditTextBankEnterWithdrawalAmount =
            view.findViewById(R.id.textInputEditTextBankEnterWithdrawalAmount)

        mTextInputLayoutBankNamOnAccount = view.findViewById(R.id.textInputLayoutBankNameOnAccount)
        mTextInputEditTextBankNameOnAccount =
            view.findViewById(R.id.textInputEditTextBankNameOnAccount)

        mTextInputLayoutBankAccountNumber = view.findViewById(R.id.textInputLayoutBankAccountNumber)
        mTextInputEditTextBankAccountNumber =
            view.findViewById(R.id.textInputEditTextBankAccountNumber)

        mTextInputLayoutBankIFSCCode = view.findViewById(R.id.textInputLayoutBankIFSCCode)
        mTextInputEditTextBankIFSCCode = view.findViewById(R.id.textInputEditTextBankIFSCCode)

        mTextInputLayoutBankName = view.findViewById(R.id.textInputLayoutBankName)
        mTextInputEditTextBankName = view.findViewById(R.id.textInputEditTextBankName)

        mTextInputLayoutBankBranchAddress = view.findViewById(R.id.textInputLayoutBankBranchAddress)
        mTextInputEditTextBankBranchAddress =
            view.findViewById(R.id.textInputEditTextBankBranchAddress)

        mButtonBankSaveBankDetailsAndWithdraw =
            view.findViewById(R.id.buttonBankSaveBankDetailsAndWithdraw)
        mButtonBankSaveBankDetailsAndWithdraw.setOnClickListener(this@BankFragment)

        mContextLoadingProgressBarBank = view.findViewById(R.id.contentLoadingProgressBarBank)
    }

    override fun onResume() {
        super.onResume()
        context?.let { mSharedPreferenceUtils = SharedPreferenceUtils(it) }
        onLoadBankDetails()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonBankSaveBankDetailsAndWithdraw -> isBankValidated()
        }
    }

    private fun onLoadBankDetails() {
        context?.let {
            mBalanceEarnings = mSharedPreferenceUtils.getBalanceEarnings(it)
            mTextViewBankBalanceEarnings.text =
                getString(
                    R.string.text_label_rupees,
                    mSharedPreferenceUtils.getBalanceEarnings(it).toString()
                )
            mTextInputEditTextBankNameOnAccount.setText(
                mSharedPreferenceUtils.getNameOnAccount(it),
                TextView.BufferType.EDITABLE
            )
            mTextInputEditTextBankAccountNumber.setText(
                mSharedPreferenceUtils.getAccountNumber(it),
                TextView.BufferType.EDITABLE
            )
            mTextInputEditTextBankIFSCCode.setText(
                mSharedPreferenceUtils.getIFSCCode(it),
                TextView.BufferType.EDITABLE
            )
            mTextInputEditTextBankName.setText(
                mSharedPreferenceUtils.getBankName(it),
                TextView.BufferType.EDITABLE
            )
            mTextInputEditTextBankBranchAddress.setText(
                mSharedPreferenceUtils.getBranchName(it),
                TextView.BufferType.EDITABLE
            )
        }
    }

    private fun isBankValidated() {
        when {
            !ValidationUtils.getValidationUtils().isInputEditTextFilledFunc(
                mTextInputEditTextBankEnterWithdrawalAmount,
                mTextInputLayoutBankEnterWithdrawalAmount,
                getString(R.string.text_error_empty_field)
            ) -> return
            !ValidationUtils.getValidationUtils().isInputEditTextFilledFunc(
                mTextInputEditTextBankNameOnAccount,
                mTextInputLayoutBankNamOnAccount,
                getString(R.string.text_error_empty_field)
            ) -> return
            !ValidationUtils.getValidationUtils().isInputEditTextFilledFunc(
                mTextInputEditTextBankAccountNumber,
                mTextInputLayoutBankAccountNumber,
                getString(R.string.text_error_empty_field)
            ) -> return
            !ValidationUtils.getValidationUtils().isInputEditTextFilledFunc(
                mTextInputEditTextBankIFSCCode,
                mTextInputLayoutBankIFSCCode,
                getString(R.string.text_error_empty_field)
            ) -> return
            !ValidationUtils.getValidationUtils().isInputEditTextFilledFunc(
                mTextInputEditTextBankName,
                mTextInputLayoutBankName,
                getString(R.string.text_error_empty_field)
            ) -> return
            !ValidationUtils.getValidationUtils().isInputEditTextFilledFunc(
                mTextInputEditTextBankBranchAddress,
                mTextInputLayoutBankBranchAddress,
                getString(R.string.text_error_empty_field)
            ) -> return
            else -> {
                isWithdrawalAmountVerified()
            }
        }
    }

    private fun isWithdrawalAmountVerified() {
        mBalanceEarnings?.let {
            if (it < 10) {
                context?.let {
                    AlertDialogUtils.getInstance().showAlert(
                        it,
                        R.drawable.ic_warning_black,
                        "Alert",
                        "Your balance amount is less than 10. You cannot withdraw.",
                        getString(android.R.string.ok),
                        null,
                        DialogInterface.OnDismissListener {
                            mTextInputEditTextBankEnterWithdrawalAmount.text?.clear()
                            it.dismiss()
                        }
                    )
                }
            } else {
//                onClickSaveBankDetailsAndWithdraw()
            }
        }
    }

    private fun onClickSaveBankDetailsAndWithdraw() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .bankWithdraw(
                            mSharedPreferenceUtils.getLoggedInUser().loginToken,
                            mTextInputEditTextBankEnterWithdrawalAmount.text.toString().trim(),
                            mTextInputEditTextBankNameOnAccount.text.toString().trim(),
                            mTextInputEditTextBankAccountNumber.text.toString().trim(),
                            mTextInputEditTextBankIFSCCode.text.toString().trim(),
                            mTextInputEditTextBankName.text.toString().trim(),
                            mTextInputEditTextBankBranchAddress.text.toString().trim()
                        ).enqueue(object : Callback<APIActionResponse> {
                            override fun onResponse(
                                call: Call<APIActionResponse>,
                                response: Response<APIActionResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val mAPIActionResponse: APIActionResponse? =
                                        response.body()
                                    mContextLoadingProgressBarBank.hide()

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
                                                    mTextInputEditTextBankEnterWithdrawalAmount.text?.clear()
                                                    mSharedPreferenceUtils.saveBankDetails(
                                                        mTextInputEditTextBankNameOnAccount.text.toString()
                                                            .trim(),
                                                        mTextInputEditTextBankAccountNumber.text.toString(),
                                                        mTextInputEditTextBankIFSCCode.text.toString()
                                                            .trim(),
                                                        mTextInputEditTextBankName.text.toString()
                                                            .trim(),
                                                        mTextInputEditTextBankBranchAddress.text.toString()
                                                            .trim()
                                                    )
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
                                                    mTextInputEditTextBankEnterWithdrawalAmount.text?.clear()
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
                                mContextLoadingProgressBarBank.hide()
                            }
                        })
                }
                else -> {
                    mContextLoadingProgressBarBank.hide()
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}