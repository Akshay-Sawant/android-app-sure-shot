package com.sureshotdiscount.app.ui.logout

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.data.model.response.APIActionResponse
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogoutFragment : DialogFragment(), View.OnClickListener {

    private lateinit var mTextViewLogoutCancel: TextView
    private lateinit var mTextViewLogoutConfirm: TextView

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewLogoutCancel = view.findViewById(R.id.textViewLogoutCancel)
        mTextViewLogoutCancel.setOnClickListener(this@LogoutFragment)

        mTextViewLogoutConfirm = view.findViewById(R.id.textViewLogoutConfirm)
        mTextViewLogoutConfirm.setOnClickListener(this@LogoutFragment)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (arguments != null) {
            if (requireArguments().getBoolean("notAlertDialog")) {
                return super.onCreateDialog(savedInstanceState)
            }
        }

        val builder: AlertDialog.Builder? = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Alert")
        builder?.setMessage("Are you sure you want to exit from this app?")
        builder?.setPositiveButton(
            getString(android.R.string.ok)
        ) { dialog, _ ->
            dialog.dismiss()

        }
        builder?.setNegativeButton(
            getText(android.R.string.cancel)
        ) { _, _ -> dismiss() }

        builder?.create()!!
        return builder.show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textViewLogoutCancel -> {
                dialog?.dismiss()
            }
            R.id.textViewLogoutConfirm -> {
                onClickLogout()
                dialog?.dismiss()
            }
        }
    }

    private fun onClickLogout() {
        context?.let { it ->
            when {
                APIClient.isNetworkConnected(it) -> {
                    val call: Call<APIActionResponse> =
                        APIClient.apiInterface.doLogout(mSharedPreferenceUtils.getLoggedInUser().loginToken)
                    call.enqueue(object : Callback<APIActionResponse> {

                        override fun onResponse(
                            call: Call<APIActionResponse>,
                            response: Response<APIActionResponse>
                        ) {
                            if (response.isSuccessful) {
                                val apiActionResponse: APIActionResponse? = response.body()
                                if (apiActionResponse != null) {
                                    if (apiActionResponse.isActionSuccess) {
                                        mSharedPreferenceUtils.doLogout(it)
                                        view?.let {
                                            Navigation.findNavController(it)
                                                .popBackStack(R.id.signUpFragment, false)
                                        }
                                    } else {
                                        AlertDialogUtils.getInstance().showAlert(
                                            it,
                                            R.drawable.ic_warning_black,
                                            apiActionResponse.title,
                                            apiActionResponse.message,
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
                                                "\nResponse: " + response.toString(), null
                                    )
                                }
                            }
                        }

                        override fun onFailure(call: Call<APIActionResponse>, t: Throwable) {
                            ErrorUtils.parseOnFailureException(it, call, t)
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