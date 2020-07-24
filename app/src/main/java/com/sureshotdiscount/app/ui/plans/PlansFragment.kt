package com.sureshotdiscount.app.ui.plans

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

class PlansFragment : Fragment(R.layout.fragment_plans) {

    private lateinit var mTextViewPlansNoDataFound: TextView
    private lateinit var mRecyclerViewPlans: RecyclerView

    private lateinit var mPlansAdapter: PlansAdapter
    private var mPlansModelList: ArrayList<PlansModel> = ArrayList()

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewPlansNoDataFound = view.findViewById(R.id.textViewPlansNoDataFound)
        mRecyclerViewPlans = view.findViewById(R.id.recyclerViewPlans)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
    }

    override fun onResume() {
        super.onResume()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
//        onLoadPlans()
    }

    private fun onLoadPlans() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .getPlans(
                            mSharedPreferenceUtils.getLoggedInUser().loginToken,
                            "",
                            ""
                        ).enqueue(object : Callback<List<PlansModel>> {
                            override fun onResponse(
                                call: Call<List<PlansModel>>,
                                response: Response<List<PlansModel>>
                            ) {
                                if (response.isSuccessful) {
                                    val mPlansModel: List<PlansModel>? =
                                        response.body()

                                    if (mPlansModel.isNullOrEmpty()) {
                                        mTextViewPlansNoDataFound.visibility =
                                            View.VISIBLE
                                        mRecyclerViewPlans.visibility = View.GONE
                                    } else {
                                        mTextViewPlansNoDataFound.visibility = View.GONE
                                        mRecyclerViewPlans.visibility = View.VISIBLE

                                        mPlansModelList =
                                            mPlansModel as ArrayList<PlansModel>

                                        mPlansAdapter = context?.let {
                                            PlansAdapter(
                                                R.layout.rv_recharge_history,
                                                mPlansModelList
                                            )
                                        }!!
                                        mRecyclerViewPlans.adapter =
                                            mPlansAdapter
                                        mPlansAdapter.notifyDataSetChanged()
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<List<PlansModel>>,
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