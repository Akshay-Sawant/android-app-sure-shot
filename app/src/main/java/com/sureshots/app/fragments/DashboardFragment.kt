package com.sureshots.app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import com.sureshots.app.R
import com.sureshots.app.adapter.DashboadTabAdapter

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var mTabLayoutDashboard: TabLayout
    private lateinit var mViewPagerTwoDashboard: ViewPager2
    private lateinit var mDashboardTabAdapter: DashboadTabAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTabLayoutDashboard = view.findViewById(R.id.tabLayoutDashboard)
        mViewPagerTwoDashboard = view.findViewById(R.id.viewPagerDashboard)
        mDashboardTabAdapter = DashboadTabAdapter(activity as FragmentActivity)

        mViewPagerTwoDashboard.adapter = mDashboardTabAdapter

        TabLayoutMediator(mTabLayoutDashboard, mViewPagerTwoDashboard,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Mobile Recharge"
                        tab.setIcon(R.drawable.ic_mobile_24dp)
                    }
                    1 -> {
                        tab.text = "DTH"
                        tab.setIcon(R.drawable.ic_toys_black_24dp)
                    }
                    else -> {
                        tab.text = "Mobile Recharge"
                        tab.setIcon(R.drawable.ic_mobile_24dp)
                    }
                }
            }).attach()

    }

}
