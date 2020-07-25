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
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.data.model.LoggedInUser
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.onDecorateText
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
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

    lateinit var mMobileNumber: String

    var mIsRegister: Boolean = false

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
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonOTPVerifyAndProceed -> view?.let {
//                onClickSignInOtp()
                Navigation.findNavController(it)
                    .navigate(R.id.action_verifyOTP_to_dashboard)
            }
            R.id.textViewResend -> onLoadTimer()
        }
    }

    private fun onValidateArguments() {
        val mVerifyOTPFragmentArgs: VerifyOTPFragmentArgs by navArgs()
        mIsRegister = mVerifyOTPFragmentArgs.isRegister
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
    }

    private fun isVerifyOTPValidated() {
        context?.let {
            when {
                ValidationUtils.getValidationUtils().isEditTextFilledFunc(
                    mEditTextOTPOne,
                    1,
                    getString(R.string.text_error_incorrect_o_t_p)
                ) -> return
                ValidationUtils.getValidationUtils().isEditTextFilledFunc(
                    mEditTextOTPTwo,
                    1,
                    getString(R.string.text_error_incorrect_o_t_p)
                ) -> return
                ValidationUtils.getValidationUtils().isEditTextFilledFunc(
                    mEditTextOTPThree,
                    1,
                    getString(R.string.text_error_incorrect_o_t_p)
                ) -> return
                ValidationUtils.getValidationUtils().isEditTextFilledFunc(
                    mEditTextOTPFour,
                    1,
                    getString(R.string.text_error_incorrect_o_t_p)
                ) -> return
                else -> {
                    if (mIsRegister) {
                        onClickSignUpOTP()
                    } else {
//                        onClickSignInOtp()
                    }
                }
            }
        }
    }

    private fun onClickSignInOtp() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .verifySignInOTP(
                            "",
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

                                    if (mLoggedInUser != null) {
                                        mSharedPreferenceUtils =
                                            SharedPreferenceUtils(it, mLoggedInUser)
                                        mSharedPreferenceUtils.saveUpdatedLoggedInUser(it)

                                        AlertDialogUtils.getInstance().showAlert(
                                            it,
                                            R.drawable.ic_check_circle_black,
                                            "Login Successful",
                                            "Your OTP has been verified successfully!",
                                            getString(android.R.string.ok),
                                            null,
                                            DialogInterface.OnDismissListener {
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
                                            "Login Failed",
                                            "Your OTP is invalid. Please to check and try again!",
                                            getString(android.R.string.ok),
                                            null,
                                            DialogInterface.OnDismissListener {
                                                it.dismiss()
                                            }
                                        )
                                    }
                                }
                            }

                            override fun onFailure(call: Call<LoggedInUser>, t: Throwable) {
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

    private fun onClickSignUpOTP() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .verifySignUpOTP(
                            "",
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

                                    if (mLoggedInUser != null) {
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
                                                it.dismiss()
                                            }
                                        )
                                    }
                                }
                            }

                            override fun onFailure(call: Call<LoggedInUser>, t: Throwable) {
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