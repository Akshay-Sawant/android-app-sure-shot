package com.sureshots.app.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.easebuzz.payment.kit.PWECouponsActivity
import com.sureshots.app.R
import com.sureshots.app.adapter.SubscriptionAdapter
import com.sureshots.app.model.Subscription
import datamodels.PWEStaticDataModel
import kotlinx.android.synthetic.main.activity_subscription.*
import org.json.JSONObject
import java.math.BigInteger
import java.security.MessageDigest

class SubscriptionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mRecyclerViewSubscription: RecyclerView
    private lateinit var mSubscriptionAdapter: SubscriptionAdapter
    private var mSubscriptionModelList: ArrayList<Subscription> = ArrayList()

    private lateinit var merchant_trxnId: String
    private lateinit var merchant_payment_amount: String
    private lateinit var merchant_productInfo: String
    private lateinit var customer_firstName: String
    private lateinit var customer_email_id: String
    private lateinit var customer_phone: String
    private lateinit var merchant_key: String
    private lateinit var hash: String
    private lateinit var customers_unique_id: String
    private lateinit var payment_mode: String
    private lateinit var udf1: String
    private lateinit var udf2: String
    private lateinit var udf3: String
    private lateinit var udf4: String
    private lateinit var udf5: String

    companion object {
        private lateinit var mIntent: Intent
        fun newIntent(context: Context): Intent {
            mIntent = Intent(context, SubscriptionActivity::class.java)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        buttonPayNow.setOnClickListener(this)
        mRecyclerViewSubscription = findViewById(R.id.recyclerViewSubscription)
        mSubscriptionModelList.clear()
        mSubscriptionAdapter = this.let {
            SubscriptionAdapter(
                it,
                R.layout.recycler_view_subscriptions,
                mSubscriptionModelList
            )
        }
        mRecyclerViewSubscription.adapter = mSubscriptionAdapter
        mSubscriptionAdapter.notifyDataSetChanged()

        onLoadSubscriptions()

        val salt = "DAH88E3UWQ"
        merchant_trxnId = "1001"
        merchant_payment_amount = "100"
        merchant_productInfo = "Headphones"
        customer_firstName = "Suraj"
        customer_email_id = "suraj@innovins.com"
        customer_phone = "9970783832"
        merchant_key = "2PBP7IABZ2"
        customers_unique_id = "199SS"
        payment_mode = "test"
        udf1 = ""
        udf2 = ""
        udf3 = ""
        udf4 = ""
        udf5 = ""
        val hash_string =
            "$merchant_key|$merchant_trxnId|${merchant_payment_amount.toDouble()}|$merchant_productInfo|$customer_firstName|$customer_email_id|$udf1|$udf2|$udf3|$udf4|$udf5||||||$salt|$merchant_key"
        hash = getSHA512(hash_string)
    }

    fun getSHA512(input: String): String {
        val md: MessageDigest = MessageDigest.getInstance("SHA-512")
        val messageDigest = md.digest(input.toByteArray())

        // Convert byte array into signum representation
        val no = BigInteger(1, messageDigest)

        // Convert message digest into hex value
        var hashtext: String = no.toString(16)

        // Add preceding 0s to make it 32 bit
        while (hashtext.length < 32) {
            hashtext = "0$hashtext"
        }

        // return the HashText
        return hashtext
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> true
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonPayNow -> {
                val intentProceed =
                    Intent(this@SubscriptionActivity, PWECouponsActivity::class.java)
                intentProceed.flags =
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT // This is mandatory flag

                intentProceed.putExtra("txnid", merchant_trxnId)
                intentProceed.putExtra("amount", merchant_payment_amount.toDouble())
                intentProceed.putExtra("productinfo", merchant_productInfo)
                intentProceed.putExtra("firstname", customer_firstName)
                intentProceed.putExtra("email", customer_email_id)
                intentProceed.putExtra("phone", customer_phone)
                intentProceed.putExtra("key", merchant_key)
                intentProceed.putExtra("udf1", udf1);
                intentProceed.putExtra("udf2", udf2);
                intentProceed.putExtra("udf3", udf3);
                intentProceed.putExtra("udf4", udf4);
                intentProceed.putExtra("udf5", udf5)
                intentProceed.putExtra("hash", hash)
                intentProceed.putExtra("unique_id", customers_unique_id)
                intentProceed.putExtra("pay_mode", payment_mode)
                startActivityForResult(intentProceed, PWEStaticDataModel.PWE_REQUEST_CODE)
            }
            //startActivity(PaymentSuccessActivity.newIntent(this))
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == PWEStaticDataModel.PWE_REQUEST_CODE) {
                val result = data.getStringExtra("result")
                val payment_response = data.getStringExtra("payment_response")
                when (result) {
                    PWEStaticDataModel.TXN_SUCCESS_CODE -> {
                        val mainObject = JSONObject(payment_response)
                        val pay_txnid = mainObject.getString("txnid")
                        val pay_firstname = mainObject.getString("firstname")
                        val pay_status = mainObject.getString("status")
                        val pay_phone = mainObject.getString("phone")
                        val pay_amount = mainObject.getString("amount")
                        startActivity(
                            PaymentSuccessActivity.newIntent(
                                this,
                                pay_txnid,
                                pay_firstname,
                                pay_status,
                                pay_phone,
                                pay_amount
                            )
                        )
                    }
                    PWEStaticDataModel.TXN_FAILED_CODE,PWEStaticDataModel. TXN_ERROR_NO_RETRY_CODE,PWEStaticDataModel. TXN_ERROR_RETRY_FAILED_CODE -> Toast.makeText(this,"Payment Failed!",Toast.LENGTH_SHORT).show()
                    PWEStaticDataModel. TXN_INVALID_INPUT_DATA_CODE -> Toast.makeText(this,"Payment Failed, Invalid input data",Toast.LENGTH_SHORT).show()
                    PWEStaticDataModel. TXN_TIMEOUT_CODE -> Toast.makeText(this,"Sessiom Timeout!",Toast.LENGTH_SHORT).show()
                    PWEStaticDataModel. TXN_USERCANCELLED_CODE, PWEStaticDataModel. TXN_BACKPRESSED_CODE,PWEStaticDataModel.TXN_BANK_BACK_PRESSED_CODE -> Toast.makeText(this,"Transaction Cancelled!",Toast.LENGTH_SHORT).show()
                    PWEStaticDataModel. TXN_ERROR_SERVER_ERROR_CODE -> Toast.makeText(this,"An error occured at our server!",Toast.LENGTH_SHORT).show()
                    PWEStaticDataModel. TXN_ERROR_TXN_NOT_ALLOWED_CODE -> Toast.makeText(this,"There seems problem, transaction not allowed from your bank!",Toast.LENGTH_SHORT).show()
                }

                try { // Handle response here
                } catch (e: Exception) { // Handle exception here
                }
            }
        }
    }

    private fun onLoadSubscriptions() {
        mSubscriptionModelList.add(
            Subscription(
                "1",
                "Fixed discounts for recharges as per chart ( In chart we will give range and mention the present %ages)."
            )
        )
        mSubscriptionModelList.add(Subscription("2", "No cashbacks only Discounts."))
        mSubscriptionModelList.add(Subscription("3", "No validity period."))
        mSubscriptionModelList.add(
            Subscription(
                "4",
                "You will automatically subscribed for discounts of future services in this app."
            )
        )
        mSubscriptionModelList.add(
            Subscription(
                "5",
                "Also you will be eligible for referral eamings. (As referral cash in wallet)"
            )
        )
    }
}
