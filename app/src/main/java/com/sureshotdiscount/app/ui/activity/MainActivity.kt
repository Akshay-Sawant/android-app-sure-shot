package com.sureshotdiscount.app.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var mToolbarMain: Toolbar
    private lateinit var mDrawerLayoutMain: DrawerLayout
    private lateinit var mNavigationViewMain: NavigationView
    private lateinit var mNavControllerMain: NavController
    private lateinit var mAppBarConfiguration: AppBarConfiguration

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils
    private lateinit var mLoginToken: String
    private var mDestination: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mToolbarMain = findViewById(R.id.toolbarMain)

        setSupportActionBar(mToolbarMain)

        mDrawerLayoutMain = findViewById(R.id.drawerLayoutMain)
        mNavigationViewMain = findViewById(R.id.navigationViewMain)

        mSharedPreferenceUtils = SharedPreferenceUtils(this@MainActivity)
        mLoginToken = mSharedPreferenceUtils.getLoggedInUser().loginToken

        mDestination = when {
            mLoginToken.isEmpty() -> {
                R.id.signUpFragment
            }
            else -> {
                R.id.myAccountFragment
            }
        }

        mNavControllerMain = findNavController(R.id.fragmentNavHost)
        mAppBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.myAccountFragment,
                R.id.rechargeHistoryFragment,
                R.id.subscriptionPlanFragment,
                R.id.referralsListFragment,
                R.id.contactUsFragment
            ),
            mDrawerLayoutMain
        )

        setupActionBarWithNavController(mNavControllerMain, mAppBarConfiguration)

        mNavigationViewMain.setupWithNavController(mNavControllerMain)
        mNavControllerMain.addOnDestinationChangedListener(this@MainActivity)

        val mNavHostFragment = fragmentNavHost as NavHostFragment
        val mGraphInflater = mNavHostFragment.navController.navInflater
        val mNavGraph = mGraphInflater.inflate(R.navigation.sure_shot_nav_graph)
        val mNavController = mNavHostFragment.navController

        mNavGraph.startDestination = mDestination
        mNavController.graph = mNavGraph
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavControllerMain.navigateUp(mAppBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (mDrawerLayoutMain.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayoutMain.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.signUpFragment -> {
                supportActionBar?.hide()
                mToolbarMain.visibility = View.GONE
                mDrawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
            else -> {
                supportActionBar?.show()
                mToolbarMain.visibility = View.VISIBLE
                mDrawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
    }
}