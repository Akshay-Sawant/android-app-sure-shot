package com.sureshotdiscount.app.ui.mobile

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
import com.sureshotdiscount.app.ui.myaccount.MyAccountFragmentDirections
import com.sureshotdiscount.app.ui.recharge.RechargeFragmentDirections
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileRechargeFragment : Fragment(R.layout.fragment_mobile_recharge), View.OnClickListener,
    MobileRechargeAdapter.OnItemSelectedListener {

    private lateinit var mTextViewMobileRechargeNoDataFound: TextView
    private lateinit var mMaterialCardMobileRecharge: MaterialCardView
    private lateinit var mRecyclerViewMobileRecharge: RecyclerView
    private lateinit var mMobileRechargeAdapter: MobileRechargeAdapter
    private var mMobileRechargeListModelList: ArrayList<MobileRechargeListModel> = ArrayList()

    private lateinit var mTextViewMobileRechargeSubscriptionPlan: TextView
    private lateinit var mImageViewMobileRechargeSubscriptionPlan: ImageView

    private lateinit var mTextViewMobileRechargeReferEarn: TextView
    private lateinit var mImageViewMobileRechargeReferEarn: ImageView

    private lateinit var mContentLoadingProgressBarMobileRecharge: ContentLoadingProgressBar

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewMobileRechargeNoDataFound =
            view.findViewById(R.id.textViewMobileRechargeNoDataFound)

        mMaterialCardMobileRecharge = view
            .findViewById(R.id.materialCardViewMobileRecharge)

        mRecyclerViewMobileRecharge = view.findViewById(R.id.recyclerViewMobileRecharge)

        mTextViewMobileRechargeSubscriptionPlan =
            view.findViewById(R.id.textViewMobileRechargeSubscriptionPlan)
        mImageViewMobileRechargeSubscriptionPlan =
            view.findViewById(R.id.imageViewMobileRechargeSubscriptionPlan)
        mImageViewMobileRechargeSubscriptionPlan.setOnClickListener(this@MobileRechargeFragment)

        mTextViewMobileRechargeReferEarn = view.findViewById(R.id.textViewMobileRechargeReferEarn)
        mImageViewMobileRechargeReferEarn = view.findViewById(R.id.imageViewMobileRechargeReferEarn)
        mImageViewMobileRechargeReferEarn.setOnClickListener(this@MobileRechargeFragment)

        mContentLoadingProgressBarMobileRecharge =
            view.findViewById(R.id.contentLoadingProgressBarMobileRecharge)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
    }

    override fun onResume() {
        super.onResume()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
        mContentLoadingProgressBarMobileRecharge.visibility = View.VISIBLE
        onLoadMobileRecharge()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageViewMobileRechargeSubscriptionPlan -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboard_to_benefitsOfSubscription)
            }
            R.id.imageViewMobileRechargeReferEarn -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboard_to_referEarn)
            }
        }
    }

    override fun onItemSelected(mPosition: MobileRechargeListModel) {
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

    private fun onLoadMobileRecharge() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .getMobileRechargeCompany(
                            mSharedPreferenceUtils.getLoggedInUser().loginToken
                        )
                        .enqueue(object : Callback<MobileRechargeModel> {
                            override fun onResponse(
                                call: Call<MobileRechargeModel>,
                                response: Response<MobileRechargeModel>
                            ) {
                                if (response.isSuccessful) {
                                    val mMobileRechargeModel: MobileRechargeModel? =
                                        response.body()
                                    mContentLoadingProgressBarMobileRecharge.visibility = View.GONE

                                    if (mMobileRechargeModel != null) {
                                        if (mMobileRechargeModel.mResponse.isNullOrEmpty()) {
                                            mTextViewMobileRechargeNoDataFound.visibility =
                                                View.VISIBLE
                                            mMaterialCardMobileRecharge.visibility = View.GONE
                                            mTextViewMobileRechargeSubscriptionPlan.visibility =
                                                View.GONE
                                            mImageViewMobileRechargeSubscriptionPlan.visibility =
                                                View.GONE
                                            mTextViewMobileRechargeReferEarn.visibility = View.GONE
                                            mImageViewMobileRechargeReferEarn.visibility = View.GONE
                                        } else {
                                            mTextViewMobileRechargeNoDataFound.visibility =
                                                View.GONE
                                            mMaterialCardMobileRecharge.visibility = View.VISIBLE
                                            mTextViewMobileRechargeSubscriptionPlan.visibility =
                                                View.VISIBLE
                                            mImageViewMobileRechargeSubscriptionPlan.visibility =
                                                View.VISIBLE
                                            mTextViewMobileRechargeReferEarn.visibility =
                                                View.VISIBLE
                                            mImageViewMobileRechargeReferEarn.visibility =
                                                View.VISIBLE

                                            mMobileRechargeListModelList =
                                                mMobileRechargeModel.mResponse as ArrayList<MobileRechargeListModel>

                                            mMobileRechargeAdapter = context?.let {
                                                MobileRechargeAdapter(
                                                    it,
                                                    R.layout.rv_mobile_recharge,
                                                    mMobileRechargeListModelList,
                                                    this@MobileRechargeFragment
                                                )
                                            }!!
                                            mRecyclerViewMobileRecharge.addItemDecoration(
                                                MiddleDividerItemDecoration(
                                                    requireContext(),
                                                    MiddleDividerItemDecoration.ALL
                                                )
                                            )
                                            mRecyclerViewMobileRecharge.adapter =
                                                mMobileRechargeAdapter
                                            mMobileRechargeAdapter.notifyDataSetChanged()
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
                                call: Call<MobileRechargeModel>,
                                t: Throwable
                            ) {
                                ErrorUtils.parseOnFailureException(
                                    it,
                                    call,
                                    t
                                )
                                mContentLoadingProgressBarMobileRecharge.visibility = View.GONE
                            }
                        })
                }
                else -> {
                    mContentLoadingProgressBarMobileRecharge.visibility = View.GONE
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}