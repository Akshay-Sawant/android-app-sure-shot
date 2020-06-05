package com.sureshots.app.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.sureshots.app.R
import com.sureshots.app.adapter.ReferralsListAdapter
import com.sureshots.app.model.ReferralsModel

class ReferralsListActivity : AppCompatActivity() {

    private lateinit var mRecyclerViewReferralsList: RecyclerView
    private lateinit var mReferralsListAdapter: ReferralsListAdapter
    private var mReferralsListModelList: ArrayList<ReferralsModel> = ArrayList()

    companion object {
        private lateinit var mIntent: Intent
        fun newIntent(context: Context): Intent {
            mIntent = Intent(context, ReferralsListActivity::class.java)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referrals_list)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mRecyclerViewReferralsList = findViewById(R.id.recyclerViewReferralsList)
        mReferralsListModelList.clear()
        mReferralsListAdapter = this.let {
            ReferralsListAdapter(
                it,
                R.layout.recycler_view_referrals_list,
                mReferralsListModelList
            )
        }
        mRecyclerViewReferralsList.adapter = mReferralsListAdapter
        mReferralsListAdapter.notifyDataSetChanged()

        onLoadReferrals()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> true
        }
    }

    private fun onLoadReferrals(){
        mReferralsListModelList.add(ReferralsModel("1","9876543210","Subscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9638527410","Subscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9873216547","Unsubscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9516238740","Subscribed"))
        mReferralsListModelList.add(ReferralsModel("1","7258410398","Subscribed"))
        mReferralsListModelList.add(ReferralsModel("1","8189784510","Subscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9898989898","Unsubscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9184623847","Unsubscribed"))
        mReferralsListModelList.add(ReferralsModel("1","8567412390","Subscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9000700088","Unsubscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9090909090","Subscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9060302010","Subscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9123456789","Unsubscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9876543210","Unsubscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9456123004","Subscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9000990009","Unsubscribed"))
        mReferralsListModelList.add(ReferralsModel("1","8568564123","Subscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9639529841","Unsubscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9876543210","Subscribed"))
        mReferralsListModelList.add(ReferralsModel("1","8459621750","Unsubscribed"))
        mReferralsListModelList.add(ReferralsModel("1","9784561230","Unsubscribed"))
    }
}
