package com.sureshots.app.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sureshots.app.R
import com.sureshots.app.adapter.RechargeTabAdapter

class RechargeOneActivity : AppCompatActivity() {

    private lateinit var mTabLayoutRecharge: TabLayout
    private lateinit var mViewPagerTwoRecharge: ViewPager2
    private lateinit var mRechargeTabAdapter: RechargeTabAdapter

    companion object {
        private lateinit var mIntent: Intent
        fun newIntent(context: Context): Intent {
            mIntent = Intent(context, RechargeOneActivity::class.java)
            return mIntent
        }
        fun newIntentNewTask(context: Context): Intent {
            mIntent = Intent(context, RechargeOneActivity::class.java)
            return mIntent
        }
        fun newIntentFromReTwo(context: Context): Intent {
            mIntent = Intent(context, RechargeOneActivity::class.java)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge_one)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mTabLayoutRecharge = findViewById(R.id.tabLayoutRecharge)
        mViewPagerTwoRecharge = findViewById(R.id.viewPagerRecharge)
        mRechargeTabAdapter = RechargeTabAdapter(this)
        mViewPagerTwoRecharge.adapter = mRechargeTabAdapter

        TabLayoutMediator(mTabLayoutRecharge, mViewPagerTwoRecharge,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Prepaid"
                    }
                    1 -> {
                        tab.text = "Postpaid"
                    }
                    else -> {
                        tab.text = "Prepaid"
                    }
                }
            }).attach()
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
}
