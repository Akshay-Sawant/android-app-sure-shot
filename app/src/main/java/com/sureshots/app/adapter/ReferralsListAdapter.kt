package com.sureshots.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sureshots.app.R
import com.sureshots.app.model.RechargeHistoryModel
import com.sureshots.app.model.ReferralsModel

class ReferralsListAdapter(private var mContext: Context,
                           private var mItemLayout: Int,
                           private var mList : List<ReferralsModel>)
    : RecyclerView.Adapter<ReferralsListAdapter.ViewHolder>() {


    /*interface OnItemSelectedListener {
        fun onItemSelected(bestCampSites: BestCampSites, adapterPosition: Int)
    }*/
    inner class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val textViewMobileNumber : TextView = itemView.findViewById(R.id.textViewMobileNumber)
        val textViewSubscription : TextView = itemView.findViewById(R.id.textViewSubscription)


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
        holder.textViewSubscription.text = mList[position].subscriptionStatus
    }

    override fun getItemCount(): Int = mList.size
}