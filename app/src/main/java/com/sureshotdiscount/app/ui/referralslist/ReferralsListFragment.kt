package com.sureshotdiscount.app.ui.referralslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReferralsListFragment : Fragment(R.layout.fragment_referrals_list), View.OnClickListener,
    IReferralsListListener {

    private lateinit var mTextViewReferralsListBalanceEarnings: TextView
    private lateinit var mButtonReferralsListWithdraw: Button
    private lateinit var mTextViewReferralsListReferrals: TextView
    private lateinit var mTextViewReferralsListNoDataFound: TextView
    private lateinit var mRecyclerViewReferralsList: RecyclerView
    private lateinit var mContentLoadingProgressBarReferralsList: ContentLoadingProgressBar

    private lateinit var mReferralsListAdapter: ReferralsListAdapter
    private var mReferralsModelList: ArrayList<ReferralsModel> = ArrayList()

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewReferralsListBalanceEarnings =
            view.findViewById(R.id.textViewReferralsListBalanceEarnings)
        mButtonReferralsListWithdraw = view.findViewById(R.id.buttonReferralsListWithdraw)
        mButtonReferralsListWithdraw.setOnClickListener(this@ReferralsListFragment)

        mTextViewReferralsListReferrals = view.findViewById(R.id.textViewReferralsListReferrals)

        mTextViewReferralsListNoDataFound = view.findViewById(R.id.textViewReferralsListNoDataFound)

        mRecyclerViewReferralsList = view.findViewById(R.id.recyclerViewReferralsList)

        mContentLoadingProgressBarReferralsList =
            view.findViewById(R.id.contentLoadingProgressBarReferralsList)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
    }

    override fun onResume() {
        super.onResume()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
        mContentLoadingProgressBarReferralsList.show()
        onLoadReferralsList()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonReferralsListWithdraw -> view?.let {
                Navigation.findNavController(it).navigate(R.id.action_referralsList_to_withdraw)
            }
        }
    }

    override fun onClickReferralsListsLevels(mView: View, mPosition: ReferralsModel) {

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
                                    mContentLoadingProgressBarReferralsList.hide()

                                    if (mReferralsListModel != null) {
                                        if (mReferralsListModel.mStatus) {
                                            mTextViewReferralsListBalanceEarnings.text = getString(
                                                R.string.text_label_rupees,
                                                mReferralsListModel.mResponse.mBalanceEarnings
                                            )

                                            if (mReferralsListModel.mResponse.mReferrals.isNullOrEmpty()) {
                                                mTextViewReferralsListNoDataFound.visibility =
                                                    View.VISIBLE
                                                mTextViewReferralsListReferrals.visibility =
                                                    View.GONE
                                                mRecyclerViewReferralsList.visibility = View.GONE
                                            } else {
                                                mTextViewReferralsListNoDataFound.visibility =
                                                    View.GONE
                                                mTextViewReferralsListReferrals.visibility =
                                                    View.VISIBLE
                                                mRecyclerViewReferralsList.visibility = View.VISIBLE

                                                mReferralsModelList =
                                                    mReferralsListModel.mResponse.mReferrals as ArrayList<ReferralsModel>

                                                mReferralsListAdapter = context?.let {
                                                    ReferralsListAdapter(
                                                        R.layout.rv_referrals_list,
                                                        mReferralsModelList,
                                                        this@ReferralsListFragment
                                                    )
                                                }!!
                                                mRecyclerViewReferralsList.adapter =
                                                    mReferralsListAdapter
                                                mReferralsListAdapter.notifyDataSetChanged()
                                            }
                                        } else {
                                            mTextViewReferralsListBalanceEarnings.text = getString(
                                                R.string.text_label_rupees,
                                                "0"
                                            )
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
                                call: Call<ReferralsListModel>,
                                t: Throwable
                            ) {
                                ErrorUtils.parseOnFailureException(
                                    it,
                                    call,
                                    t
                                )
                                mContentLoadingProgressBarReferralsList.hide()
                            }
                        })
                }
                else -> {
                    mContentLoadingProgressBarReferralsList.hide()
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}