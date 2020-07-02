package com.sureshots.app.ui.mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
//import com.innovins.helperlibrary.helper.LoadingViewManager
import com.sureshots.app.MiddleDividerItemDecoration

import com.sureshots.app.R
/*import com.sureshots.app.ui.activity.RechargeOneActivity
import com.sureshots.app.ui.activity.ReferEarnActivity*/
import com.sureshots.app.model.SimCompanyModel
import com.sureshots.app.network.APIClient
import com.sureshots.app.network.ErrorUtils
import com.sureshots.app.network.ServerInvalidResponseException
import kotlinx.android.synthetic.main.fragment_mobile_recharge.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileRechargeFragment : Fragment(R.layout.fragment_mobile_recharge), View.OnClickListener,
    SimCompanyAdapter.OnItemSelectedListener {

    private lateinit var mRecyclerViewSimCompany: RecyclerView
    private lateinit var mSimCompanyAdapter: SimCompanyAdapter
    private var mSimCompanyModelList: ArrayList<SimCompanyModel> = ArrayList()
//    private lateinit var loadingViewManager: LoadingViewManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.imageViewReferEarn.setOnClickListener(this)
        mRecyclerViewSimCompany = view.findViewById(R.id.recyclerViewSimCompany)
        //loadingViewManager = LoadingViewManager(view, R.id.mainView,this)
        mSimCompanyModelList.clear()
        mSimCompanyAdapter = context?.let {
            SimCompanyAdapter(
                it,
                R.layout.recycler_view_sim_company,
                mSimCompanyModelList,
                this
            )
        }!!
        mRecyclerViewSimCompany.addItemDecoration(
            MiddleDividerItemDecoration(
                requireContext(),
                MiddleDividerItemDecoration.ALL
            )
        )
        mRecyclerViewSimCompany.adapter = mSimCompanyAdapter
        mSimCompanyAdapter.notifyDataSetChanged()

        onLoadSimCompany()
    }

    private fun onLoadSimCompany() {
        mSimCompanyModelList.add(SimCompanyModel("1", "Airtel", R.drawable.airtel))
        mSimCompanyModelList.add(SimCompanyModel("2", "Jio", R.drawable.jio))
        mSimCompanyModelList.add(SimCompanyModel("3", "BSNL", R.drawable.bsnllogo))
        mSimCompanyModelList.add(SimCompanyModel("4", "idea", R.drawable.idea))
        mSimCompanyModelList.add(SimCompanyModel("5", "vodafone", R.drawable.vodafone))
        mSimCompanyModelList.add(SimCompanyModel("6", "Tata", R.drawable.tata))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageViewReferEarn -> view?.let {
//                startActivity(ReferEarnActivity.newIntent(requireActivity()))
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboard_to_referEarnFragment)
            }
        }
    }

    override fun onItemSelected(adapterPosition: Int) {
//        startActivity(RechargeOneActivity.newIntent(requireContext()))
    }

    private fun getSimCompany() {
        /*val loginHelper = LoginHelper(this)
        if(!loginHelper.isUserLogin()){
            LoginHelper.startLoginFlow(this)
            return
        }*/
        if (!APIClient.isNetworkConnected(activity?.applicationContext!!)) {
            //AlertDialogManager.instance.displayNoConnectionAlert(this)
            /*loadingViewManager.showErrorView(
                getString(R.string.alert_error_no_internet_connection_title),
                getString(R.string.alert_error_no_internet_connection))*/
            return
        }
//        loadingViewManager.showLoadingView(requireActivity(), "Loading files...")
        val call: Call<List<SimCompanyModel>> = APIClient.apiInterface.getSimCompany("abc")
        call.enqueue(object : Callback<List<SimCompanyModel>> {
            override fun onResponse(
                call: Call<List<SimCompanyModel>>,
                response: Response<List<SimCompanyModel>>
            ) {
//                loadingViewManager.hideLoadingView()
                if (response.isSuccessful) {
                    val simCompanyList: List<SimCompanyModel>? = response.body()
                    if (simCompanyList != null) {
                        mSimCompanyModelList.clear()
                        mSimCompanyModelList.addAll(simCompanyList)
                        mSimCompanyAdapter.notifyDataSetChanged()
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

            override fun onFailure(call: Call<List<SimCompanyModel>>, t: Throwable) {
//                ErrorUtils.parseOnFailureException(activity!!, call, t, loadingViewManager)
            }

        })
    }

}
