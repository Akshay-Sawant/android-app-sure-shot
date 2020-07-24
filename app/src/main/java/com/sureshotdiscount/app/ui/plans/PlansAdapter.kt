package com.sureshotdiscount.app.ui.plans

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PlansAdapter(private val mItemLayout: Int, private val mPlansModelList: List<PlansModel>) :
    RecyclerView.Adapter<PlansAdapter.PlansViewHolder>() {

    inner class PlansViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlansViewHolder {
        return PlansViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    mItemLayout,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return mPlansModelList.size
    }

    override fun onBindViewHolder(holder: PlansViewHolder, position: Int) {

    }
}