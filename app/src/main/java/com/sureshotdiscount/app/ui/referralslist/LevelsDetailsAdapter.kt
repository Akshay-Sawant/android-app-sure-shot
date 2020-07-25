package com.sureshotdiscount.app.ui.referralslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class LevelsDetailsAdapter(
    private val mItemLayout: Int,
    private val mLevelsDetailsModel: List<LevelsDetailsModel>
) : RecyclerView.Adapter<LevelsDetailsAdapter.LevelsDetailsViewHolder>() {

    inner class LevelsDetailsViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelsDetailsViewHolder {
        return LevelsDetailsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    mItemLayout,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return mLevelsDetailsModel.size
    }

    override fun onBindViewHolder(holder: LevelsDetailsViewHolder, position: Int) {

    }
}