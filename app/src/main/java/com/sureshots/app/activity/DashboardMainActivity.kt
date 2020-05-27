package com.sureshots.app.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sureshots.app.R
import com.sureshots.app.adapter.DashboadTabAdapter
import kotlinx.android.synthetic.main.activity_dashboard_main.*

class DashboardMainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mTabLayoutDashboard: TabLayout
    private lateinit var mViewPagerTwoDashboard: ViewPager2
    private lateinit var mDashboardTabAdapter: DashboadTabAdapter

    companion object {
        private lateinit var mIntent: Intent
        fun newIntent(context: Context): Intent {
            mIntent = Intent(context, DashboardMainActivity::class.java)
            return mIntent
        }
        fun newIntentFromPaySuccess(context: Context): Intent {
            mIntent = Intent(context, DashboardMainActivity::class.java)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return mIntent
        }
        fun newIntentFromPrepaid(context: Context): Intent {
            mIntent = Intent(context, DashboardMainActivity::class.java)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return mIntent
        }
        fun newIntentFromPostpaid(context: Context): Intent {
            mIntent = Intent(context, DashboardMainActivity::class.java)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mTabLayoutDashboard = findViewById(R.id.tabLayoutDashboard)
        mViewPagerTwoDashboard = findViewById(R.id.viewPagerDashboard)
        mDashboardTabAdapter = DashboadTabAdapter(this)

        mViewPagerTwoDashboard.adapter = mDashboardTabAdapter

        TabLayoutMediator(mTabLayoutDashboard, mViewPagerTwoDashboard,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Mobile Recharge"
                        tab.setIcon(R.drawable.ic_smartphone)
                    }
                    1 -> {
                        tab.text = "DTH"
                        tab.setIcon(R.drawable.ic_satellite_tv)
                    }
                    else -> {
                        tab.text = "Mobile Recharge"
                        tab.setIcon(R.drawable.ic_smartphone)
                    }
                }
            }).attach()

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_recharge_history ->{
                //startActivity(PaymentSuccessActivity.newIntent(this))
            }
            R.id.nav_subscription ->{
                //startActivity(LeaveApplicationActivity.newIntent(this))
            }
            R.id.nav_referrals ->{
                //startActivity(OnBoardingActivity.newIntentToChangeLanguage(this))
            }
            R.id.nav_contact ->{
                //LoginHelper(this).requestLogout(this)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
