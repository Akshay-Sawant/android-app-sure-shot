package com.sureshots.app.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation

import com.sureshots.app.R
import kotlinx.android.synthetic.main.fragment_verify_o_t_p.view.*

/**
 * A simple [Fragment] subclass.
 */
class VerifyOTPFragment : Fragment(R.layout.fragment_verify_o_t_p),View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.buttonOTPVerifyAndProceed.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonOTPVerifyAndProceed -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_verifyOTP_to_dashboard)
            }
        }
    }

}
