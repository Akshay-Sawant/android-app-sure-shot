package com.sureshots.app.fragments

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation

import com.sureshots.app.R
/*import com.sureshots.app.activity.DashboardMainActivity
import com.sureshots.app.activity.RechargeTwoActivity*/
import kotlinx.android.synthetic.main.fragment_prepaid.*
import kotlinx.android.synthetic.main.fragment_prepaid.view.*

/**
 * A simple [Fragment] subclass.
 */
class PrepaidFragment : Fragment(R.layout.fragment_prepaid),View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.buttonProceed.setOnClickListener(this)
        view.textViewChange.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonProceed -> view?.let {
                prepaidNumber()
                /*Navigation.findNavController(it)
                    .navigate(R.id.action_rechargeOneFragment_to_rechargeTwoFragment)*/
            }
            R.id.textViewChange -> view?.let {
                //startActivity(DashboardMainActivity.newIntentFromPrepaid(requireContext()))
                //Navigation.findNavController(it).navigate(R.id.action_rechargeOneFragment_to_dashboardFragment)
            }
        }
    }

    private fun prepaidNumber(){
        textInputLayoutMobileNumber.error =null
        val number = textInputMobileNumber.text.toString().trim()
        var hasError = false
        if(number == ""){
            textInputLayoutMobileNumber.error = "Enter your mobile number!"
            hasError = true
        }
        if (hasError) {
            val toast = Toast.makeText(requireContext(), "Please correct highlighted fields!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return
        }
//        startActivity(RechargeTwoActivity.newIntent(requireContext(),number))
    }

}
