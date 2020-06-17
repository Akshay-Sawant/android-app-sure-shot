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

    private lateinit var pay_txnid: String
    private lateinit var pay_firstname: String
    private lateinit var pay_status: String
    private lateinit var pay_phone: String
    private lateinit var pay_amount: String

    companion object {
        private lateinit var mIntent: Intent
        fun newIntent(context: Context,
                      pay_txnid: String,
                      pay_firstname: String,
                      pay_status: String,
                      pay_phone: String,
                      pay_amount: String): Intent {
            mIntent = Intent(context, PaymentSuccessActivity::class.java)
            mIntent.putExtra("pay_txnid",pay_txnid)
            mIntent.putExtra("pay_firstname",pay_firstname)
            mIntent.putExtra("pay_status",pay_status)
            mIntent.putExtra("pay_phone",pay_phone)
            mIntent.putExtra("pay_amount",pay_amount)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        buttonContinue.setOnClickListener(this)
        pay_txnid = intent.getStringExtra("pay_txnid") as String
        pay_firstname = intent.getStringExtra("pay_firstname") as String
        pay_status = intent.getStringExtra("pay_status") as String
        pay_phone = intent.getStringExtra("pay_phone") as String
        pay_amount = intent.getStringExtra("pay_amount") as String
        textViewMobileNumber.text = pay_phone
        textViewOrderId.text = getString(R.string.text_order_id,pay_txnid)
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
