package com.sureshots.app.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.baoyachi.stepview.VerticalStepView
import com.sureshots.app.R
import kotlinx.android.synthetic.main.activity_refer_earn.*
import java.sql.Ref

class ReferEarnActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var verticalStepView : VerticalStepView
    private var mStepList: ArrayList<String> = ArrayList()
    private var myClipboard: ClipboardManager? = null
    private lateinit var myClip: ClipData

    companion object {
        private lateinit var mIntent: Intent
        fun newIntent(context: Context): Intent {
            mIntent = Intent(context, ReferEarnActivity::class.java)
            return mIntent
        }
        fun newIntentNewTask(context: Context): Intent {
            mIntent = Intent(context, ReferEarnActivity::class.java)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refer_earn)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        textViewTaptoCopy.setOnClickListener(this)
        buttonShareNow.setOnClickListener(this)
        verticalStepView = findViewById(R.id.stepViewReferEarn)
        myClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
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
            .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this,R.drawable.ic_step_icon_24dp))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> true
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.textViewTaptoCopy ->{
                myClip = ClipData.newPlainText("text", textViewReferralCode.text)
                myClipboard?.setPrimaryClip(myClip)
                Toast.makeText(this, "Text Copied", Toast.LENGTH_SHORT).show();
            }
            R.id.buttonShareNow -> {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type="text/plain"
                //shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello do");
                startActivity(Intent.createChooser(shareIntent,"Share"))
            }
        }
    }
}
