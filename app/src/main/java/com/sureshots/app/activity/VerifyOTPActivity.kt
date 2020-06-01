package com.sureshots.app.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.innovins.helperlibrary.helper.AlertDialogManager
import com.sureshots.app.R
import com.sureshots.app.login.LoggedInUser
import com.sureshots.app.login.LoginHelper
import com.sureshots.app.model.response.APIActionResponse
import com.sureshots.app.network.APIClient
import com.sureshots.app.network.ErrorUtils
import com.sureshots.app.network.ServerInvalidResponseException
import kotlinx.android.synthetic.main.activity_verify_o_t_p.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyOTPActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var textViewResend : TextView
    private lateinit var Number: String
    private lateinit var mLoggedInUser: LoggedInUser


    companion object {
        private lateinit var mIntent: Intent
        fun newIntent(context: Context,phoneNumber : String): Intent {
            mIntent = Intent(context, VerifyOTPActivity::class.java)
            mIntent.putExtra("number",phoneNumber)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_o_t_p)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        buttonOTPVerifyAndProceed.setOnClickListener(this)
        Number = intent.getSerializableExtra("number") as String
        textViewResend = findViewById(R.id.textViewResend)
        textViewMobile.text = Number
        reverseTimer(60,textViewOTPTime)
        val ss = SpannableString(getString(R.string.text_did_not_receive_the_code))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Toast.makeText(this@VerifyOTPActivity, "OTP sent again succesfully!", Toast.LENGTH_SHORT).show()
                textViewResend.movementMethod = null
                reverseTimer(60,textViewOTPTime)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        ss.setSpan(clickableSpan, 26, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textViewResend.text = ss
        //textViewResend.movementMethod = LinkMovementMethod.getInstance()

        editTextOTPOne.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if(count > 0){
                    editTextOTPTwo.requestFocus()
                }
            }
        })
        editTextOTPTwo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if(count > 0){
                    editTextOTPThree.requestFocus()
                }
            }
        })
        editTextOTPThree.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if(count > 0){
                    editTextOTPFour.requestFocus()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> true
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonOTPVerifyAndProceed -> verifyOtp1()
        }
    }

    private fun reverseTimer(Seconds: Int, tv: TextView) {
        object : CountDownTimer((Seconds * 1000 + 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                seconds = seconds % 60
                tv.text = String.format("%02d", minutes) + ":" + String.format("%02d", seconds)
            }
            override fun onFinish() {
                tv.text = "0.00"
                textViewResend.movementMethod = LinkMovementMethod.getInstance()
            }
        }.start()
    }

    private fun verifyOtp1() {
        /*Form Validation*/
        val firstDigit = editTextOTPOne.text.toString().trim()
        val secondDigit = editTextOTPTwo.text.toString().trim()
        val thirdDigit = editTextOTPThree.text.toString().trim()
        val fourthDigit = editTextOTPFour.text.toString().trim()

        if (firstDigit == "" || secondDigit == "" || thirdDigit == "" || fourthDigit == "") {
            val toast = Toast.makeText(this, "Please enter the OTP to continue!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return
        }
        startActivity(DashboardMainActivity.newIntent(this))
    }
        /*Form Validation*/

    private fun verifyOtp(){
        /*Form Validation*/
        val firstDigit = editTextOTPOne.text.toString().trim()
        val secondDigit = editTextOTPTwo.text.toString().trim()
        val thirdDigit = editTextOTPThree.text.toString().trim()
        val fourthDigit = editTextOTPFour.text.toString().trim()

        if (firstDigit == "" || secondDigit == "" || thirdDigit == "" || fourthDigit == "") {
            val toast = Toast.makeText(this, "Please enter the OTP to continue!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return
        }
        /*Form Validation*/

        if (!APIClient.isNetworkConnected(applicationContext)) {
            AlertDialogManager.instance.displayNoConnectionAlert(this)
            return
        }

        // loadingViewManager.showLoadingView(this@LoginActivity, "Logging in...")
        /*mainView.visibility = View.GONE
        linearLayoutLoading.visibility = View.VISIBLE
        textViewProgressMessage.text = "Logging in..."*/

        val call: Call<LoggedInUser> = APIClient.apiInterface
            .verifySignUpOTP(mLoggedInUser.userName,
                firstDigit + secondDigit + thirdDigit + fourthDigit)
        call.enqueue(object: Callback<LoggedInUser> {

            override fun onResponse(call: Call<LoggedInUser>, response: Response<LoggedInUser>) {
                // loadingViewManager.hideLoadingView()
                /*mainView.visibility = View.VISIBLE
                linearLayoutLoading.visibility = View.GONE*/

                if(response.isSuccessful) {
                    val user: LoggedInUser ?= response.body()
                    if(user != null){
                        user.isSocialLogin = false
                        val loginHelper = LoginHelper(this@VerifyOTPActivity, user)
                        if(loginHelper.saveUpdatedLoggedInUser(this@VerifyOTPActivity)){

                            /*if(mFinishActivityOnLoginSuccess){
                                setResult(Activity.RESULT_OK)
                                finish()
                            } else {
                                startActivity(MainActivity.newIntentAsNewTask(this@VerifyOTPActivity))
                            }*/

                        } else {
                            AlertDialogManager.instance.
                                showAlertDialog(this@VerifyOTPActivity,
                                    R.drawable.ic_warning_black_24dp,
                                    getString(R.string.alert_login_failed_title),
                                    getString(R.string.alert_login_error_message))
                        }
                    } else {
                        // server returned 200 with a blank response :/
                        ErrorUtils.logNetworkError(
                            ServerInvalidResponseException
                                .ERROR_200_BLANK_RESPONSE + "\nResponse: " + response.toString(), null)
                        AlertDialogManager.instance.displayInvalidResponseAlert(this@VerifyOTPActivity)
                        // server returned 200 with a blank response :/
                    }
                }
            }

            override fun onFailure(call: Call<LoggedInUser>, t: Throwable) {
                ErrorUtils.parseOnFailureException(this@VerifyOTPActivity, call, t, null)
            }
        })
    }

    private fun resendOtp(){
        if (!APIClient.isNetworkConnected(applicationContext)) {
            AlertDialogManager.instance.displayNoConnectionAlert(this)
            return
        }

        val call: Call<APIActionResponse> = APIClient.apiInterface.requestSignUpOTP(mLoggedInUser.userName)
        call.enqueue(object: Callback<APIActionResponse> {

            override fun onResponse(call: Call<APIActionResponse>, response: Response<APIActionResponse>) {

                if(response.isSuccessful) {
                    val apiActionResponse: APIActionResponse ?= response.body()
                    if(apiActionResponse != null){
                        if(apiActionResponse.isActionSuccess){

                            AlertDialogManager.instance.showAlertDialog(
                                this@VerifyOTPActivity, R.drawable.ic_check_circle_black_24dp,
                                apiActionResponse.title, apiActionResponse.message)

                        } else {
                            AlertDialogManager.instance.showAlertDialog(
                                this@VerifyOTPActivity, R.drawable.ic_warning_black_24dp,
                                apiActionResponse.title, apiActionResponse.message)
                        }
                    } else {
                        // server returned 200 with a blank response :/
                        ErrorUtils.logNetworkError(
                            ServerInvalidResponseException
                                .ERROR_200_BLANK_RESPONSE + "\nResponse: " + response.toString(), null)
                        AlertDialogManager.instance.displayInvalidResponseAlert(this@VerifyOTPActivity)
                        // server returned 200 with a blank response :/
                    }
                }
            }

            override fun onFailure(call: Call<APIActionResponse>, t: Throwable) {
                ErrorUtils.parseOnFailureException(this@VerifyOTPActivity, call, t, null)
            }
        })
    }
}
