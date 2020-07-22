package com.sureshotdiscount.app.ui.benefitsofsubscription

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
class BenefitsOfSubscriptionFragment : Fragment(R.layout.fragment_benefits_of_subscription), View.OnClickListener {

    private lateinit var mRecyclerViewSubscription: RecyclerView
    private lateinit var mButtonSubscriptionPayNow: Button

    private lateinit var mBenefitsOfSubscriptionAdapter: BenefitsOfSubscriptionAdapter
    private var mSubscriptionModelList: ArrayList<Subscription> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerViewSubscription = view.findViewById(R.id.recyclerViewSubscription)
        mSubscriptionModelList.clear()
        mBenefitsOfSubscriptionAdapter = context?.let {
            BenefitsOfSubscriptionAdapter(
                it,
                R.layout.rv_benefits_of_subscription,
                mSubscriptionModelList
            )
        }!!
        mRecyclerViewSubscription.adapter = mBenefitsOfSubscriptionAdapter
        mBenefitsOfSubscriptionAdapter.notifyDataSetChanged()

        mButtonSubscriptionPayNow = view.findViewById(R.id.buttonSubscriptionPayNow)
        mButtonSubscriptionPayNow.setOnClickListener(this)

        onLoadSubscriptions()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonSubscriptionPayNow -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_benefitsOfSubscription_to_paymentSuccessful)
            }
        }
    }

    private fun onLoadSubscriptions() {
        mSubscriptionModelList.add(
            Subscription(
                "1",
                getString(R.string.subscription_one)
            )
        )
        mSubscriptionModelList.add(
            Subscription(
                "2",
                getString(R.string.subscription_two)
            )
        )
        mSubscriptionModelList.add(
            Subscription(
                "3",
                getString(R.string.subscription_three)
            )
        )
        mSubscriptionModelList.add(
            Subscription(
                "4",
                getString(R.string.subscription_four)
            )
        )
        mSubscriptionModelList.add(
            Subscription(
                "5",
                getString(R.string.subscription_five)
            )
        )
    }
}