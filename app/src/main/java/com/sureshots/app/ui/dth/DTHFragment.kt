package com.sureshots.app.ui.dth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
//import com.innovins.helperlibrary.helper.LoadingViewManager
import com.sureshots.app.utils.others.MiddleDividerItemDecoration

import com.sureshots.app.R
/*import com.sureshots.app.ui.activity.RechargeDTHActivity
import com.sureshots.app.ui.activity.RechargeOneActivity
import com.sureshots.app.ui.activity.ReferEarnActivity*/
import com.sureshots.app.data.model.DTHCompanyModel
import com.sureshots.app.data.api.APIClient
import com.sureshots.app.utils.error.ErrorUtils
import com.sureshots.app.utils.server.ServerInvalidResponseException
import kotlinx.android.synthetic.main.fragment_d_t_h.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class DTHFragment : Fragment(R.layout.fragment_d_t_h),View.OnClickListener,
    DTHCompanyAdapter.OnItemSelectedListener {

    private lateinit var mRecyclerViewDTHCompany: RecyclerView
    private lateinit var mDTHCompanyAdapter: DTHCompanyAdapter
    private var mDTHCompanyModelList: ArrayList<DTHCompanyModel> = ArrayList()
//    private lateinit var loadingViewManager: LoadingViewManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.imageViewReferEarn.setOnClickListener(this)
        mRecyclerViewDTHCompany = view.findViewById(R.id.recyclerViewDishCompany)
//        loadingViewManager = LoadingViewManager(view, R.id.mainView,this)
        mDTHCompanyModelList.clear()
        mDTHCompanyAdapter = context?.let {
            DTHCompanyAdapter(
                it,
                R.layout.recycler_view_dth_company,
                mDTHCompanyModelList,
                this
            )
        }!!
        mRecyclerViewDTHCompany.addItemDecoration(
            MiddleDividerItemDecoration(
                requireContext(),
                MiddleDividerItemDecoration.ALL
            )
        )
            mRecyclerViewDTHCompany.adapter = mDTHCompanyAdapter
            mDTHCompanyAdapter.notifyDataSetChanged()


        onLoadDTHCompany()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageViewReferEarn -> view?.let {
//                startActivity(ReferEarnActivity.newIntentNewTask(requireActivity()))
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboard_to_referEarnFragment)
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

    override fun onItemSelected(adapterPosition: Int) {
//        startActivity(RechargeDTHActivity.newIntent(requireContext()))
    }

    private fun getDTHCompany() {
        /*val loginHelper = LoginHelper(this)
        if(!loginHelper.isUserLogin()){
            LoginHelper.startLoginFlow(this)
            return
        }*/
        if (!APIClient.isNetworkConnected(activity?.applicationContext!!)) {
            //AlertDialogManager.instance.displayNoConnectionAlert(this)
            /*loadingViewManager.showErrorView(
                getString(R.string.alert_error_no_internet_connection_title),
                getString(R.string.alert_error_no_internet_connection))
            return*/
        }
//        loadingViewManager.showLoadingView(requireActivity(), "Loading files...")
        val call: Call<List<DTHCompanyModel>> = APIClient.apiInterface.getDTHCompany("abc")
        call.enqueue(object : Callback<List<DTHCompanyModel>> {
            override fun onResponse(call: Call<List<DTHCompanyModel>>, response: Response<List<DTHCompanyModel>>) {
//                loadingViewManager.hideLoadingView()
                if (response.isSuccessful) {
                    val simCompanyList: List<DTHCompanyModel>? = response.body()
                    if (simCompanyList != null) {
                        mDTHCompanyModelList.clear()
                        mDTHCompanyModelList.addAll(simCompanyList)
                        mDTHCompanyAdapter.notifyDataSetChanged()
                    } else {
                        // server returned 200 with a blank response :/
                        ErrorUtils.logNetworkError(
                            ServerInvalidResponseException
                                .ERROR_200_BLANK_RESPONSE + "\nResponse: " + response.toString(),
                            null
                        )
                        /*loadingViewManager.showErrorView(
                            getString(R.string.alert_connection_status_not_ok_title),
                            getString(R.string.alert_connection_status_not_ok_message))*/
                        // server returned 200 with a blank response :/
                    }
                }
            }
            override fun onFailure(call: Call<List<DTHCompanyModel>>, t: Throwable) {
//                ErrorUtils.parseOnFailureException(activity!!, call, t, loadingViewManager)
            }

        })
    }

}
