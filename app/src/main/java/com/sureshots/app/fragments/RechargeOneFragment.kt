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
import com.sureshots.app.adapter.RechargeTabAdapter

/**
 * A simple [Fragment] subclass.
 */
class RechargeOneFragment : Fragment(R.layout.fragment_recharge_one) {

    private lateinit var mTabLayoutRecharge: TabLayout
    private lateinit var mViewPagerTwoRecharge: ViewPager2
    private lateinit var mRechargeTabAdapter: RechargeTabAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTabLayoutRecharge = view.findViewById(R.id.tabLayoutRecharge)
        mViewPagerTwoRecharge = view.findViewById(R.id.viewPagerRecharge)
        mRechargeTabAdapter = RechargeTabAdapter(activity as FragmentActivity)
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

}
