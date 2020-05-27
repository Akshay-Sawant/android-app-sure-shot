package com.sureshots.app.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.sureshots.app.R
import kotlinx.android.synthetic.main.activity_payment_success.*

class PaymentSuccessActivity : AppCompatActivity(),View.OnClickListener {

    companion object {
        private lateinit var mIntent: Intent
        fun newIntent(context: Context): Intent {
            mIntent = Intent(context, PaymentSuccessActivity::class.java)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        buttonContinue.setOnClickListener(this)
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

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.buttonContinue -> startActivity(DashboardMainActivity.newIntentFromPaySuccess(this))
        }
    }
}
