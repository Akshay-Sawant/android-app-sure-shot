package com.sureshots.app.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.sureshots.app.R

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var mToolbarMain: Toolbar
    private lateinit var mDrawerLayoutMain: DrawerLayout
    private lateinit var mNavigationViewMain: NavigationView
    private lateinit var mNavControllerMain: NavController
    private lateinit var mAppBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mToolbarMain = findViewById(R.id.toolbarMain)

        setSupportActionBar(mToolbarMain)

        mDrawerLayoutMain = findViewById(R.id.drawerLayoutMain)
        mNavigationViewMain = findViewById(R.id.navigationViewMain)

        mNavControllerMain = findNavController(R.id.fragmentNavHost)
        mAppBarConfiguration = AppBarConfiguration(setOf(R.id.DashboardFragment), mDrawerLayoutMain)

        setupActionBarWithNavController(mNavControllerMain, mAppBarConfiguration)

        mNavigationViewMain.setupWithNavController(mNavControllerMain)
        mNavControllerMain.addOnDestinationChangedListener(this@MainActivity)
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
            R.id.SignUpFragment -> {
                supportActionBar?.hide()
                mToolbarMain.visibility = View.GONE
                mDrawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
            /*R.id.DashboardFragment -> {
                supportActionBar?.show()
                toolbar.visibility = View.VISIBLE
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }*/
            else -> {
                supportActionBar?.show()
                mToolbarMain.visibility = View.VISIBLE
                mDrawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
    }
}