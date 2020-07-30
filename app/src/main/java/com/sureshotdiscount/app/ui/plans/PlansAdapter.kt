package com.sureshotdiscount.app.ui.plans

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.ui.rechargeHistory.IPlans
import kotlinx.android.synthetic.main.rv_plans.view.*

class PlansAdapter(
    private val mItemLayout: Int,
    private val mPlansListModelList: List<PlansListModel>,
    private val mIPlans: IPlans
) :
    RecyclerView.Adapter<PlansAdapter.PlansViewHolder>() {

    inner class PlansViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView),
        View.OnClickListener {
        init {
            mItemView.setOnClickListener(this@PlansViewHolder)
        }

        override fun onClick(v: View?) {
            mIPlans.onClickPlans(mPlansListModelList[adapterPosition])
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
        return mPlansListModelList.size
    }

    override fun onBindViewHolder(holder: PlansViewHolder, position: Int) {
        holder.itemView.textViewPlansAmount.text = holder.itemView.context.getString(
            R.string.text_label_rupees,
            mPlansListModelList[position].mAmount
        )
        holder.itemView.textViewPlansOperatorName.text = mPlansListModelList[position].mOperatorName
        holder.itemView.textViewPlansTalktime.text = mPlansListModelList[position].mTalktime
        holder.itemView.textViewPlansValidity.text = mPlansListModelList[position].mValidity
        holder.itemView.textViewPlansDescription.text = mPlansListModelList[position].mDescription
    }
}