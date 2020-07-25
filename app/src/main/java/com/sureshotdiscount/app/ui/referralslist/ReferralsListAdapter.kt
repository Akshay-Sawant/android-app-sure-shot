package com.sureshotdiscount.app.ui.referralslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_referrals_list.view.*

class ReferralsListAdapter(
    private val mItemLayout: Int,
    private val mLevelsDetailsModelList: List<LevelsDetailsModel>
) : RecyclerView.Adapter<ReferralsListAdapter.ReferralsListViewHolder>() {

    inner class ReferralsListViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferralsListViewHolder {
        return ReferralsListViewHolder(
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

    override fun onBindViewHolder(holder: ReferralsListViewHolder, position: Int) {
        holder.itemView.textViewReferralsListLevel.text =
            mLevelsDetailsModelList[position].mReferralLevel
        holder.itemView.textViewReferralsListAmount.text =
            mLevelsDetailsModelList[position].mReferralAmount
    }
}