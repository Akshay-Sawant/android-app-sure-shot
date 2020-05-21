package com.sureshots.app.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.navigation.Navigation
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.VerticalStepView

import com.sureshots.app.R
import kotlinx.android.synthetic.main.fragment_refer_earn.view.*

/**
 * A simple [Fragment] subclass.
 */
class ReferEarnFragment : Fragment(R.layout.fragment_refer_earn),View.OnClickListener {

    private lateinit var verticalStepView : VerticalStepView
    private var mStepList: ArrayList<String> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.buttonShareNow.setOnClickListener(this)
        verticalStepView = view.findViewById(R.id.stepViewReferEarn)
        mStepList.clear()
        mStepList.add("Get Rs.2 in winnings for every new signup  from your referral link")
        mStepList.add("Get Rs.3 in winnings after your friend makes his first deposit  of Rs.10 or more")
        mStepList.add("A maximum of 25 referrals are allowed")

        verticalStepView.setStepsViewIndicatorComplectingPosition(mStepList.size)
            .reverseDraw(false)
            .setStepViewTexts(mStepList)
            .setLinePaddingProportion(1.5f)
            .setStepsViewIndicatorCompletedLineColor(R.color.colorAccent)
            .setStepViewComplectedTextColor(R.color.colorAccent)
            .setStepViewUnComplectedTextColor(R.color.colorAccent)
            .setStepsViewIndicatorUnCompletedLineColor(R.color.colorAccent)
            .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(requireActivity(),R.drawable.ic_step_icon_24dp))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonShareNow -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_referEarn_to_rechargeFragment)
            }
        }
    }

}
