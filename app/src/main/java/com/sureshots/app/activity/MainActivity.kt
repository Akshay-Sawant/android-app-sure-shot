package com.sureshots.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.sureshots.app.R

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNavController = this@MainActivity.findNavController(R.id.sure_shot_nav_host)
        NavigationUI.setupActionBarWithNavController(this@MainActivity, mNavController)
        mNavController.addOnDestinationChangedListener(this@MainActivity)

    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.SignUpFragment -> supportActionBar?.hide()
            else -> supportActionBar?.show()
        }
    }
}
