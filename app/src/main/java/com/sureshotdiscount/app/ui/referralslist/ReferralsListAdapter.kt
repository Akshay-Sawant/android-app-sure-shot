package com.sureshotdiscount.app.ui.referralslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.sureshotdiscount.app.R
import kotlinx.android.synthetic.main.rv_referrals_list.view.*

class ReferralsListAdapter(
    private val mItemLayout: Int,
    private val mReferralsModelList: List<ReferralsModel>,
    private val mIReferralsListListener: IReferralsListListener
) : RecyclerView.Adapter<ReferralsListAdapter.ReferralsListViewHolder>() {

    inner class ReferralsListViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView),
        View.OnClickListener {
        val mMaterialCardViewReferralsListLevel =
            mItemView.findViewById<MaterialCardView>(R.id.materialCardViewReferralsListLevel)

        init {
            mMaterialCardViewReferralsListLevel.setOnClickListener(this@ReferralsListViewHolder)
        }

        override fun onClick(v: View?) {
            v?.let {
                mIReferralsListListener.onClickReferralsListsLevels(
                    it,
                    mReferralsModelList[adapterPosition]
                )
            }
        }
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
        return mReferralsModelList.size
    }

    override fun onBindViewHolder(holder: ReferralsListViewHolder, position: Int) {
        holder.itemView.textViewReferralsListLevel.text =
            mReferralsModelList[position].mReferralLevel
        holder.itemView.textViewReferralsListAmount.text = holder.itemView.context.getString(
            R.string.text_label_rupees,
            mReferralsModelList[position].mReferralAmount
        )
    }
}