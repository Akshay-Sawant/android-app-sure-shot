package com.sureshotdiscount.app.ui.rechargeHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_recharge_history.view.*

class RechargeHistoryAdapter(
    private val mItemLayout: Int,
    private val mRechargeHistoryModelList: List<RechargeHistoryModel>
) :
    RecyclerView.Adapter<RechargeHistoryAdapter.RechargeHistoryViewHolder>() {

    inner class RechargeHistoryViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RechargeHistoryViewHolder {
        return RechargeHistoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    mItemLayout,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return mRechargeHistoryModelList.size
    }

    override fun onBindViewHolder(holder: RechargeHistoryViewHolder, position: Int) {
        holder.itemView.textViewRechargeHistoryMobileNumber.text =
            mRechargeHistoryModelList[position].mMobileNumber
        holder.itemView.textViewRechargeHistoryRechargeDate.text =
            mRechargeHistoryModelList[position].mRechargeDate
        holder.itemView.textViewRechargeHistoryRechargeAmount.text =
            mRechargeHistoryModelList[position].mRechargeAmount
        holder.itemView.textViewRechargeHistoryPaymentMode.text =
            mRechargeHistoryModelList[position].mPaymentMode
        holder.itemView.textViewRechargeHistoryReferenceNumber.text =
            mRechargeHistoryModelList[position].mReferenceNumber
    }
}