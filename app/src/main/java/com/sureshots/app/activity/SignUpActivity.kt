package com.sureshots.app.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sureshots.app.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(),View.OnClickListener {

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
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonContinue -> startActivity(VerifyOTPActivity.newIntent(this))
        }
    }
}