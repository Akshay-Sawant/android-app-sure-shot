package com.sureshotdiscount.app.ui.subscriptionplan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.sureshotdiscount.app.R

class SubscriptionPlanFragment : Fragment(R.layout.fragment_subscription_plan),
    View.OnClickListener {

    private lateinit var mTextViewSubscriptionPlanNoDataFound: TextView

    private lateinit var mMaterialCardViewSubscriptionPlan: MaterialCardView
    private lateinit var mTextViewSubscriptionPlanAmount: TextView
    private lateinit var mTextViewSubscriptionPlanExpiryDate: TextView

    private lateinit var mButtonSubscriptionPlanRenew: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewSubscriptionPlanNoDataFound =
            view.findViewById(R.id.textViewSubscriptionPlanNoDataFound)

        mMaterialCardViewSubscriptionPlan = view.findViewById(R.id.materialCardViewSubscriptionPlan)
        mTextViewSubscriptionPlanAmount = view.findViewById(R.id.textViewSubscriptionPlanAmount)
        mTextViewSubscriptionPlanExpiryDate =
            view.findViewById(R.id.textViewSubscriptionPlanExpiryDate)

        mButtonSubscriptionPlanRenew = view.findViewById(R.id.buttonSubscriptionPlanRenew)
        mButtonSubscriptionPlanRenew.setOnClickListener(this@SubscriptionPlanFragment)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonSubscriptionPlanRenew -> {

            }
        }
    }
}