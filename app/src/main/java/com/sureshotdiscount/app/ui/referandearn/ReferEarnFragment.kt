package com.sureshotdiscount.app.ui.referandearn

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.baoyachi.stepview.VerticalStepView
import com.sureshotdiscount.app.R


class ReferEarnFragment : Fragment(R.layout.fragment_refer_earn), View.OnClickListener {

    private lateinit var verticalStepView: VerticalStepView
    private var mStepList: ArrayList<String> = ArrayList()

    private lateinit var mTextViewReferAndEarnReferralCode: TextView
    private lateinit var mTextViewReferAndEarnTapToCopy: TextView
    private lateinit var mButtonReferAndEarnShareNow: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            .setStepsViewIndicatorCompleteIcon(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_step_icon_24dp
                )
            )

        mTextViewReferAndEarnReferralCode = view.findViewById(R.id.textViewReferralCode)
        mTextViewReferAndEarnTapToCopy = view.findViewById(R.id.textViewTapToCopy)
        mTextViewReferAndEarnTapToCopy.setOnClickListener(this@ReferEarnFragment)

        mButtonReferAndEarnShareNow = view.findViewById(R.id.buttonShareNow)
        mButtonReferAndEarnShareNow.setOnClickListener(this@ReferEarnFragment)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textViewTapToCopy -> {
//                onClickTapToCopy()
            }
            R.id.buttonShareNow -> {
//                onClickShareNow()
            }
        }
    }

    private fun onClickTapToCopy() {
        val clipboard: ClipboardManager? =
            context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        val clip =
            ClipData.newPlainText(getString(R.string.text_your_referral_code), "Text to copy")
        clipboard?.setPrimaryClip(clip)
        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
    }

    private fun onClickShareNow() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Sure Shot Discounts")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share Via")
        startActivity(shareIntent)
    }
}