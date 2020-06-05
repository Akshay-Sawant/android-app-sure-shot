package com.sureshots.app.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.sureshots.app.R

class SubscriptionMainActivity : AppCompatActivity() {

    companion object {
        private lateinit var mIntent: Intent
        fun newIntent(context: Context): Intent {
            mIntent = Intent(context, SubscriptionMainActivity::class.java)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription_main)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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


}
