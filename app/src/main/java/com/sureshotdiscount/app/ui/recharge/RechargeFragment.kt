package com.sureshotdiscount.app.ui.recharge

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
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class RechargeFragment : Fragment(R.layout.fragment_recharge), View.OnClickListener {

    private lateinit var mAppCompatImageViewRechargeCompanyLogo: AppCompatImageView
    private lateinit var mTextViewRechargeCompanyName: TextView
    private lateinit var mTextViewRechargeChange: TextView

    private lateinit var mTextInputLayoutRechargeMobileNumber: TextInputLayout
    private lateinit var mTextInputEditTextRechargeMobileNumber: TextInputEditText

    private lateinit var mAppCompatSpinnerRechargeLocation: AppCompatSpinner
    private lateinit var mLocationAdapter: ArrayAdapter<String>
    private var mLocationArrayList = ArrayList<String>()
    private var mLocationCodeArrayList = ArrayList<String>()
    private var mCircleModelListModelList: ArrayList<CircleListModel> = ArrayList()
    private lateinit var mSelectedLocationName: String
    private lateinit var mSelectedLocationCode: String

    private lateinit var mTextInputLayoutRechargeSubscriptionId: TextInputLayout
    private lateinit var mTextInputEditTextRechargeSubscriptionId: TextInputEditText

    private lateinit var mButtonRechargeProceed: Button

    private lateinit var mContentLoadingProgressBarRecharge: ContentLoadingProgressBar

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils
    private var mIsMobileRecharge by Delegates.notNull<Boolean>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAppCompatImageViewRechargeCompanyLogo =
            view.findViewById(R.id.appCompatImageViewRechargeCompanyLogo)
        mTextViewRechargeCompanyName = view.findViewById(R.id.textViewRechargeCompanyName)
        mTextViewRechargeChange = view.findViewById(R.id.textViewRechargeChange)
        mTextViewRechargeChange.setOnClickListener(this@RechargeFragment)

        mTextInputLayoutRechargeMobileNumber =
            view.findViewById(R.id.textInputLayoutRechargeMobileNumber)
        mTextInputEditTextRechargeMobileNumber =
            view.findViewById(R.id.textInputEditTextRechargeMobileNumber)

        mAppCompatSpinnerRechargeLocation = view.findViewById(R.id.appCompatSpinnerRechargeLocation)
        mAppCompatSpinnerRechargeLocation.tag = getString(R.string.text_label_location)

        mTextInputLayoutRechargeSubscriptionId =
            view.findViewById(R.id.textInputLayoutRechargeSubscriptionId)
        mTextInputEditTextRechargeSubscriptionId =
            view.findViewById(R.id.textInputEditTextRechargeSubscriptionId)

        mButtonRechargeProceed = view.findViewById(R.id.buttonRechargeProceed)
        mButtonRechargeProceed.setOnClickListener(this@RechargeFragment)

        mContentLoadingProgressBarRecharge =
            view.findViewById(R.id.contentLoadingProgressBarRecharge)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
            mIsMobileRecharge = mSharedPreferenceUtils.getIsMobileRecharge(it)
        }
    }

    override fun onResume() {
        super.onResume()
        when {
            mIsMobileRecharge -> {
                mAppCompatSpinnerRechargeLocation.visibility = View.VISIBLE
                mTextInputLayoutRechargeSubscriptionId.visibility = View.GONE

                mContentLoadingProgressBarRecharge.show()
                onSelectItemFromSpinner()
                onLoadLocation()
            }
            else -> {
                mAppCompatSpinnerRechargeLocation.visibility = View.GONE
                mTextInputLayoutRechargeSubscriptionId.visibility = View.VISIBLE
                mButtonRechargeProceed.visibility = View.VISIBLE
            }
        }
        onLoadCompanyDetails()
    }

    override fun onDetach() {
        super.onDetach()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textViewRechargeChange -> view?.let {
                Navigation.findNavController(it).popBackStack()
            }
            R.id.buttonRechargeProceed -> {
                if (mIsMobileRecharge) {
                    isMobileRechargeValidated()
                } else {
                    isDTHRechargeValidated()
                }
            }
        }
    }

    private fun onSelectItemFromSpinner() {
        mAppCompatSpinnerRechargeLocation.onItemSelectedListener =
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
                    when (mAppCompatSpinnerRechargeLocation.selectedItemPosition) {
                        0 -> {
                            mButtonRechargeProceed.visibility = View.GONE
                        }
                        else -> {
                            mSharedPreferenceUtils.saveRechargeCircleCode(mSelectedLocationCode)
                            mButtonRechargeProceed.visibility = View.VISIBLE
                        }
                    }
                }
            }
    }

    private fun onLoadCompanyDetails() {
        context?.let {
            Glide.with(this@RechargeFragment)
                .load(mSharedPreferenceUtils.getRechargeCompanyLogo(it))
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(mAppCompatImageViewRechargeCompanyLogo)
            mTextViewRechargeCompanyName.text = mSharedPreferenceUtils.getRechargeCompanyName(it)
        }
    }

    private fun isMobileRechargeValidated() {
        when {
            !ValidationUtils.getValidationUtils()
                .isInputEditTextMobileFunc(
                    mTextInputLayoutRechargeMobileNumber,
                    mTextInputEditTextRechargeMobileNumber,
                    getString(R.string.text_error_mobile)
                ) -> return
            else -> {
                mSharedPreferenceUtils.saveRechargeMobileNumber(
                    mTextInputEditTextRechargeMobileNumber.text.toString().trim()
                )
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_recharge_to_rechargeDetails)
                }
            }
        }
    }

    private fun isDTHRechargeValidated() {
        when {
            !ValidationUtils.getValidationUtils()
                .isInputEditTextMobileFunc(
                    mTextInputLayoutRechargeMobileNumber,
                    mTextInputEditTextRechargeMobileNumber,
                    getString(R.string.text_error_mobile)
                ) -> return
            !ValidationUtils.getValidationUtils()
                .isInputEditTextFilledFunc(
                    mTextInputEditTextRechargeSubscriptionId,
                    mTextInputLayoutRechargeSubscriptionId,
                    getString(R.string.text_error_subscription_id)
                ) -> return
            else -> {
                mSharedPreferenceUtils.saveRechargeMobileNumber(
                    mTextInputEditTextRechargeMobileNumber.text.toString().trim()
                )
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
                                    mContentLoadingProgressBarRecharge.hide()

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
                                            mAppCompatSpinnerRechargeLocation.adapter =
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
                                mContentLoadingProgressBarRecharge.hide()
                            }
                        })
                }
                else -> {
                    mContentLoadingProgressBarRecharge.hide()
                    AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
                }
            }
        }
    }
}