package com.sureshotdiscount.app.ui.mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.sureshotdiscount.app.utils.others.MiddleDividerItemDecoration
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileRechargeFragment : Fragment(R.layout.fragment_mobile_recharge), View.OnClickListener,
    MobileRechargeAdapter.OnItemSelectedListener {

    private lateinit var mTextViewMobileRechargeNoDataFound: TextView
    private lateinit var mMaterialCardMobileRecharge: MaterialCardView
    private lateinit var mRecyclerViewMobileRecharge: RecyclerView
    private lateinit var mMobileRechargeAdapter: MobileRechargeAdapter
    private var mMobileRechargeModelList: ArrayList<MobileRechargeModel> = ArrayList()

    private lateinit var mImageViewMobileRechargeSubscriptionPlan: ImageView
    private lateinit var mImageViewMobileRechargeReferEarn: ImageView

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewMobileRechargeNoDataFound =
            view.findViewById(R.id.textViewMobileRechargeNoDataFound)

        mMaterialCardMobileRecharge = view
            .findViewById(R.id.materialCardViewMobileRecharge)

        mRecyclerViewMobileRecharge = view.findViewById(R.id.recyclerViewMobileRecharge)

        mMobileRechargeModelList.clear()
        mMobileRechargeAdapter = context?.let {
            MobileRechargeAdapter(
                it,
                R.layout.rv_mobile_recharge,
                mMobileRechargeModelList,
                this
            )
        }!!
        mRecyclerViewMobileRecharge.addItemDecoration(
            MiddleDividerItemDecoration(
                requireContext(),
                MiddleDividerItemDecoration.ALL
            )
        )
        mRecyclerViewMobileRecharge.adapter = mMobileRechargeAdapter
        mMobileRechargeAdapter.notifyDataSetChanged()

        mImageViewMobileRechargeSubscriptionPlan =
            view.findViewById(R.id.imageViewMobileRechargeSubscriptionPlan)
        mImageViewMobileRechargeSubscriptionPlan.setOnClickListener(this@MobileRechargeFragment)

        mImageViewMobileRechargeReferEarn = view.findViewById(R.id.imageViewMobileRechargeReferEarn)
        mImageViewMobileRechargeReferEarn.setOnClickListener(this@MobileRechargeFragment)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }

        onLoadSimCompany()
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

    override fun onItemSelected(mPosition: MobileRechargeModel) {
        view?.let {
            Navigation.findNavController(it).navigate(R.id.action_dashboard_to_recharge)
        }
    }

    private fun onLoadSimCompany() {
        mMobileRechargeModelList.add(
            MobileRechargeModel(
                "1",
                "Airtel",
                R.drawable.airtel
            )
        )
        mMobileRechargeModelList.add(
            MobileRechargeModel(
                "2",
                "Jio",
                R.drawable.jio
            )
        )
        mMobileRechargeModelList.add(
            MobileRechargeModel(
                "3",
                "BSNL",
                R.drawable.bsnllogo
            )
        )
        mMobileRechargeModelList.add(
            MobileRechargeModel(
                "4",
                "idea",
                R.drawable.idea
            )
        )
        mMobileRechargeModelList.add(
            MobileRechargeModel(
                "5",
                "vodafone",
                R.drawable.vodafone
            )
        )
        mMobileRechargeModelList.add(
            MobileRechargeModel(
                "6",
                "Tata",
                R.drawable.tata
            )
        )
    }

    private fun getSimCompany() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .getMobileRechargeCompany(
                            mSharedPreferenceUtils.getLoggedInUser().loginToken
                        )
                        .enqueue(object : Callback<List<MobileRechargeModel>> {
                            override fun onResponse(
                                call: Call<List<MobileRechargeModel>>,
                                response: Response<List<MobileRechargeModel>>
                            ) {
                                if (response.isSuccessful) {
                                    val mMobileRechargeModel: List<MobileRechargeModel>? = response.body()

                                    if (mMobileRechargeModel.isNullOrEmpty()) {
                                        mTextViewMobileRechargeNoDataFound.visibility = View.VISIBLE
                                        mMaterialCardMobileRecharge.visibility = View.GONE
                                    } else {
                                        mTextViewMobileRechargeNoDataFound.visibility = View.GONE
                                        mMaterialCardMobileRecharge.visibility = View.VISIBLE

                                        mMobileRechargeModelList =
                                            mMobileRechargeModel as ArrayList<MobileRechargeModel>

                                        mMobileRechargeModelList.clear()
                                        mMobileRechargeAdapter = context?.let {
                                            MobileRechargeAdapter(
                                                it,
                                                R.layout.rv_mobile_recharge,
                                                mMobileRechargeModelList,
                                                this@MobileRechargeFragment
                                            )
                                        }!!
                                        mRecyclerViewMobileRecharge.addItemDecoration(
                                            MiddleDividerItemDecoration(
                                                requireContext(),
                                                MiddleDividerItemDecoration.ALL
                                            )
                                        )
                                        mRecyclerViewMobileRecharge.adapter = mMobileRechargeAdapter
                                        mMobileRechargeAdapter.notifyDataSetChanged()
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<List<MobileRechargeModel>>,
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