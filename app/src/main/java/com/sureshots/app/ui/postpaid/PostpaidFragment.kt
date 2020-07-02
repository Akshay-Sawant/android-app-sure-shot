package com.sureshots.app.ui.postpaid

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation

import com.sureshots.app.R
//import com.sureshots.app.ui.activity.RechargeTwoActivity
import kotlinx.android.synthetic.main.fragment_postpaid.*
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
                postPaidNumber()
                //Navigation.findNavController(it).navigate(R.id.action_rechargeOneFragment_to_rechargeTwoFragment)
            }
            R.id.textViewChange -> view?.let {
                //startActivity(DashboardMainActivity.newIntentFromPostpaid(requireContext()))
                Navigation.findNavController(it).navigate(R.id.action_rechargeOneFragment_to_dashboardFragment)
            }
        }
    }

    private fun postPaidNumber(){
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
//        startActivity(RechargeTwoActivity.newIntentNewTask(requireContext(),number))
    }
}
