package com.sureshotdiscount.app.ui.verification

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.data.model.LoggedInUser
import com.sureshotdiscount.app.data.model.response.APIActionResponse
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.onDecorateText
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class VerifyOTPFragment : Fragment(R.layout.fragment_verify_o_t_p), View.OnClickListener {

    private lateinit var mTextViewMobile: TextView

    private lateinit var mEditTextOTPOne: EditText
    private lateinit var mEditTextOTPTwo: EditText
    private lateinit var mEditTextOTPThree: EditText
    private lateinit var mEditTextOTPFour: EditText

    private lateinit var mButtonOTPVerifyAndProceed: Button

    private lateinit var mTextViewOTPTime: TextView
    private lateinit var mTextViewResend: TextView

    private lateinit var mContentLoadingProgressBarVerifyOTP: ContentLoadingProgressBar

    private lateinit var mMobileNumber: String

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewMobile = view.findViewById(R.id.textViewMobile)

        mEditTextOTPOne = view.findViewById(R.id.editTextOTPOne)
        mEditTextOTPTwo = view.findViewById(R.id.editTextOTPTwo)
        mEditTextOTPThree = view.findViewById(R.id.editTextOTPThree)
        mEditTextOTPFour = view.findViewById(R.id.editTextOTPFour)

        mButtonOTPVerifyAndProceed = view.findViewById(R.id.buttonOTPVerifyAndProceed)
        mButtonOTPVerifyAndProceed.setOnClickListener(this@VerifyOTPFragment)

        mTextViewOTPTime = view.findViewById(R.id.textViewOTPTime)

        mTextViewResend = view.findViewById(R.id.textViewResend)
        mTextViewResend.setOnClickListener(this@VerifyOTPFragment)

        mContentLoadingProgressBarVerifyOTP =
            view.findViewById(R.id.contentLoadingProgressBarVerifyOTP)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }

        onFocusChanger()
        onDecorateText(
            getString(R.string.text_did_not_receive_the_code),
            26,
            mTextViewResend,
            Color.BLUE
        )

        onValidateArguments()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonOTPVerifyAndProceed -> isVerifyOTPValidated()
            R.id.textViewResend -> onLoadTimer()
        }
    }

    private fun onValidateArguments() {
        val mVerifyOTPFragmentArgs: VerifyOTPFragmentArgs by navArgs()
        mMobileNumber = mVerifyOTPFragmentArgs.verificationMobileNumber
        mTextViewMobile.text = mMobileNumber
    }

    private fun onFocusChanger() {
        context?.let {
            mEditTextOTPOne.hasFocus()
            ValidationUtils.getValidationUtils().focusChanger(
                mEditTextOTPOne,
                mEditTextOTPTwo
            )
            ValidationUtils.getValidationUtils().focusChanger(
                mEditTextOTPTwo,
                mEditTextOTPThree
            )
            ValidationUtils.getValidationUtils().focusChanger(
                mEditTextOTPThree,
                mEditTextOTPFour
            )
        }
    }

    private fun onLoadTimer() {
        val mMilliSeconds = 60000L
        val mCountDownInterval = 1000L

        object : CountDownTimer(mMilliSeconds, mCountDownInterval) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                mTextViewOTPTime.text = (millisUntilFinished / 1000).toString() + " Sec"
                mTextViewResend.isClickable = false
            }

            override fun onFinish() {
                mTextViewOTPTime.text = getString(R.string.hint_time)
                mTextViewResend.isClickable = true
            }
        }.start()
        onClickResendOTP()
    }

    private fun isVerifyOTPValidated() {
        context?.let {
            when {
                !ValidationUtils.getValidationUtils().isEditTextFilledFunc(
                    mEditTextOTPOne,
                    1,
                    getString(R.string.text_error_incorrect_o_t_p)
                ) -> return
                !ValidationUtils.getValidationUtils().isEditTextFilledFunc(
                    mEditTextOTPTwo,
                    1,
                    getString(R.string.text_error_incorrect_o_t_p)
                ) -> return
                !ValidationUtils.getValidationUtils().isEditTextFilledFunc(
                    mEditTextOTPThree,
                    1,
                    getString(R.string.text_error_incorrect_o_t_p)
                ) -> return
                !ValidationUtils.getValidationUtils().isEditTextFilledFunc(
                    mEditTextOTPFour,
                    1,
                    getString(R.string.text_error_incorrect_o_t_p)
                ) -> return
                else -> {
                    mContentLoadingProgressBarVerifyOTP.visibility = View.VISIBLE
                    onClickVerifyOTP()
                }
            }
        }
    }

    private fun onClearVerifyOTP() {
        mEditTextOTPOne.text?.clear()
        mEditTextOTPTwo.text?.clear()
        mEditTextOTPThree.text?.clear()
        mEditTextOTPFour.text?.clear()
    }

    private fun onClickVerifyOTP() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .verifyOTP(
                            mMobileNumber,
                            mEditTextOTPOne.text.toString().trim() +
                                    mEditTextOTPTwo.text.toString().trim() +
                                    mEditTextOTPThree.text.toString().trim() +
                                    mEditTextOTPFour.text.toString().trim()
                        )
                        .enqueue(object : Callback<LoggedInUser> {
                            override fun onResponse(
                                call: Call<LoggedInUser>,
                                response: Response<LoggedInUser>
                            ) {
                                if (response.isSuccessful) {
                                    val mLoggedInUser: LoggedInUser? = response.body()
                                    mContentLoadingProgressBarVerifyOTP.visibility = View.GONE

                                    if (mLoggedInUser != null) {
                                        if (mLoggedInUser.status) {
                                            mSharedPreferenceUtils =
                                                SharedPreferenceUtils(it, mLoggedInUser)
                                            mSharedPreferenceUtils.saveUpdatedLoggedInUser(it)

                                            AlertDialogUtils.getInstance().showAlert(
                                                it,
                                                R.drawable.ic_check_circle_black,
                                                "Registration Successful",
                                                "Your OTP has been verified successfully!",
                                                getString(android.R.string.ok),
                                                null,
                                                DialogInterface.OnDismissListener {
                                                    onClearVerifyOTP()
                                                    view?.let { it1 ->
                                                        ValidationUtils.getValidationUtils()
                                                            .hideKeyboardFunc(it1)
                                                    }
                                                    view?.let { it1 ->
                                                        Navigation.findNavController(it1)
                                                            .navigate(R.id.action_verifyOTP_to_dashboard)
                                                    }
                                                    it.dismiss()
                                                }
                                            )
                                        } else {
                                            AlertDialogUtils.getInstance().showAlert(
                                                it,
                                                R.drawable.ic_warning_black,
                                                "Registration Failed",
                                                "Your OTP is invalid. Please to check and try again!",
                                                getString(android.R.string.ok),
                                                null,
                                                DialogInterface.OnDismissListener {
                                                    onClearVerifyOTP()
                                                    view?.let { it1 ->
                                                        ValidationUtils.getValidationUtils()
                                                            .hideKeyboardFunc(it1)
                                                    }
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

                            override fun onFailure(call: Call<LoggedInUser>, t: Throwable) {
                                ErrorUtils.parseOnFailureException(
                                    it,
                                    call,
                                    t
                                )
                                mContentLoadingProgressBarVerifyOTP.visibility = View.GONE
                            }
                        })
                }
                else -> {
                    mContentLoadingProgressBarVerifyOTP.visibility = View.GONE
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }

    private fun onClickResendOTP() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .resendOTP(mMobileNumber.trim())
                        .enqueue(object : Callback<APIActionResponse> {
                            override fun onResponse(
                                call: Call<APIActionResponse>,
                                response: Response<APIActionResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val mAPIActionResponse: APIActionResponse? = response.body()
                                    mContentLoadingProgressBarVerifyOTP.visibility = View.GONE

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
                                                    view?.let { it1 ->
                                                        ValidationUtils.getValidationUtils()
                                                            .hideKeyboardFunc(it1)
                                                    }
                                                    it.dismiss()
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
                                                    view?.let { it1 ->
                                                        ValidationUtils.getValidationUtils()
                                                            .hideKeyboardFunc(it1)
                                                    }
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
                                mContentLoadingProgressBarVerifyOTP.visibility = View.GONE
                            }
                        })
                }
                else -> {
                    mContentLoadingProgressBarVerifyOTP.visibility = View.GONE
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}