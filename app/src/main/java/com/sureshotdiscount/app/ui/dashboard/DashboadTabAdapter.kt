package com.sureshotdiscount.app.ui.dashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sureshotdiscount.app.ui.dth.DTHFragment
import com.sureshotdiscount.app.ui.mobile.MobileRechargeFragment

class DashboadTabAdapter(mFragmentActivity: FragmentActivity) :
    FragmentStateAdapter(mFragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MobileRechargeFragment()
            1 -> DTHFragment()
            else -> MobileRechargeFragment()
        }
    }
}