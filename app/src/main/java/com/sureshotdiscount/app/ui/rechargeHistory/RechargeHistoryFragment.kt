package com.sureshotdiscount.app.ui.rechargeHistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sureshotdiscount.app.R

class RechargeHistoryFragment : Fragment(R.layout.fragment_recharge_history) {

    private lateinit var mRecyclerViewRechargeHistory: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerViewRechargeHistory = view.findViewById(R.id.recyclerViewRechargeHistory)
    }
}