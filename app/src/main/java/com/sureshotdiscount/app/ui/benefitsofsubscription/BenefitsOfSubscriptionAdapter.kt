package com.sureshotdiscount.app.ui.benefitsofsubscription

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sureshotdiscount.app.data.model.Subscription
import kotlinx.android.synthetic.main.rv_benefits_of_subscription.view.*

class BenefitsOfSubscriptionAdapter(
    private var mContext: Context,
    private var mItemLayout: Int,
    private var mSubscriptionList: List<Subscription>
) : RecyclerView.Adapter<BenefitsOfSubscriptionAdapter.SubscriptionViewHolder>() {

    inner class SubscriptionViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {
        return SubscriptionViewHolder(
            LayoutInflater.from(mContext)
                .inflate(
                    mItemLayout,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return mSubscriptionList.size
    }

    override fun onBindViewHolder(holder: SubscriptionViewHolder, position: Int) {
        holder.itemView.textViewSubscriptions.text = mSubscriptionList[position].subscriptionName
    }
}