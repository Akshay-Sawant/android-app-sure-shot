package com.sureshotdiscount.app.ui.rechargeHistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RechargeHistoryFragment : Fragment(R.layout.fragment_recharge_history) {

    private lateinit var mTextViewRechargeHistoryNoDataFound: TextView
    private lateinit var mRecyclerViewRechargeHistory: RecyclerView
    private lateinit var mRechargeHistoryAdapter: RechargeHistoryAdapter
    private var mRechargeHistoryModelList: ArrayList<RechargeHistoryModel> = ArrayList()

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewRechargeHistoryNoDataFound =
            view.findViewById(R.id.textViewRechargeHistoryNoDataFound)
        mRecyclerViewRechargeHistory = view.findViewById(R.id.recyclerViewRechargeHistory)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }

//        onLoadRechargeHistory()
    }

    private fun onLoadRechargeHistory() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .getRechargeHistory(
                            mSharedPreferenceUtils.getLoggedInUser().loginToken
                        ).enqueue(object : Callback<List<RechargeHistoryModel>> {
                            override fun onResponse(
                                call: Call<List<RechargeHistoryModel>>,
                                response: Response<List<RechargeHistoryModel>>
                            ) {
                                if (response.isSuccessful) {
                                    val mRechargeHistoryModel: List<RechargeHistoryModel>? =
                                        response.body()

                                    if (mRechargeHistoryModel.isNullOrEmpty()) {
                                        mTextViewRechargeHistoryNoDataFound.visibility =
                                            View.VISIBLE
                                        mRecyclerViewRechargeHistory.visibility = View.GONE
                                    } else {
                                        mTextViewRechargeHistoryNoDataFound.visibility = View.GONE
                                        mRecyclerViewRechargeHistory.visibility = View.VISIBLE

                                        mRechargeHistoryModelList =
                                            mRechargeHistoryModel as ArrayList<RechargeHistoryModel>

                                        mRechargeHistoryModelList.clear()
                                        mRechargeHistoryAdapter = context?.let {
                                            RechargeHistoryAdapter(
                                                R.layout.rv_recharge_history,
                                                mRechargeHistoryModelList
                                            )
                                        }!!
                                        mRecyclerViewRechargeHistory.adapter =
                                            mRechargeHistoryAdapter
                                        mRechargeHistoryAdapter.notifyDataSetChanged()
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<List<RechargeHistoryModel>>,
                                t: Throwable
                            ) {
                                ErrorUtils.parseOnFailureException(
                                    it,
                                    call,
                                    t
                                )
                            }
                        })
                }
                else -> {
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}