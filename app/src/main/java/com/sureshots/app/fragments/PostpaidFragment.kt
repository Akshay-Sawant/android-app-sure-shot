package com.sureshots.app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.sureshots.app.R
import com.sureshots.app.activity.DashboardMainActivity
import com.sureshots.app.activity.RechargeTwoActivity
import kotlinx.android.synthetic.main.fragment_postpaid.view.*

/**
 * A simple [Fragment] subclass.
 */
class PostpaidFragment : Fragment(R.layout.fragment_postpaid),View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.buttonProceed.setOnClickListener(this)
        view.textViewChange.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonProceed -> view?.let {
                startActivity(RechargeTwoActivity.newIntentNewTask(requireContext()))
                //Navigation.findNavController(it).navigate(R.id.action_rechargeOneFragment_to_rechargeTwoFragment)
            }
            R.id.textViewChange -> view?.let {
                //startActivity(DashboardMainActivity.newIntentFromPostpaid(requireContext()))
                //Navigation.findNavController(it).navigate(R.id.action_rechargeOneFragment_to_dashboardFragment)
            }
        }
    }
}
