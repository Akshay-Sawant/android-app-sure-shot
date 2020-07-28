package com.sureshotdiscount.app.ui.dth

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

/**
 * A simple [Fragment] subclass.
 */
class DTHFragment : Fragment(R.layout.fragment_d_t_h), View.OnClickListener,
    DTHAdapter.OnItemSelectedListener {

    private lateinit var mTextViewDTHNoDataFound: TextView
    private lateinit var mMaterialCardViewDTH: MaterialCardView

    private lateinit var mRecyclerViewDTH: RecyclerView
    private lateinit var mDTHAdapter: DTHAdapter
    private var mDTHModelList: ArrayList<DTHModel> = ArrayList()

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

        mDTHModelList.clear()
        mDTHAdapter = context?.let {
            DTHAdapter(
                it,
                R.layout.rv_dth,
                mDTHModelList,
                this
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

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }

        onLoadDTHCompany()
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

    override fun onItemSelected(mView: View, mPosition: DTHModel) {
        view?.let {
            Navigation.findNavController(it).navigate(R.id.action_myAccount_to_recharge)
        }
    }

    private fun getDTHCompany() {
        context?.let {
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .getDTHCompany(mSharedPreferenceUtils.getLoggedInUser().loginToken)
                        .enqueue(object : Callback<List<DTHModel>> {
                            override fun onResponse(
                                call: Call<List<DTHModel>>,
                                response: Response<List<DTHModel>>
                            ) {
                                if (response.isSuccessful) {
                                    val mDTHModel: List<DTHModel>? = response.body()

                                    if (mDTHModel.isNullOrEmpty()) {
                                        mTextViewDTHNoDataFound.visibility = View.VISIBLE
                                        mMaterialCardViewDTH.visibility = View.GONE
                                    } else {
                                        mTextViewDTHNoDataFound.visibility = View.GONE
                                        mMaterialCardViewDTH.visibility = View.VISIBLE

                                        mDTHModelList =
                                            mDTHModel as ArrayList<DTHModel>

                                        mDTHModelList.clear()
                                        mDTHAdapter = context?.let {
                                            DTHAdapter(
                                                it,
                                                R.layout.rv_dth,
                                                mDTHModelList,
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
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<List<DTHModel>>,
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
        mDTHModelList.add(
            DTHModel(
                "1",
                "Airtel Digital TV",
                R.drawable.airteldish
            )
        )
        mDTHModelList.add(
            DTHModel(
                "2",
                "Videocon D2H",
                R.drawable.videocon
            )
        )
        mDTHModelList.add(
            DTHModel(
                "3",
                "Reliance Digital TV",
                R.drawable.reliance
            )
        )
        mDTHModelList.add(
            DTHModel(
                "4",
                "Sun Direct",
                R.drawable.sundirect
            )
        )
        mDTHModelList.add(
            DTHModel(
                "5",
                "Tata Sky",
                R.drawable.tatasky
            )
        )
        mDTHModelList.add(
            DTHModel(
                "6",
                "Dish TV",
                R.drawable.dishtv
            )
        )
    }
}