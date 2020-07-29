package com.sureshotdiscount.app.ui.plans

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.ui.rechargeHistory.IPlans
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates


class PlansFragment : Fragment(R.layout.fragment_plans), IPlans {

    private lateinit var mContentLoadingProgressBarPlans: ContentLoadingProgressBar

    private lateinit var mTextViewPlansNoDataFound: TextView
    private lateinit var mRecyclerViewPlans: RecyclerView
    private lateinit var mLinearLayoutManagerPlans: LinearLayoutManager

    private lateinit var mPlansAdapter: PlansAdapter
    private var mPlansListModelList: ArrayList<PlansListModel> = ArrayList()

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils
    private lateinit var mCompanyCode: String
    private lateinit var mCircleCode: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContentLoadingProgressBarPlans = view.findViewById(R.id.contentLoadingProgressBarPlans)

        mTextViewPlansNoDataFound = view.findViewById(R.id.textViewPlansNoDataFound)
        mRecyclerViewPlans = view.findViewById(R.id.recyclerViewPlans)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
            mCompanyCode = mSharedPreferenceUtils.getRechargeCompanyCode(it).toString()
            mCircleCode = mSharedPreferenceUtils.getRechargeCircleCode(it).toString()
        }
    }

    override fun onResume() {
        super.onResume()
        mContentLoadingProgressBarPlans.show()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
        onLoadPlans()
    }

    override fun onClickPlans(mPosition: PlansListModel) {
        mSharedPreferenceUtils.saveRechargeAmount(mPosition.mAmount)
        view?.let { Navigation.findNavController(it).popBackStack() }
    }

    private fun onLoadPlans() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .getPlans(
                            mSharedPreferenceUtils.getLoggedInUser().loginToken,
                            mCompanyCode,
                            mCircleCode
                        ).enqueue(object : Callback<PlansModel> {
                            override fun onResponse(
                                call: Call<PlansModel>,
                                response: Response<PlansModel>
                            ) {
                                if (response.isSuccessful) {
                                    val mPlansModel: PlansModel? =
                                        response.body()
                                    mContentLoadingProgressBarPlans.hide()

                                    if (mPlansModel != null) {
                                        if (mPlansModel.mStatus) {
                                            mTextViewPlansNoDataFound.visibility = View.GONE
                                            mRecyclerViewPlans.visibility = View.VISIBLE

                                            mPlansListModelList =
                                                mPlansModel.mResponse as ArrayList<PlansListModel>

                                            mPlansAdapter = context?.let {
                                                PlansAdapter(
                                                    R.layout.rv_plans,
                                                    mPlansListModelList,
                                                    this@PlansFragment
                                                )
                                            }!!
                                            mRecyclerViewPlans.adapter =
                                                mPlansAdapter
                                            mPlansAdapter.notifyDataSetChanged()
                                        } else {
                                            mTextViewPlansNoDataFound.visibility =
                                                View.VISIBLE
                                            mRecyclerViewPlans.visibility = View.GONE
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
                                call: Call<PlansModel>,
                                t: Throwable
                            ) {
                                ErrorUtils.parseOnFailureException(
                                    it,
                                    call,
                                    t
                                )
                                mContentLoadingProgressBarPlans.hide()
                            }
                        })
                }
                else -> {
                    mContentLoadingProgressBarPlans.hide()
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}