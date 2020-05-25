package com.sureshots.app.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.sureshots.app.R
import com.sureshots.app.fragments.SignUpFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
    ,NavController.OnDestinationChangedListener
    /*,NavigationView.OnNavigationItemSelectedListener*/ {

    private lateinit var mNavController: NavController
    //private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    val signUpFragment = SignUpFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)

        /*if(savedInstanceState == null) { // initial transaction should be wrapped like this
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, signUpFragment)
                .commitAllowingStateLoss()
        }*/
        mNavController = Navigation.findNavController(this,R.id.sure_shot_nav_host)
        NavigationUI.setupWithNavController(navigationView, mNavController)
        NavigationUI.setupActionBarWithNavController(this, mNavController, drawerLayout)
        //navigationView.setNavigationItemSelectedListener(this)
        mNavController.addOnDestinationChangedListener(this@MainActivity)

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(mNavController, drawerLayout)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        when (destination.id) {
            R.id.SignUpFragment -> {
                supportActionBar?.hide()
                toolbar.visibility = View.GONE
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
            R.id.DashboardFragment -> {
                supportActionBar?.show()
                toolbar.visibility = View.VISIBLE
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
            else -> {
                supportActionBar?.show()
                toolbar.visibility = View.VISIBLE
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
    }

    /*override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true
        drawerLayout.closeDrawers()
        val id = menuItem.itemId
        when (id) {
            R.id.first -> mNavController.navigate(R.id.RechargeOneFragment)
            R.id.second -> mNavController.navigate(R.id.RechargeTwoFragment)
            R.id.third -> mNavController.navigate(R.id.ReferEarnFragment)
        }
        return true
    }*/
}
