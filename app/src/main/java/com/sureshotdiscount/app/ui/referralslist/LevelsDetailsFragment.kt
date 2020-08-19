package com.sureshotdiscount.app.ui.referralslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
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

    private lateinit var mMaterialCardViewLevelsDetails: MaterialCardView

    private lateinit var mTextViewLevelsDetailsUnsubscribed: TextView
    private lateinit var mTextViewLevelsDetailsSubscribed: TextView

    private lateinit var mTextViewLevelsDetailsNoDataFound: TextView
    private lateinit var mRecyclerViewLevelsDetails: RecyclerView

    private lateinit var mContentLoadingProgressBarLevelsDetails: ContentLoadingProgressBar

    private lateinit var mLevelsDetailsAdapter: LevelsDetailsAdapter
    private var mLevelsDetailsModel: ArrayList<LevelsDetailsModel> = ArrayList()

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    private var mLevelId: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMaterialCardViewLevelsDetails = view.findViewById(R.id.materialCardViewLevelsDetails)

        mTextViewLevelsDetailsUnsubscribed =
            view.findViewById(R.id.textViewLevelsDetailsUnsubscribed)
        mTextViewLevelsDetailsSubscribed = view.findViewById(R.id.textViewLevelsDetailsSubscribed)

        mTextViewLevelsDetailsNoDataFound = view.findViewById(R.id.textViewLevelsDetailsNoDataFound)
        mRecyclerViewLevelsDetails = view.findViewById(R.id.recyclerViewLevelsDetails)

        mContentLoadingProgressBarLevelsDetails =
            view.findViewById(R.id.contentLoadingProgressBarLevelsDetails)

        context?.let { mSharedPreferenceUtils = SharedPreferenceUtils(it) }
    }

    override fun onResume() {
        super.onResume()
        mContentLoadingProgressBarLevelsDetails.show()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
        onValidateArguments()
        onLoadLevelsDetails()
    }

    private fun onValidateArguments() {
        val mLevelsDetailsFragmentArgs: LevelsDetailsFragmentArgs by navArgs()
        mLevelId = mLevelsDetailsFragmentArgs.levelId
    }

    private fun onLoadLevelsDetails() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    mLevelId?.let { it1 ->
                        APIClient.apiInterface
                            .levelsDetails(
                                mSharedPreferenceUtils.getLoggedInUser().loginToken,
                                it1
                            ).enqueue(object : Callback<LevelsModel> {
                                override fun onResponse(
                                    call: Call<LevelsModel>,
                                    response: Response<LevelsModel>
                                ) {
                                    if (response.isSuccessful) {
                                        val mLevelsModel: LevelsModel? =
                                            response.body()
                                        mContentLoadingProgressBarLevelsDetails.hide()

                                        if (mLevelsModel != null) {
                                            mMaterialCardViewLevelsDetails.visibility = View.VISIBLE
                                            mTextViewLevelsDetailsUnsubscribed.text =
                                                getString(
                                                    R.string.text_label_unsubscribed,
                                                    mLevelsModel.mUnsubscribed.toString()
                                                )
                                            mTextViewLevelsDetailsSubscribed.text =
                                                getString(
                                                    R.string.text_label_subscribed,
                                                    mLevelsModel.mSubscribed.toString()
                                                )

                                            if (mLevelsModel.mResponse.isNullOrEmpty()) {
                                                mTextViewLevelsDetailsNoDataFound.visibility =
                                                    View.VISIBLE
                                                mRecyclerViewLevelsDetails.visibility = View.GONE
                                            } else {
                                                mTextViewLevelsDetailsNoDataFound.visibility =
                                                    View.GONE
                                                mRecyclerViewLevelsDetails.visibility = View.VISIBLE

                                                mLevelsDetailsModel =
                                                    mLevelsModel.mResponse as ArrayList<LevelsDetailsModel>

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
                                            mMaterialCardViewLevelsDetails.visibility = View.VISIBLE
                                            mTextViewLevelsDetailsUnsubscribed.text =
                                                getString(
                                                    R.string.text_label_unsubscribed,
                                                    mLevelsModel?.mUnsubscribed.toString()
                                                )
                                            mTextViewLevelsDetailsSubscribed.text =
                                                getString(
                                                    R.string.text_label_subscribed,
                                                    mLevelsModel?.mSubscribed.toString()
                                                )
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
                                    mContentLoadingProgressBarLevelsDetails.hide()
                                }
                            })
                    }
                }
                else -> {
                    mContentLoadingProgressBarLevelsDetails.hide()
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}