package com.sureshotdiscount.app.ui.mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.sureshotdiscount.app.utils.others.MiddleDividerItemDecoration
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.model.SimCompanyModel
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import kotlinx.android.synthetic.main.fragment_mobile_recharge.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileRechargeFragment : Fragment(R.layout.fragment_mobile_recharge), View.OnClickListener,
    SimCompanyAdapter.OnItemSelectedListener {

    private lateinit var mTextViewMobileRechargeNoDataFound: TextView
    private lateinit var mMaterialCardMobileRecharge: MaterialCardView
    private lateinit var mRecyclerViewMobileRecharge: RecyclerView
    private lateinit var mSimCompanyAdapter: SimCompanyAdapter
    private var mSimCompanyModelList: ArrayList<SimCompanyModel> = ArrayList()

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewMobileRechargeNoDataFound =
            view.findViewById(R.id.textViewMobileRechargeNoDataFound)

        mMaterialCardMobileRecharge = view
            .findViewById(R.id.materialCardViewMobileRecharge)

        view.imageViewReferEarn.setOnClickListener(this)
        mRecyclerViewMobileRecharge = view.findViewById(R.id.recyclerViewMobileRecharge)

        mSimCompanyModelList.clear()
        mSimCompanyAdapter = context?.let {
            SimCompanyAdapter(
                it,
                R.layout.recycler_view_sim_company,
                mSimCompanyModelList,
                this
            )
        }!!
        mRecyclerViewMobileRecharge.addItemDecoration(
            MiddleDividerItemDecoration(
                requireContext(),
                MiddleDividerItemDecoration.ALL
            )
        )
        mRecyclerViewMobileRecharge.adapter = mSimCompanyAdapter
        mSimCompanyAdapter.notifyDataSetChanged()

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }

        onLoadSimCompany()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageViewReferEarn -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboard_to_referEarn)
            }
        }
    }

    override fun onItemSelected(mPosition: SimCompanyModel) {
        view?.let {
            Navigation.findNavController(it).navigate(R.id.action_dashboard_to_recharge)
        }
    }

    private fun onLoadSimCompany() {
        mSimCompanyModelList.add(
            SimCompanyModel(
                "1",
                "Airtel",
                R.drawable.airtel
            )
        )
        mSimCompanyModelList.add(
            SimCompanyModel(
                "2",
                "Jio",
                R.drawable.jio
            )
        )
        mSimCompanyModelList.add(
            SimCompanyModel(
                "3",
                "BSNL",
                R.drawable.bsnllogo
            )
        )
        mSimCompanyModelList.add(
            SimCompanyModel(
                "4",
                "idea",
                R.drawable.idea
            )
        )
        mSimCompanyModelList.add(
            SimCompanyModel(
                "5",
                "vodafone",
                R.drawable.vodafone
            )
        )
        mSimCompanyModelList.add(
            SimCompanyModel(
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
                        .getSimCompany(
                            mSharedPreferenceUtils.getLoggedInUser().loginToken
                        )
                        .enqueue(object : Callback<List<SimCompanyModel>> {
                            override fun onResponse(
                                call: Call<List<SimCompanyModel>>,
                                response: Response<List<SimCompanyModel>>
                            ) {
                                if (response.isSuccessful) {
                                    val mSimCompanyModel: List<SimCompanyModel>? = response.body()

                                    if (mSimCompanyModel.isNullOrEmpty()) {
                                        mTextViewMobileRechargeNoDataFound.visibility = View.VISIBLE
                                        mMaterialCardMobileRecharge.visibility = View.GONE
                                    } else {
                                        mTextViewMobileRechargeNoDataFound.visibility = View.GONE
                                        mMaterialCardMobileRecharge.visibility = View.VISIBLE

                                        mSimCompanyModelList =
                                            mSimCompanyModel as ArrayList<SimCompanyModel>

                                        mSimCompanyModelList.clear()
                                        mSimCompanyAdapter = context?.let {
                                            SimCompanyAdapter(
                                                it,
                                                R.layout.recycler_view_sim_company,
                                                mSimCompanyModelList,
                                                this@MobileRechargeFragment
                                            )
                                        }!!
                                        mRecyclerViewMobileRecharge.addItemDecoration(
                                            MiddleDividerItemDecoration(
                                                requireContext(),
                                                MiddleDividerItemDecoration.ALL
                                            )
                                        )
                                        mRecyclerViewMobileRecharge.adapter = mSimCompanyAdapter
                                        mSimCompanyAdapter.notifyDataSetChanged()
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<List<SimCompanyModel>>,
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