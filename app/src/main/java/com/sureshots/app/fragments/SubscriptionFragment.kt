package com.sureshots.app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

import com.sureshots.app.R
import com.sureshots.app.adapter.SubscriptionAdapter
import com.sureshots.app.model.Subscription
import kotlinx.android.synthetic.main.fragment_subscription.view.*

/**
 * A simple [Fragment] subclass.
 */
class SubscriptionFragment : Fragment(R.layout.fragment_subscription),View.OnClickListener {

    private lateinit var mRecyclerViewSubscription: RecyclerView
    private lateinit var mSubscriptionAdapter: SubscriptionAdapter
    private var mSubscriptionModelList: ArrayList<Subscription> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.buttonPayNow.setOnClickListener(this)
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

        onLoadSubscriptions()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonPayNow -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_subscriptionFragment_to_paymentSuccessFragment)
            }
        }
    }

    private fun onLoadSubscriptions(){
        mSubscriptionModelList.add(Subscription("1", "- Fixed discounts for recharges as per chart\n" + " ( In chart we will give range and mention the present %ages)."))
        mSubscriptionModelList.add(Subscription("2", "- No cashbacks only Discounts."))
        mSubscriptionModelList.add(Subscription("3", "- No validity period."))
        mSubscriptionModelList.add(Subscription("4", "- You will automatically subscribed for discounts of future services in this app."))
        mSubscriptionModelList.add(Subscription("5", "- Also you will be eligible for referral eamings. (As referral cash in wallet)"))
    }

}
