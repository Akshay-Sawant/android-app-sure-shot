package com.sureshots.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sureshots.app.R
import com.sureshots.app.model.RechargeHistoryModel

class RechargeHistoryAdapter(private var mContext: Context,
                             private var mItemLayout: Int,
                             private var mList : List<RechargeHistoryModel>)
    : RecyclerView.Adapter<RechargeHistoryAdapter.ViewHolder>() {


    /*interface OnItemSelectedListener {
        fun onItemSelected(bestCampSites: BestCampSites, adapterPosition: Int)
    }*/
    inner class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val textViewMobileNumber : TextView = itemView.findViewById(R.id.textViewMobileNumber)
        val textViewRechargeAmount : TextView = itemView.findViewById(R.id.textViewRechargeAmount)
        val textViewRechargeDate : TextView = itemView.findViewById(R.id.textViewRechargeDate)
        val textViewPaymentMode : TextView = itemView.findViewById(R.id.textViewPaymentMode)
        val textViewReferenceNumber : TextView = itemView.findViewById(R.id.textViewReferenceNumber)


        init {
            //itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            //onItemSelectedListener.onItemSelected(mList[adapterPosition], adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext)
                .inflate(
                    mItemLayout,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.textViewMobileNumber.text = mList[position].mobileNumber
        holder.textViewRechargeAmount.text = mList[position].rechargeAmount
        holder.textViewRechargeDate.text = mList[position].rechargeDate
        holder.textViewPaymentMode.text = mList[position].paymentMode
        holder.textViewReferenceNumber.text = mList[position].referenceNumber
    }

    override fun getItemCount(): Int = mList.size
}