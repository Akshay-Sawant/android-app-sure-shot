package com.sureshotdiscount.app.ui.rechargeHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sureshotdiscount.app.R
import kotlinx.android.synthetic.main.rv_recharge_history.view.*

class RechargeHistoryAdapter(
    private val mItemLayout: Int,
    private val mRechargeHistoryDetailsModelList: List<RechargeHistoryDetailsModel>
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
        return mRechargeHistoryDetailsModelList.size
    }

    override fun onBindViewHolder(holder: RechargeHistoryViewHolder, position: Int) {
        holder.itemView.textViewRechargeHistoryMobileNumber.text =
            mRechargeHistoryDetailsModelList[position].mMobileNumber
        holder.itemView.textViewRechargeHistoryRechargeDate.text =
            mRechargeHistoryDetailsModelList[position].mRechargeDate
        holder.itemView.textViewRechargeHistoryRechargeAmount.text =
            holder.itemView.context.getString(
                R.string.text_label_rupees,
                mRechargeHistoryDetailsModelList[position].mRechargeAmount
            )
        holder.itemView.textViewRechargeHistoryPaymentMode.text =
            mRechargeHistoryDetailsModelList[position].mPaymentMode
        holder.itemView.textViewRechargeHistoryReferenceNumber.text =
            mRechargeHistoryDetailsModelList[position].mReferenceNumber
    }
}