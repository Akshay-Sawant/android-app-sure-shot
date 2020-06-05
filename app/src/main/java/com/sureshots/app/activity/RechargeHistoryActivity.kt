package com.sureshots.app.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.sureshots.app.R
import com.sureshots.app.adapter.RechargeHistoryAdapter
import com.sureshots.app.model.RechargeHistoryModel

class RechargeHistoryActivity : AppCompatActivity() {

    private lateinit var mRecyclerViewRechargeHistory: RecyclerView
    private lateinit var mRechargeHistoryAdapter: RechargeHistoryAdapter
    private var mRechargeHistoryModelList: ArrayList<RechargeHistoryModel> = ArrayList()

    companion object {
        private lateinit var mIntent: Intent
        fun newIntent(context: Context): Intent {
            mIntent = Intent(context, RechargeHistoryActivity::class.java)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge_history)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mRecyclerViewRechargeHistory = findViewById(R.id.recyclerViewRechargeHistory)
        mRechargeHistoryModelList.clear()
        mRechargeHistoryAdapter = this.let {
            RechargeHistoryAdapter(
                it,
                R.layout.recycler_view_recharge_history,
                mRechargeHistoryModelList
            )
        }
        mRecyclerViewRechargeHistory.adapter = mRechargeHistoryAdapter
        mRechargeHistoryAdapter.notifyDataSetChanged()

        onLoadRechargeHistory()
    }

    private fun onLoadRechargeHistory() {
        mRechargeHistoryModelList.add(
            RechargeHistoryModel(
                "1",
                "9876543210",
                "₹100",
                "04/06/2020",
                "UPI",
                "INV0007ML5SOB")
        )
        mRechargeHistoryModelList.add(
            RechargeHistoryModel(
                "1",
                "9876543210",
                "₹100",
                "04/06/2020",
                "UPI",
                "INV0007ML5SOB")
        )
        mRechargeHistoryModelList.add(
            RechargeHistoryModel(
                "1",
                "9876543210",
                "₹100",
                "04/06/2020",
                "UPI",
                "INV0007ML5SOB")
        )
        mRechargeHistoryModelList.add(
            RechargeHistoryModel(
                "1",
                "9876543210",
                "₹100",
                "04/06/2020",
                "UPI",
                "INV0007ML5SOB")
        )
        mRechargeHistoryModelList.add(
            RechargeHistoryModel(
                "1",
                "9876543210",
                "₹100",
                "04/06/2020",
                "UPI",
                "INV0007ML5SOB")
        )
        mRechargeHistoryModelList.add(
            RechargeHistoryModel(
                "1",
                "9876543210",
                "₹100",
                "04/06/2020",
                "UPI",
                "INV0007ML5SOB")
        )
        mRechargeHistoryModelList.add(
            RechargeHistoryModel(
                "1",
                "9876543210",
                "₹100",
                "04/06/2020",
                "UPI",
                "INV0007ML5SOB")
        )
        mRechargeHistoryModelList.add(
            RechargeHistoryModel(
                "1",
                "9876543210",
                "₹100",
                "04/06/2020",
                "UPI",
                "INV0007ML5SOB")
        )
        mRechargeHistoryModelList.add(
            RechargeHistoryModel(
                "1",
                "9876543210",
                "₹100",
                "04/06/2020",
                "UPI",
                "INV0007ML5SOB")
        )
        mRechargeHistoryModelList.add(
            RechargeHistoryModel(
                "1",
                "9876543210",
                "₹100",
                "04/06/2020",
                "UPI",
                "INV0007ML5SOB")
        )
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
}
