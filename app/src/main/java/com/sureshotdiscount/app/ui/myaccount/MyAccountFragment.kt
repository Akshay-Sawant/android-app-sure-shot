package com.sureshotdiscount.app.ui.myaccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sureshotdiscount.app.R

/**
 * A simple [Fragment] subclass.
 */
class MyAccountFragment : Fragment(R.layout.fragment_my_account) {

    private lateinit var mTabLayoutDashboard: TabLayout
    private lateinit var mViewPagerTwoDashboard: ViewPager2
    private lateinit var mDashboardTabAdapter: MyAccountTabAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTabLayoutDashboard = view.findViewById(R.id.tabLayoutDashboard)
        mViewPagerTwoDashboard = view.findViewById(R.id.viewPagerDashboard)
        mDashboardTabAdapter =
            MyAccountTabAdapter(activity as FragmentActivity)

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