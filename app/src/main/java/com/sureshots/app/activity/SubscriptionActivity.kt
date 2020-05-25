package com.sureshots.app.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sureshots.app.R
import com.sureshots.app.adapter.SubscriptionAdapter
import com.sureshots.app.model.Subscription
import kotlinx.android.synthetic.main.activity_subscription.*

class SubscriptionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mRecyclerViewSubscription: RecyclerView
    private lateinit var mSubscriptionAdapter: SubscriptionAdapter
    private var mSubscriptionModelList: ArrayList<Subscription> = ArrayList()

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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonPayNow -> startActivity(PaymentSuccessActivity.newIntent(this))
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
