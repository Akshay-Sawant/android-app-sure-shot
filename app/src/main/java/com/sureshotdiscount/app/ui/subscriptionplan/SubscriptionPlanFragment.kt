package com.sureshotdiscount.app.ui.subscriptionplan

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
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

class SubscriptionPlanFragment : Fragment(R.layout.fragment_subscription_plan) {

    private lateinit var mTextViewSubscriptionPlanNoDataFound: TextView

    private lateinit var mMaterialCardViewSubscriptionPlan: MaterialCardView
    private lateinit var mTextViewSubscriptionPlanAmount: TextView
    private lateinit var mTextViewSubscriptionPlanExpiryDate: TextView

    private lateinit var mContentLoadingProgressBarSubscriptionPlan: ContentLoadingProgressBar

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewSubscriptionPlanNoDataFound =
            view.findViewById(R.id.textViewSubscriptionPlanNoDataFound)

        mMaterialCardViewSubscriptionPlan = view.findViewById(R.id.materialCardViewSubscriptionPlan)
        mTextViewSubscriptionPlanAmount = view.findViewById(R.id.textViewSubscriptionPlanAmount)
        mTextViewSubscriptionPlanExpiryDate =
            view.findViewById(R.id.textViewSubscriptionPlanExpiryDate)

        mContentLoadingProgressBarSubscriptionPlan =
            view.findViewById(R.id.contentLoadingProgressBarSubscriptionPlan)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
    }

    override fun onResume() {
        super.onResume()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
        mContentLoadingProgressBarSubscriptionPlan.show()
        onLoadSubscriptionPlan()
    }

    private fun onLoadSubscriptionPlan() {
        context?.let {
            if (APIClient.isNetworkConnected(it)) {
                APIClient.apiInterface
                    .getSubscriptionPlan(
                        mSharedPreferenceUtils.getLoggedInUser().loginToken
                    )
                    .enqueue(object : Callback<SubscriptionPlanModel> {
                        override fun onResponse(
                            call: Call<SubscriptionPlanModel>,
                            response: Response<SubscriptionPlanModel>
                        ) {
                            if (response.isSuccessful) {
                                val mSubscriptionPlanModel: SubscriptionPlanModel? = response.body()
                                mContentLoadingProgressBarSubscriptionPlan.hide()

                                if (mSubscriptionPlanModel != null) {
                                    if (mSubscriptionPlanModel.mStatus) {
                                        mTextViewSubscriptionPlanNoDataFound.visibility = View.GONE
                                        mMaterialCardViewSubscriptionPlan.visibility = View.VISIBLE

                                        mTextViewSubscriptionPlanAmount.text =
                                            getString(
                                                R.string.text_label_rupees,
                                                mSubscriptionPlanModel.mResponse.mSubscriptionAmount
                                            )
                                        mTextViewSubscriptionPlanExpiryDate.text =
                                            mSubscriptionPlanModel.mResponse.mSubscriptionExpiryDate
                                    } else {
                                        mTextViewSubscriptionPlanNoDataFound.visibility =
                                            View.VISIBLE
                                        mTextViewSubscriptionPlanNoDataFound.text =
                                            mSubscriptionPlanModel.mMessage
                                        mMaterialCardViewSubscriptionPlan.visibility = View.GONE
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

                        override fun onFailure(call: Call<SubscriptionPlanModel>, t: Throwable) {
                            ErrorUtils.parseOnFailureException(
                                it,
                                call,
                                t
                            )
                            mContentLoadingProgressBarSubscriptionPlan.hide()
                        }
                    })
            } else {
                mContentLoadingProgressBarSubscriptionPlan.hide()
                AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
            }
        }
    }
}