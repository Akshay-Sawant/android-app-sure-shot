package com.sureshotdiscount.app.ui.rechargeHistory.withdraw

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sureshotdiscount.app.R

class WithdrawFragment : Fragment(R.layout.fragment_withdraw) {

    private lateinit var mTabLayoutWithdraw: TabLayout
    private lateinit var mViewPager2Withdraw: ViewPager2
    private lateinit var mWithdrawTabAdapter: WithdrawTabAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTabLayoutWithdraw = view.findViewById(R.id.tabLayoutWithdraw)
        mViewPager2Withdraw = view.findViewById(R.id.viewPagerTwoWithdraw)

        mWithdrawTabAdapter = WithdrawTabAdapter(activity as FragmentActivity)
        mViewPager2Withdraw.adapter = mWithdrawTabAdapter

        TabLayoutMediator(mTabLayoutWithdraw, mViewPager2Withdraw,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.text_label_paytm)
                    1 -> tab.text = getString(R.string.text_label_bank)
                    else -> tab.text = getString(R.string.text_label_paytm)
                }
            }).attach()
    }
}