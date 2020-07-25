package com.sureshotdiscount.app.ui.referralslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReferralsListFragment : Fragment(R.layout.fragment_referrals_list), View.OnClickListener {

    private lateinit var mTextViewReferralsListBalanceEarnings: TextView
    private lateinit var mButtonReferralsListWithdraw: Button
    private lateinit var mTextViewReferralsListNoDataFound: TextView
    private lateinit var mRecyclerViewReferralsList: RecyclerView

    private lateinit var mReferralsListAdapter: ReferralsListAdapter
    private var mLevelsDetailsModelList: ArrayList<LevelsDetailsModel> = ArrayList()

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewReferralsListBalanceEarnings =
            view.findViewById(R.id.textViewReferralsListBalanceEarnings)
        mButtonReferralsListWithdraw = view.findViewById(R.id.buttonReferralsListWithdraw)
        mButtonReferralsListWithdraw.setOnClickListener(this@ReferralsListFragment)

        mTextViewReferralsListNoDataFound = view.findViewById(R.id.textViewReferralsListNoDataFound)

        mRecyclerViewReferralsList = view.findViewById(R.id.recyclerViewReferralsList)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
    }

    override fun onResume() {
        super.onResume()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonReferralsListWithdraw -> {

            }
        }
    }

    private fun onLoadReferralsList() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .referralsList(
                            mSharedPreferenceUtils.getLoggedInUser().loginToken
                        ).enqueue(object : Callback<ReferralsListModel> {
                            override fun onResponse(
                                call: Call<ReferralsListModel>,
                                response: Response<ReferralsListModel>
                            ) {
                                if (response.isSuccessful) {
                                    val mReferralsListModel: ReferralsListModel? =
                                        response.body()

                                    if (mReferralsListModel != null) {
                                        mTextViewReferralsListBalanceEarnings.text =
                                            mReferralsListModel.mBalanceEarnings

                                        if (mReferralsListModel.mReferrals.isNullOrEmpty()) {
                                            mTextViewReferralsListNoDataFound.visibility =
                                                View.VISIBLE
                                            mRecyclerViewReferralsList.visibility = View.GONE
                                        } else {
                                            mTextViewReferralsListNoDataFound.visibility =
                                                View.GONE
                                            mRecyclerViewReferralsList.visibility = View.VISIBLE

                                            mLevelsDetailsModelList =
                                                mReferralsListModel.mReferrals as ArrayList<LevelsDetailsModel>

                                            mReferralsListAdapter = context?.let {
                                                ReferralsListAdapter(
                                                    R.layout.rv_referrals_list,
                                                    mLevelsDetailsModelList
                                                )
                                            }!!
                                            mRecyclerViewReferralsList.adapter =
                                                mReferralsListAdapter
                                            mReferralsListAdapter.notifyDataSetChanged()
                                        }
                                    } else {

                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<ReferralsListModel>,
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