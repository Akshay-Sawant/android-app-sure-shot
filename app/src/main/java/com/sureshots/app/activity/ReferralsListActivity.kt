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
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 1","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 2","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 3","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 4","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 5","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 6","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 7","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 8","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 9","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 10","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 11","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 12","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 13","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 14","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 15","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 16","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 17","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 18","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 19","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 20","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 21","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 22","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 23","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 24","Rs. 0"))
        mReferralsListModelList.add(ReferralsModel("1","LEVEL 25","Rs. 0"))
    }
}