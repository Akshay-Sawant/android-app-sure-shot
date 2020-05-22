package com.sureshots.app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sureshots.app.MiddleDividerItemDecoration

import com.sureshots.app.R
import com.sureshots.app.adapter.DTHCompanyAdapter
import com.sureshots.app.model.DTHCompanyModel
import kotlinx.android.synthetic.main.fragment_d_t_h.view.*

/**
 * A simple [Fragment] subclass.
 */
class DTHFragment : Fragment(R.layout.fragment_d_t_h),View.OnClickListener {

    private lateinit var mRecyclerViewDTHCompany: RecyclerView
    private lateinit var mDTHCompanyAdapter: DTHCompanyAdapter
    private var mDTHCompanyModelList: ArrayList<DTHCompanyModel> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.buttonReferNow.setOnClickListener(this)
        mRecyclerViewDTHCompany = view.findViewById(R.id.recyclerViewDishCompany)
        mDTHCompanyModelList.clear()
        mDTHCompanyAdapter = context?.let {
            DTHCompanyAdapter(
                it,
                R.layout.recycler_view_dth_company,
                mDTHCompanyModelList
            )
        }!!
        mRecyclerViewDTHCompany.addItemDecoration(MiddleDividerItemDecoration(requireContext(), MiddleDividerItemDecoration.ALL))
            mRecyclerViewDTHCompany.adapter = mDTHCompanyAdapter
            mDTHCompanyAdapter.notifyDataSetChanged()


        onLoadDTHCompany()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonReferNow -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboard_to_referEarnFragment)
            }
        }
    }

    private fun onLoadDTHCompany() {
        mDTHCompanyModelList.add(DTHCompanyModel("1", "Airtel Digital TV", R.drawable.airtel))
        mDTHCompanyModelList.add(DTHCompanyModel("2", "Dish TV", R.drawable.jio))
        mDTHCompanyModelList.add(DTHCompanyModel("3", "Reliance Digital TV", R.drawable.bsnl))
        mDTHCompanyModelList.add(DTHCompanyModel("4", "Sun Direct", R.drawable.idea))
        mDTHCompanyModelList.add(DTHCompanyModel("5", "Tata Sky", R.drawable.vodafone))
        mDTHCompanyModelList.add(DTHCompanyModel("6", "D2H", R.drawable.docomo))
    }

}
