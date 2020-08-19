package com.sureshotdiscount.app.ui.referralslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_levels_details.view.*

class LevelsDetailsAdapter(
    private val mItemLayout: Int,
    private val mLevelsDetailsModelList: List<LevelsDetailsModel>
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
        return mLevelsDetailsModelList.size
    }

    override fun onBindViewHolder(holder: LevelsDetailsViewHolder, position: Int) {
        holder.itemView.textViewLevelsDetailsContactNumber.text =
            mLevelsDetailsModelList[position].mMobileNo
        holder.itemView.textViewLevelsDetailsStatus.text = mLevelsDetailsModelList[position].mStatus
    }
}