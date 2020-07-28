package com.sureshotdiscount.app.ui.prepaid

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.widget.ContentLoadingProgressBar
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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

class PrepaidFragment : Fragment(R.layout.fragment_prepaid), View.OnClickListener {

    private lateinit var mAppCompatImageViewPrepaidCompanyLogo: AppCompatImageView
    private lateinit var mTextViewPrepaidCompanyName: TextView
    private lateinit var mTextViewPrepaidChange: TextView

    private lateinit var mTextInputLayoutPrepaidMobileNumber: TextInputLayout
    private lateinit var mTextInputEditTextPrepaidMobileNumber: TextInputEditText

    private lateinit var mAppCompatSpinnerPrepaidLocation: AppCompatSpinner
    private lateinit var mLocationAdapter: ArrayAdapter<String>
    private var mLocationArrayList = ArrayList<String>()
    private var mLocationCodeArrayList = ArrayList<String>()
    private var mCircleModelListModelList: ArrayList<CircleListModel> = ArrayList()
    private lateinit var mSelectedLocationName: String
    private lateinit var mSelectedLocationCode: String

    private lateinit var mButtonPrepaidProceed: Button

    private lateinit var mContentLoadingProgressBarPrepaid: ContentLoadingProgressBar

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAppCompatImageViewPrepaidCompanyLogo =
            view.findViewById(R.id.appCompatImageViewPrepaidCompanyLogo)
        mTextViewPrepaidCompanyName = view.findViewById(R.id.textViewPrepaidCompanyName)
        mTextViewPrepaidChange = view.findViewById(R.id.textViewPrepaidChange)
        mTextViewPrepaidChange.setOnClickListener(this@PrepaidFragment)

        mTextInputLayoutPrepaidMobileNumber =
            view.findViewById(R.id.textInputLayoutPrepaidMobileNumber)
        mTextInputEditTextPrepaidMobileNumber =
            view.findViewById(R.id.textInputEditTextPrepaidMobileNumber)

        mAppCompatSpinnerPrepaidLocation = view.findViewById(R.id.appCompatSpinnerPrepaidLocation)
        mAppCompatSpinnerPrepaidLocation.tag = getString(R.string.text_label_location)

        mButtonPrepaidProceed = view.findViewById(R.id.buttonPrepaidProceed)
        mButtonPrepaidProceed.setOnClickListener(this@PrepaidFragment)

        mContentLoadingProgressBarPrepaid = view.findViewById(R.id.contentLoadingProgressBarPrepaid)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
    }

    override fun onResume() {
        super.onResume()
        mContentLoadingProgressBarPrepaid.show()
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
            R.id.textViewPrepaidChange -> view?.let {
                Navigation.findNavController(it).popBackStack()
            }
            R.id.buttonPrepaidProceed -> isPrepaidValidated()
        }
    }

    private fun onSelectItemFromSpinner() {
        mAppCompatSpinnerPrepaidLocation.onItemSelectedListener =
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
                    when (mAppCompatSpinnerPrepaidLocation.selectedItemPosition) {
                        0 -> {
                            mButtonPrepaidProceed.visibility = View.GONE
                        }
                        else -> {
                            mSharedPreferenceUtils.saveRechargeCircleCode(mSelectedLocationCode)
                            mButtonPrepaidProceed.visibility = View.VISIBLE
                        }
                    }
                }
            }
    }

    private fun onLoadCompanyDetails() {
        context?.let {
            Glide.with(this@PrepaidFragment)
                .load(mSharedPreferenceUtils.getRechargeCompanyLogo(it))
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(mAppCompatImageViewPrepaidCompanyLogo)
            mTextViewPrepaidCompanyName.text = mSharedPreferenceUtils.getRechargeCompanyName(it)
        }
    }

    private fun isPrepaidValidated() {
        when {
            !ValidationUtils.getValidationUtils()
                .isInputEditTextMobileFunc(
                    mTextInputLayoutPrepaidMobileNumber,
                    mTextInputEditTextPrepaidMobileNumber,
                    getString(R.string.text_error_mobile)
                ) -> return
            else -> {
                mSharedPreferenceUtils.saveRechargeMobileNumber(
                    mTextInputEditTextPrepaidMobileNumber.text.toString().trim()
                )
                mSharedPreferenceUtils.saveRechargeType(getString(R.string.text_label_prepaid))
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
                                    mContentLoadingProgressBarPrepaid.hide()

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
                                            mAppCompatSpinnerPrepaidLocation.adapter =
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
                                mContentLoadingProgressBarPrepaid.hide()
                            }
                        })
                }
                else -> {
                    mContentLoadingProgressBarPrepaid.hide()
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}