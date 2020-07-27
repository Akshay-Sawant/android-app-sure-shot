package com.sureshotdiscount.app.ui.myaccount

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils

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

        onBackPress()
    }

    override fun onResume() {
        super.onResume()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
    }

    private fun onBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    context?.let {
                        AlertDialogUtils.getInstance().showAlert(
                            it,
                            R.drawable.ic_warning_black,
                            "Close App",
                            "Are you sure you want to exit from this app?",
                            getString(android.R.string.ok),
                            DialogInterface.OnClickListener { dialog, _ ->
                                activity?.finish()
                                dialog.dismiss()
                            },
                            getString(android.R.string.cancel),
                            DialogInterface.OnClickListener { dialog, _ ->
                                dialog.dismiss()
                            }
                        )
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}