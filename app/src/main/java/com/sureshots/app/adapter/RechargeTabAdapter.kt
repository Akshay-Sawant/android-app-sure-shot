package com.sureshots.app.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sureshots.app.fragments.DTHFragment
import com.sureshots.app.fragments.MobileRechargeFragment
import com.sureshots.app.fragments.PostpaidFragment
import com.sureshots.app.fragments.PrepaidFragment

class RechargeTabAdapter(mFragmentActivity: FragmentActivity): FragmentStateAdapter(mFragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PrepaidFragment()
            1 -> PostpaidFragment()
            else -> PrepaidFragment()
        }
    }
}