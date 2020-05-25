package com.sureshots.app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.sureshots.app.R
import kotlinx.android.synthetic.main.fragment_recharge_two.view.*

/**
 * A simple [Fragment] subclass.
 */
class RechargeTwoFragment : Fragment(R.layout.fragment_recharge_two),View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.buttonProceed.setOnClickListener(this)
        view.textViewChangeSim.setOnClickListener(this)
        view.textViewChangeNumber.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonProceed -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_rechargeTwoFragment_to_subscriptionFragment)
            }
            R.id.textViewChangeSim -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_rechargeTwoFragment_to_dashboardFragment)
            }
            R.id.textViewChangeNumber -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_rechargeTwoFragment_to_rechargeOneFragment)
            }
        }
    }

}
