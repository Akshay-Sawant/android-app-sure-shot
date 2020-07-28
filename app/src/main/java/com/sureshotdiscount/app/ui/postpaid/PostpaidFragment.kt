package com.sureshotdiscount.app.ui.postpaid

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.widget.ContentLoadingProgressBar
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.ui.prepaid.CircleListModel
import com.sureshotdiscount.app.ui.prepaid.CircleModel
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostpaidFragment : Fragment(R.layout.fragment_postpaid), View.OnClickListener {

    private lateinit var mAppCompatImageViewPostPaidCompanyLogo: AppCompatImageView
    private lateinit var mTextViewPostPaidCompanyName: TextView
    private lateinit var mTextViewPostPaidChange: TextView

    private lateinit var mTextInputLayoutPostPaidMobileNumber: TextInputLayout
    private lateinit var mTextInputEditTextPostPaidMobileNumber: TextInputEditText

    private lateinit var mAppCompatSpinnerPostpaidLocation: AppCompatSpinner
    private lateinit var mLocationAdapter: ArrayAdapter<String>
    private var mLocationArrayList = ArrayList<String>()
    private var mLocationCodeArrayList = ArrayList<String>()
    private var mCircleModelListModelList: ArrayList<CircleListModel> = ArrayList()
    private lateinit var mSelectedLocationName: String
    private lateinit var mSelectedLocationCode: String

    private lateinit var mButtonPostPaidProceed: Button

    private lateinit var mContentLoadingProgressBarPostpaid: ContentLoadingProgressBar

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAppCompatImageViewPostPaidCompanyLogo =
            view.findViewById(R.id.appCompatImageViewPostPaidCompanyLogo)
        mTextViewPostPaidCompanyName = view.findViewById(R.id.textViewPostPaidCompanyName)
        mTextViewPostPaidChange = view.findViewById(R.id.textViewPostPaidChange)
        mTextViewPostPaidChange.setOnClickListener(this@PostpaidFragment)

        mTextInputLayoutPostPaidMobileNumber =
            view.findViewById(R.id.textInputLayoutPostPaidMobileNumber)
        mTextInputEditTextPostPaidMobileNumber =
            view.findViewById(R.id.textInputEditTextPostPaidMobileNumber)

        mAppCompatSpinnerPostpaidLocation = view.findViewById(R.id.appCompatSpinnerPostpaidLocation)
        mAppCompatSpinnerPostpaidLocation.tag = getString(R.string.text_label_location)

        mButtonPostPaidProceed = view.findViewById(R.id.buttonPostPaidProceed)
        mButtonPostPaidProceed.setOnClickListener(this@PostpaidFragment)

        mContentLoadingProgressBarPostpaid =
            view.findViewById(R.id.contentLoadingProgressBarPostpaid)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
    }

    override fun onResume() {
        super.onResume()
        mContentLoadingProgressBarPostpaid.show()
        onLoadCompanyDetails()
        onSelectItemFromSpinner()
        onLoadLocation()
    }

    override fun onDetach() {
        super.onDetach()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textViewPostPaidChange -> view?.let {
                Navigation.findNavController(it).popBackStack()
            }
            R.id.buttonPostPaidProceed -> isPostpaidValidated()
        }
    }

    private fun onSelectItemFromSpinner() {
        mAppCompatSpinnerPostpaidLocation.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    mSelectedLocationName = mLocationArrayList[position]
                    mSelectedLocationCode = mLocationCodeArrayList[position]
                    when (mAppCompatSpinnerPostpaidLocation.selectedItemPosition) {
                        0 -> {
                            mButtonPostPaidProceed.visibility = View.GONE
                        }
                        else -> {
                            mSharedPreferenceUtils.saveRechargeCircleCode(mSelectedLocationCode)
                            mButtonPostPaidProceed.visibility = View.VISIBLE
                        }
                    }
                }
            }
    }

    private fun onLoadCompanyDetails() {
        context?.let {
            Glide.with(it)
                .load(mSharedPreferenceUtils.getRechargeCompanyLogo(it))
                .placeholder(R.drawable.launcher_white)
                .into(mAppCompatImageViewPostPaidCompanyLogo)
            mTextViewPostPaidCompanyName.text = mSharedPreferenceUtils.getRechargeCompanyName(it)
        }
    }

    private fun isPostpaidValidated() {
        when {
            !ValidationUtils.getValidationUtils()
                .isInputEditTextMobileFunc(
                    mTextInputLayoutPostPaidMobileNumber,
                    mTextInputEditTextPostPaidMobileNumber,
                    getString(R.string.text_error_mobile)
                ) -> return
            else -> {
                mSharedPreferenceUtils.saveRechargeMobileNumber(
                    mTextInputEditTextPostPaidMobileNumber.text.toString().trim()
                )
                mSharedPreferenceUtils.saveRechargeType(getString(R.string.text_label_postpaid))
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_recharge_to_rechargeDetails)
                }
            }
        }
    }

    private fun onLoadLocation() {
        context?.let { it ->
            when {
                APIClient.isNetworkConnected(it) -> {
                    APIClient.apiInterface
                        .getCircleList(mSharedPreferenceUtils.getLoggedInUser().loginToken)
                        .enqueue(object : Callback<CircleModel> {
                            override fun onResponse(
                                call: Call<CircleModel>,
                                response: Response<CircleModel>
                            ) {
                                if (response.isSuccessful) {
                                    val mCircleModel: CircleModel? = response.body()
                                    mContentLoadingProgressBarPostpaid.hide()

                                    if (mCircleModel != null) {
                                        if (mCircleModel.mStatus) {
                                            mCircleModelListModelList =
                                                mCircleModel.mResponse as ArrayList<CircleListModel>
                                            mLocationArrayList.add(
                                                0,
                                                getString(R.string.text_label_location)
                                            )
                                            mLocationCodeArrayList.add(
                                                0,
                                                getString(R.string.text_label_location)
                                            )
                                            for (mItem in mCircleModel.mResponse.indices) {
                                                mLocationArrayList.add(mCircleModelListModelList[mItem].mCircleName)
                                                mLocationCodeArrayList.add(mCircleModelListModelList[mItem].mCircleCode)
                                            }
                                            mLocationAdapter = ArrayAdapter(
                                                it,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                mLocationArrayList
                                            )
                                            mLocationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                            mAppCompatSpinnerPostpaidLocation.adapter =
                                                mLocationAdapter
                                            mLocationAdapter.notifyDataSetChanged()
                                        } else {
                                            AlertDialogUtils.getInstance().showAlert(
                                                it,
                                                R.drawable.ic_warning_black,
                                                "Invalid credentials",
                                                "Your token has been expired. Please do re-login to proceed",
                                                getString(android.R.string.ok),
                                                null,
                                                DialogInterface.OnDismissListener {
                                                    it.dismiss()
                                                }
                                            )
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
                                call: Call<CircleModel>,
                                t: Throwable
                            ) {
                                ErrorUtils.parseOnFailureException(
                                    it,
                                    call,
                                    t
                                )
                                mContentLoadingProgressBarPostpaid.hide()
                            }
                        })
                }
                else -> {
                    mContentLoadingProgressBarPostpaid.hide()
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}