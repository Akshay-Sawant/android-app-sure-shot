package com.sureshots.app.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sureshots.app.R

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
        mDashboardTabAdapter =
            DashboadTabAdapter(activity as FragmentActivity)

        mViewPagerTwoDashboard.adapter = mDashboardTabAdapter

        TabLayoutMediator(mTabLayoutDashboard, mViewPagerTwoDashboard,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = getString(R.string.text_label_mobile_recharge)
                        tab.setIcon(R.drawable.ic_smartphone)
                    }
                    1 -> {
                        tab.text = getString(R.string.text_label_dth)
                        tab.setIcon(R.drawable.ic_satellite_tv)
                    }
                    else -> {
                        tab.text = getString(R.string.text_label_mobile_recharge)
                        tab.setIcon(R.drawable.ic_smartphone)
                    }
                }
            }).attach()
    }
}