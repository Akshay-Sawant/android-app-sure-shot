package com.sureshots.app.ui.dth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.sureshots.app.utils.others.MiddleDividerItemDecoration
import com.sureshots.app.R
import com.sureshots.app.data.model.DTHCompanyModel
import com.sureshots.app.data.api.APIClient
import com.sureshots.app.utils.error.ErrorUtils
import com.sureshots.app.utils.others.AlertDialogUtils
import com.sureshots.app.utils.others.SharedPreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class DTHFragment : Fragment(R.layout.fragment_d_t_h), View.OnClickListener,
    DTHCompanyAdapter.OnItemSelectedListener {

    private lateinit var mTextViewDTHNoDataFound: TextView
    private lateinit var mMaterialCardViewDTH: MaterialCardView

    private lateinit var mRecyclerViewDTH: RecyclerView
    private lateinit var mDTHCompanyAdapter: DTHCompanyAdapter
    private var mDTHCompanyModelList: ArrayList<DTHCompanyModel> = ArrayList()

    private lateinit var mImageViewDTHSubscriptionPlan: ImageView
    private lateinit var mImageViewDTHReferAndEarn: ImageView

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewDTHNoDataFound = view.findViewById(R.id.textViewDTHNoDataFound)
        mMaterialCardViewDTH = view.findViewById(R.id.materialCardViewDTH)

        mRecyclerViewDTH = view.findViewById(R.id.recyclerViewDTH)

        mImageViewDTHSubscriptionPlan = view.findViewById(R.id.imageViewDTHSubscriptionPlan)
        mImageViewDTHSubscriptionPlan.setOnClickListener(this@DTHFragment)

        mImageViewDTHReferAndEarn = view.findViewById(R.id.imageViewDTHReferEarn)
        mImageViewDTHReferAndEarn.setOnClickListener(this@DTHFragment)

        mDTHCompanyModelList.clear()
        mDTHCompanyAdapter = context?.let {
            DTHCompanyAdapter(
                it,
                R.layout.recycler_view_dth_company,
                mDTHCompanyModelList,
                this
            )
        }!!
        mRecyclerViewDTH.addItemDecoration(
            MiddleDividerItemDecoration(
                requireContext(),
                MiddleDividerItemDecoration.ALL
            )
        )
        mRecyclerViewDTH.adapter = mDTHCompanyAdapter
        mDTHCompanyAdapter.notifyDataSetChanged()

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }

        onLoadDTHCompany()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageViewDTHSubscriptionPlan -> {

            }
            R.id.imageViewReferEarn -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboard_to_referEarnFragment)
            }
        }
    }

    override fun onItemSelected(mView: View, mPosition: DTHCompanyModel) {
        view?.let {
            Navigation.findNavController(it).navigate(R.id.action_dashboard_to_rechargeOneFragment)
        }
    }

    private fun getDTHCompany() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .getDTHCompany(mSharedPreferenceUtils.getLoggedInUser().loginToken)
                        .enqueue(object : Callback<List<DTHCompanyModel>> {
                            override fun onResponse(
                                call: Call<List<DTHCompanyModel>>,
                                response: Response<List<DTHCompanyModel>>
                            ) {
                                if (response.isSuccessful) {
                                    val mDTHCompanyModel: List<DTHCompanyModel>? = response.body()

                                    if (mDTHCompanyModel.isNullOrEmpty()) {
                                        mTextViewDTHNoDataFound.visibility = View.VISIBLE
                                        mMaterialCardViewDTH.visibility = View.GONE
                                    } else {
                                        mTextViewDTHNoDataFound.visibility = View.GONE
                                        mMaterialCardViewDTH.visibility = View.VISIBLE

                                        mDTHCompanyModelList =
                                            mDTHCompanyModel as ArrayList<DTHCompanyModel>

                                        mDTHCompanyModelList.clear()
                                        mDTHCompanyAdapter = context?.let {
                                            DTHCompanyAdapter(
                                                it,
                                                R.layout.recycler_view_dth_company,
                                                mDTHCompanyModelList,
                                                this@DTHFragment
                                            )
                                        }!!
                                        mRecyclerViewDTH.addItemDecoration(
                                            MiddleDividerItemDecoration(
                                                requireContext(),
                                                MiddleDividerItemDecoration.ALL
                                            )
                                        )
                                        mRecyclerViewDTH.adapter = mDTHCompanyAdapter
                                        mDTHCompanyAdapter.notifyDataSetChanged()
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<List<DTHCompanyModel>>,
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

    private fun onLoadDTHCompany() {
        mDTHCompanyModelList.add(
            DTHCompanyModel(
                "1",
                "Airtel Digital TV",
                R.drawable.airteldish
            )
        )
        mDTHCompanyModelList.add(
            DTHCompanyModel(
                "2",
                "Videocon D2H",
                R.drawable.videocon
            )
        )
        mDTHCompanyModelList.add(
            DTHCompanyModel(
                "3",
                "Reliance Digital TV",
                R.drawable.reliance
            )
        )
        mDTHCompanyModelList.add(
            DTHCompanyModel(
                "4",
                "Sun Direct",
                R.drawable.sundirect
            )
        )
        mDTHCompanyModelList.add(
            DTHCompanyModel(
                "5",
                "Tata Sky",
                R.drawable.tatasky
            )
        )
        mDTHCompanyModelList.add(
            DTHCompanyModel(
                "6",
                "Dish TV",
                R.drawable.dishtv
            )
        )
    }
}