package com.sureshotdiscount.app.ui.subscription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.model.Subscription

/**
 * A simple [Fragment] subclass.
 */
class SubscriptionFragment : Fragment(R.layout.fragment_subscription), View.OnClickListener {

    private lateinit var mRecyclerViewSubscription: RecyclerView
    private lateinit var mButtonSubscriptionPayNow: Button

    private lateinit var mSubscriptionAdapter: SubscriptionAdapter
    private var mSubscriptionModelList: ArrayList<Subscription> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerViewSubscription = view.findViewById(R.id.recyclerViewSubscription)
        mSubscriptionModelList.clear()
        mSubscriptionAdapter = context?.let {
            SubscriptionAdapter(
                it,
                R.layout.recycler_view_subscriptions,
                mSubscriptionModelList
            )
        }!!
        mRecyclerViewSubscription.adapter = mSubscriptionAdapter
        mSubscriptionAdapter.notifyDataSetChanged()

        mButtonSubscriptionPayNow = view.findViewById(R.id.buttonSubscriptionPayNow)
        mButtonSubscriptionPayNow.setOnClickListener(this)

        onLoadSubscriptions()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonSubscriptionPayNow -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_subscriptionFragment_to_paymentSuccessFragment)
            }
        }
    }

    private fun onLoadSubscriptions() {
        mSubscriptionModelList.add(
            Subscription(
                "1",
                "- Fixed discounts for recharges as per chart\n" + " ( In chart we will give range and mention the present %ages)."
            )
        )
        mSubscriptionModelList.add(
            Subscription(
                "2",
                "- No cashbacks only Discounts."
            )
        )
        mSubscriptionModelList.add(
            Subscription(
                "3",
                "- No validity period."
            )
        )
        mSubscriptionModelList.add(
            Subscription(
                "4",
                "- You will automatically subscribed for discounts of future services in this app."
            )
        )
        mSubscriptionModelList.add(
            Subscription(
                "5",
                "- Also you will be eligible for referral eamings. (As referral cash in wallet)"
            )
        )
    }
}