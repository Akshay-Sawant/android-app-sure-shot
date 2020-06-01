package com.sureshots.app.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.innovins.helperlibrary.constant.REQUEST_CODE_LOGIN_SUCCESS
import com.innovins.helperlibrary.helper.AlertDialogManager
import com.innovins.helperlibrary.helper.LoadingViewManager
import com.sureshots.app.R
import com.sureshots.app.login.LoggedInUser
import com.sureshots.app.network.APIClient
import com.sureshots.app.network.ErrorUtils
import com.sureshots.app.network.ServerInvalidResponseException
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var loadingViewManager: LoadingViewManager

    companion object {
        private lateinit var mIntent: Intent
        fun newIntent(context: Context): Intent {
            mIntent = Intent(context, SignUpActivity::class.java)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        buttonContinue.setOnClickListener(this)
        loadingViewManager = LoadingViewManager(this, R.id.mainView, null)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonContinue ->  signUp()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CODE_LOGIN_SUCCESS){
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun signUp(){
        textInputLayoutMobileNumber.error =null
        val number = textInputMobileNumber.text.toString().trim()
        var hasError = false
        if(number == ""){
            textInputLayoutMobileNumber.error = "Enter your mobile number!"
            hasError = true
        }
        if (hasError) {
            val toast = Toast.makeText(this, "Please correct highlighted fields!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return
        }
        /*if (!APIClient.isNetworkConnected(applicationContext)) {
            AlertDialogManager.instance.displayNoConnectionAlert(this)
            return
        }*/
        startActivity(VerifyOTPActivity.newIntent(this,number))
        /*loadingViewManager.showLoadingView(this@SignUpActivity, "Signing up for an account, please wait...")

        val call: Call<LoggedInUser> = APIClient.apiInterface.doSignUp(number)
        call.enqueue(object: Callback<LoggedInUser> {

            override fun onResponse(call: Call<LoggedInUser>, response: Response<LoggedInUser>) {
                loadingViewManager.hideLoadingView()

                if(response.isSuccessful) {
                    val user: LoggedInUser?= response.body()
                    if(user != null){
                        user.isSocialLogin = false

                        *//*startActivityForResult(
                            VerifyOTPActivity.newIntentForResult(this@SignUpActivity, user, mFinishOnSuccess),
                            REQUEST_CODE_LOGIN_SUCCESS
                            //startActivity(VerifyOTPActivity.newIntent(this,number))
                        )*//*

                    } else {
                        // server returned 200 with a blank response :/
                        ErrorUtils.logNetworkError(
                            ServerInvalidResponseException
                                .ERROR_200_BLANK_RESPONSE + "\nResponse: " + response.toString(), null)
                        AlertDialogManager.instance.displayInvalidResponseAlert(this@SignUpActivity)
                        // server returned 200 with a blank response :/
                    }
                }
            }

            override fun onFailure(call: Call<LoggedInUser>, t: Throwable) {
                loadingViewManager.hideLoadingView()
                ErrorUtils.parseOnFailureException(this@SignUpActivity, call, t, null)
            }
        })*/

    }
}
