package com.sureshotdiscount.app.ui.referralslist

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
import com.sureshotdiscount.app.utils.others.ValidationUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LevelsDetailsFragment : Fragment(R.layout.fragment_levels_details) {

    private lateinit var mTextViewLevelsDetailsUnsubscribed: TextView
    private lateinit var mTextViewLevelsDetailsSubscribed: TextView

    private lateinit var mTextViewLevelsDetailsNoDataFound: TextView
    private lateinit var mRecyclerViewLevelsDetails: RecyclerView

    private lateinit var mLevelsDetailsAdapter: LevelsDetailsAdapter
    private var mLevelsDetailsModel: ArrayList<LevelsDetailsModel> = ArrayList()

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewLevelsDetailsUnsubscribed =
            view.findViewById(R.id.textViewLevelsDetailsUnsubscribed)
        mTextViewLevelsDetailsSubscribed = view.findViewById(R.id.textViewLevelsDetailsSubscribed)

        mTextViewLevelsDetailsNoDataFound = view.findViewById(R.id.textViewLevelsDetailsNoDataFound)
        mRecyclerViewLevelsDetails = view.findViewById(R.id.recyclerViewLevelsDetails)

        context?.let { mSharedPreferenceUtils = SharedPreferenceUtils(it) }
    }

    override fun onResume() {
        super.onResume()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
    }

    private fun onLoadLevelsDetails() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .levelsDetails(
                            mSharedPreferenceUtils.getLoggedInUser().loginToken,
                            ""
                        ).enqueue(object : Callback<LevelsModel> {
                            override fun onResponse(
                                call: Call<LevelsModel>,
                                response: Response<LevelsModel>
                            ) {
                                if (response.isSuccessful) {
                                    val mLevelsModel: LevelsModel? =
                                        response.body()

                                    if (mLevelsModel != null) {
                                        mTextViewLevelsDetailsUnsubscribed.text =
                                            mLevelsModel.mUnsubscribed
                                        mTextViewLevelsDetailsSubscribed.text =
                                            mLevelsModel.mSubscribed

                                        if (mLevelsModel.mLevelsDetails.isNullOrEmpty()) {
                                            mTextViewLevelsDetailsNoDataFound.visibility =
                                                View.VISIBLE
                                            mRecyclerViewLevelsDetails.visibility = View.GONE
                                        } else {
                                            mTextViewLevelsDetailsNoDataFound.visibility =
                                                View.GONE
                                            mRecyclerViewLevelsDetails.visibility = View.VISIBLE

                                            mLevelsDetailsModel =
                                                mLevelsModel.mLevelsDetails as ArrayList<LevelsDetailsModel>

                                            mLevelsDetailsAdapter = context?.let {
                                                LevelsDetailsAdapter(
                                                    R.layout.rv_levels_details,
                                                    mLevelsDetailsModel
                                                )
                                            }!!
                                            mRecyclerViewLevelsDetails.adapter =
                                                mLevelsDetailsAdapter
                                            mLevelsDetailsAdapter.notifyDataSetChanged()
                                        }
                                    } else {

                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<LevelsModel>,
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