package com.sureshotdiscount.app.ui.dth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.sureshotdiscount.app.utils.others.MiddleDividerItemDecoration
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DTHFragment : Fragment(R.layout.fragment_d_t_h), View.OnClickListener,
    DTHAdapter.OnItemSelectedListener {

    private lateinit var mContentLoadingProgressBarDTH: ContentLoadingProgressBar

    private lateinit var mTextViewDTHNoDataFound: TextView
    private lateinit var mMaterialCardViewDTH: MaterialCardView

    private lateinit var mRecyclerViewDTH: RecyclerView
    private lateinit var mDTHAdapter: DTHAdapter
    private var mDTHListModelList: ArrayList<DTHListModel> = ArrayList()

    private lateinit var mTextViewDTHSubscriptionPlanAndBenefits: TextView
    private lateinit var mImageViewDTHSubscriptionPlan: ImageView

    private lateinit var mTextViewDTHReferAndEarn: TextView
    private lateinit var mImageViewDTHReferAndEarn: ImageView

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContentLoadingProgressBarDTH = view.findViewById(R.id.contentLoadingProgressBarDTH)

        mTextViewDTHNoDataFound = view.findViewById(R.id.textViewDTHNoDataFound)
        mMaterialCardViewDTH = view.findViewById(R.id.materialCardViewDTH)

        mRecyclerViewDTH = view.findViewById(R.id.recyclerViewDTH)

        mTextViewDTHSubscriptionPlanAndBenefits =
            view.findViewById(R.id.textViewDTHSubscriptionPlan)
        mImageViewDTHSubscriptionPlan = view.findViewById(R.id.imageViewDTHSubscriptionPlan)

        mTextViewDTHReferAndEarn = view.findViewById(R.id.textViewDTHReferEarn)
        mImageViewDTHSubscriptionPlan.setOnClickListener(this@DTHFragment)

        mImageViewDTHReferAndEarn = view.findViewById(R.id.imageViewDTHReferEarn)
        mImageViewDTHReferAndEarn.setOnClickListener(this@DTHFragment)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
    }

    override fun onResume() {
        super.onResume()
        mContentLoadingProgressBarDTH.show()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
        onLoadDTH()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageViewDTHSubscriptionPlan -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboard_to_benefitsOfSubscription)
            }
            R.id.imageViewDTHReferEarn -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboard_to_referEarn)
            }
        }
    }

    override fun onItemSelected(mView: View, mPosition: DTHListModel) {
        mSharedPreferenceUtils.saveRechargeDetails(
            mPosition.mCompanyId,
            mPosition.mCompanyCode,
            mPosition.mCompanyName,
            mPosition.mCompanyLogoUrl
        )
        view?.let {
            Navigation.findNavController(it).navigate(R.id.action_myAccount_to_recharge)
        }
    }

    private fun onLoadDTH() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .getDTHCompany(mSharedPreferenceUtils.getLoggedInUser().loginToken)
                        .enqueue(object : Callback<DTHModel> {
                            override fun onResponse(
                                call: Call<DTHModel>,
                                response: Response<DTHModel>
                            ) {
                                if (response.isSuccessful) {
                                    val mDTHModel: DTHModel? = response.body()
                                    mContentLoadingProgressBarDTH.hide()

                                    if (mDTHModel != null) {
                                        if (mDTHModel.mStatus) {
                                            mTextViewDTHNoDataFound.visibility = View.GONE
                                            mMaterialCardViewDTH.visibility = View.VISIBLE
                                            mTextViewDTHSubscriptionPlanAndBenefits.visibility =
                                                View.VISIBLE
                                            mImageViewDTHSubscriptionPlan.visibility = View.VISIBLE
                                            mTextViewDTHReferAndEarn.visibility = View.VISIBLE
                                            mImageViewDTHReferAndEarn.visibility = View.VISIBLE

                                            mDTHListModelList.clear()
                                            mDTHListModelList =
                                                mDTHModel.mResponse as ArrayList<DTHListModel>

                                            mDTHAdapter = context?.let {
                                                DTHAdapter(
                                                    it,
                                                    R.layout.rv_dth,
                                                    mDTHListModelList,
                                                    this@DTHFragment
                                                )
                                            }!!
                                            mRecyclerViewDTH.addItemDecoration(
                                                MiddleDividerItemDecoration(
                                                    requireContext(),
                                                    MiddleDividerItemDecoration.ALL
                                                )
                                            )
                                            mRecyclerViewDTH.adapter = mDTHAdapter
                                            mDTHAdapter.notifyDataSetChanged()
                                        } else {
                                            mTextViewDTHNoDataFound.visibility = View.VISIBLE
                                            mMaterialCardViewDTH.visibility = View.GONE
                                            mTextViewDTHSubscriptionPlanAndBenefits.visibility =
                                                View.GONE
                                            mImageViewDTHSubscriptionPlan.visibility = View.GONE
                                            mTextViewDTHReferAndEarn.visibility = View.GONE
                                            mImageViewDTHReferAndEarn.visibility = View.GONE
                                        }
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<DTHModel>,
                                t: Throwable
                            ) {
                                ErrorUtils.parseOnFailureException(
                                    it,
                                    call,
                                    t
                                )
                                mContentLoadingProgressBarDTH.hide()
                            }
                        })
                }
                else -> {
                    mContentLoadingProgressBarDTH.hide()
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}