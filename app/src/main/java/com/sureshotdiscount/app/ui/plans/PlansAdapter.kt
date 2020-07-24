package com.sureshotdiscount.app.ui.plans

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sureshotdiscount.app.ui.rechargeHistory.IPlans
import kotlinx.android.synthetic.main.rv_plans.view.*

class PlansAdapter(
    private val mItemLayout: Int,
    private val mPlansModelList: List<PlansModel>,
    private val mIPlans: IPlans
) :
    RecyclerView.Adapter<PlansAdapter.PlansViewHolder>() {

    inner class PlansViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView),
        View.OnClickListener {
        init {
            mItemView.setOnClickListener(this@PlansViewHolder)
        }

        override fun onClick(v: View?) {
            mIPlans.onClickPlans(mPlansModelList[adapterPosition])
        }
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
        holder.itemView.textViewPlansAmount.text = mPlansModelList[position].mAmount
        holder.itemView.textViewPlansOperatorName.text = mPlansModelList[position].mOperatorName
        holder.itemView.textViewPlansTalktime.text = mPlansModelList[position].mTalktime
        holder.itemView.textViewPlansValidity.text = mPlansModelList[position].mValidity
        holder.itemView.textViewPlansDescription.text = mPlansModelList[position].mDescription
    }
}