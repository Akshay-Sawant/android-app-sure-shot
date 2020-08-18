package com.sureshotdiscount.app.ui.referralslist.withdraw

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class WithdrawTabAdapter(mFragmentActivity: FragmentActivity) :
    FragmentStateAdapter(mFragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PaytmFragment()
            1 -> BankFragment()
            else -> PaytmFragment()
        }
    }
}