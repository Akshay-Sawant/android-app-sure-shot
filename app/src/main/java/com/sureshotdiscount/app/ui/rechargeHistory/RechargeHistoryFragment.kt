package com.sureshotdiscount.app.ui.rechargeHistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RechargeHistoryFragment : Fragment(R.layout.fragment_recharge_history) {

    private lateinit var mTextViewRechargeHistoryNoDataFound: TextView
    private lateinit var mRecyclerViewRechargeHistory: RecyclerView
    private lateinit var mRechargeHistoryAdapter: RechargeHistoryAdapter
    private var mRechargeHistoryDetailsModelList: ArrayList<RechargeHistoryDetailsModel> =
        ArrayList()

    private lateinit var mContentLoadingProgressBarRechargeHistory: ContentLoadingProgressBar

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewRechargeHistoryNoDataFound =
            view.findViewById(R.id.textViewRechargeHistoryNoDataFound)
        mRecyclerViewRechargeHistory = view.findViewById(R.id.recyclerViewRechargeHistory)

        mContentLoadingProgressBarRechargeHistory =
            view.findViewById(R.id.contentLoadingProgressBarRechargeHistory)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
    }

    override fun onResume() {
        super.onResume()
        mContentLoadingProgressBarRechargeHistory.show()
        onLoadRechargeHistory()
    }

    private fun onLoadRechargeHistory() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .getRechargeHistory(
                            mSharedPreferenceUtils.getLoggedInUser().loginToken
                        ).enqueue(object : Callback<RechargeHistoryModel> {
                            override fun onResponse(
                                call: Call<RechargeHistoryModel>,
                                response: Response<RechargeHistoryModel>
                            ) {
                                if (response.isSuccessful) {
                                    val mRechargeHistoryModel: RechargeHistoryModel? =
                                        response.body()
                                    mContentLoadingProgressBarRechargeHistory.hide()

                                    if (mRechargeHistoryModel != null) {
                                        if (mRechargeHistoryModel.mStatus) {
                                            mTextViewRechargeHistoryNoDataFound.visibility =
                                                View.GONE
                                            mRecyclerViewRechargeHistory.visibility = View.VISIBLE

                                            mRechargeHistoryDetailsModelList.clear()
                                            mRechargeHistoryDetailsModelList =
                                                mRechargeHistoryModel.mResponse as ArrayList<RechargeHistoryDetailsModel>


                                            mRechargeHistoryAdapter = context?.let {
                                                RechargeHistoryAdapter(
                                                    R.layout.rv_recharge_history,
                                                    mRechargeHistoryDetailsModelList
                                                )
                                            }!!
                                            mRecyclerViewRechargeHistory.adapter =
                                                mRechargeHistoryAdapter
                                            mRechargeHistoryAdapter.notifyDataSetChanged()
                                        } else {
                                            mTextViewRechargeHistoryNoDataFound.visibility =
                                                View.VISIBLE
                                            mRecyclerViewRechargeHistory.visibility = View.GONE
                                        }
                                    } else {
                                        ErrorUtils.logNetworkError(
                                            ServerInvalidResponseException.ERROR_200_BLANK_RESPONSE +
                                                    "\nResponse: " + response.toString(),
                                            null
                                        )
                                        AlertDialogUtils.getInstance()
                                            .displayInvalidResponseAlert(it)
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<RechargeHistoryModel>,
                                t: Throwable
                            ) {
                                ErrorUtils.parseOnFailureException(
                                    it,
                                    call,
                                    t
                                )
                                mContentLoadingProgressBarRechargeHistory.hide()
                            }
                        })
                }
                else -> {
                    mContentLoadingProgressBarRechargeHistory.hide()
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}