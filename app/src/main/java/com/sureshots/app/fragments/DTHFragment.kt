package com.sureshots.app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sureshots.app.MiddleDividerItemDecoration

import com.sureshots.app.R
import com.sureshots.app.adapter.DTHCompanyAdapter
import com.sureshots.app.model.DTHCompanyModel

/**
 * A simple [Fragment] subclass.
 */
class DTHFragment : Fragment(R.layout.fragment_d_t_h) {

    private lateinit var mRecyclerViewDTHCompany: RecyclerView
    private lateinit var mDTHCompanyAdapter: DTHCompanyAdapter
    private var mDTHCompanyModelList: ArrayList<DTHCompanyModel> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    private fun onLoadDTHCompany() {
        mDTHCompanyModelList.add(DTHCompanyModel("1", "Airtel Digital TV", R.drawable.airtel))
        mDTHCompanyModelList.add(DTHCompanyModel("2", "Dish TV", R.drawable.jio))
        mDTHCompanyModelList.add(DTHCompanyModel("3", "Reliance Digital TV", R.drawable.bsnl))
        mDTHCompanyModelList.add(DTHCompanyModel("4", "Sun Direct", R.drawable.idea))
        mDTHCompanyModelList.add(DTHCompanyModel("5", "Tata Sky", R.drawable.vodafone))
        mDTHCompanyModelList.add(DTHCompanyModel("6", "D2H", R.drawable.docomo))
    }

}
