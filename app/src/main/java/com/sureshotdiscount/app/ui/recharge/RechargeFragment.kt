package com.sureshotdiscount.app.ui.recharge

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
class RechargeFragment : Fragment(R.layout.fragment_recharge) {

    private lateinit var mTabLayoutRecharge: TabLayout
    private lateinit var mViewPagerTwoRecharge: ViewPager2
    private lateinit var mRechargeTabAdapter: RechargeTabAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTabLayoutRecharge = view.findViewById(R.id.tabLayoutRecharge)
        mViewPagerTwoRecharge = view.findViewById(R.id.viewPagerRecharge)
        mRechargeTabAdapter =
            RechargeTabAdapter(activity as FragmentActivity)
        mViewPagerTwoRecharge.adapter = mRechargeTabAdapter

        TabLayoutMediator(mTabLayoutRecharge, mViewPagerTwoRecharge,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.text_label_prepaid)
                    1 -> tab.text = getString(R.string.text_label_postpaid)
                    else -> tab.text = getString(R.string.text_label_prepaid)
                }
            }).attach()
    }
}