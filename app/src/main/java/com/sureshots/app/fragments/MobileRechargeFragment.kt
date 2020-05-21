package com.sureshots.app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

import com.sureshots.app.R
import com.sureshots.app.adapter.SimCompanyAdapter
import com.sureshots.app.model.SimCompanyModel
import kotlinx.android.synthetic.main.fragment_mobile_recharge.view.*

class MobileRechargeFragment : Fragment(R.layout.fragment_mobile_recharge),View.OnClickListener {

    private lateinit var mRecyclerViewSimCompany: RecyclerView
    private lateinit var mSimCompanyAdapter: SimCompanyAdapter
    private var mSimCompanyModelList: ArrayList<SimCompanyModel> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.materialcardReferEarn.setOnClickListener(this)
        mRecyclerViewSimCompany = view.findViewById(R.id.recyclerViewSimCompany)
        mSimCompanyModelList.clear()
        mSimCompanyAdapter = context?.let {
            SimCompanyAdapter(
                it,
                R.layout.recycler_view_sim_company,
                mSimCompanyModelList
            )
        }!!
        mRecyclerViewSimCompany.adapter = mSimCompanyAdapter
        mSimCompanyAdapter.notifyDataSetChanged()

        onLoadSimCompany()
    }

    private fun onLoadSimCompany() {
        mSimCompanyModelList.add(SimCompanyModel("1", "Airtel", R.drawable.airtel))
        mSimCompanyModelList.add(SimCompanyModel("2", "Jio", R.drawable.jio))
        mSimCompanyModelList.add(SimCompanyModel("3", "BSNL", R.drawable.bsnl))
        mSimCompanyModelList.add(SimCompanyModel("4", "idea", R.drawable.idea))
        mSimCompanyModelList.add(SimCompanyModel("5", "vodafone", R.drawable.vodafone))
        mSimCompanyModelList.add(SimCompanyModel("6", "Tata Docomo", R.drawable.docomo))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.materialcardReferEarn -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboard_to_referEarnFragment)
            }
        }
    }

}
