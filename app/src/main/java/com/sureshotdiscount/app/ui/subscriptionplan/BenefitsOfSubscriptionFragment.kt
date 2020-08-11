package com.sureshotdiscount.app.ui.subscriptionplan

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class BenefitsOfSubscriptionFragment : Fragment(R.layout.fragment_benefits_of_subscription),
    View.OnClickListener {

    private lateinit var mTextViewBenefitsOfSubscriptionMessage: TextView
    private lateinit var mWebViewBenefitsOfSubscription: WebView
    private lateinit var mButtonBenefitsOfSubscriptionPayToSubscribeNow: Button

    private lateinit var mContentLoadingProgressBarBenefitsOfSubscription: ContentLoadingProgressBar

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewBenefitsOfSubscriptionMessage =
            view.findViewById(R.id.textViewBenefitsOfSubscriptionMessage)

        mWebViewBenefitsOfSubscription =
            view.findViewById(R.id.webViewBenefitsOfSubscription)
        mWebViewBenefitsOfSubscription.settings.lightTouchEnabled = true
        mWebViewBenefitsOfSubscription.settings.javaScriptEnabled = true
        mWebViewBenefitsOfSubscription.settings.setGeolocationEnabled(true)
        mWebViewBenefitsOfSubscription.isSoundEffectsEnabled = true

        mButtonBenefitsOfSubscriptionPayToSubscribeNow =
            view.findViewById(R.id.buttonBenefitsOfSubscriptionPayToSubscribeNow)
        mButtonBenefitsOfSubscriptionPayToSubscribeNow.setOnClickListener(this)

        mContentLoadingProgressBarBenefitsOfSubscription =
            view.findViewById(R.id.contentLoadingProgressBarBenefitsOfSubscription)
    }

    override fun onResume() {
        super.onResume()
        mContentLoadingProgressBarBenefitsOfSubscription.show()
        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
        onLoadWebView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonBenefitsOfSubscriptionPayToSubscribeNow -> view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_benefitsOfSubscription_to_paymentSuccessful)
            }
        }
    }

    private fun onLoadWebView() {
        context?.let {
            mWebViewBenefitsOfSubscription.webViewClient = object : WebViewClient() {

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    mWebViewBenefitsOfSubscription.loadData(
                        getString(R.string.text_error_unable_to_load),
                        "text/html",
                        "UTF-8"
                    )
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url?.startsWith("tel")!!) {
                        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(url)))
                        return true
                    } else if (url.startsWith("mailto")) {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "message/rfc822"
                        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(url.substring(7, url.length)))
                        startActivity(Intent.createChooser(intent, "Send Email"))
                        return true
                    } else if (url.startsWith("http") || url.startsWith("https")) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(Intent.createChooser(intent, "Open web page with"))
                        return true
                    } else {
                        return super.shouldOverrideUrlLoading(view, url)
                    }
                }
            }
            onLoadBenefitOfSubscription()
        }
    }

    private fun onLoadBenefitOfSubscription() {
        context?.let {
            if (APIClient.isNetworkConnected(it)) {
                APIClient.apiInterface
                    .getSubscriptionPlan(
                        mSharedPreferenceUtils.getLoggedInUser().loginToken
                    )
                    .enqueue(object : Callback<SubscriptionPlanModel> {
                        override fun onResponse(
                            call: Call<SubscriptionPlanModel>,
                            response: Response<SubscriptionPlanModel>
                        ) {
                            if (response.isSuccessful) {
                                val mSubscriptionPlanModel: SubscriptionPlanModel? = response.body()
                                mContentLoadingProgressBarBenefitsOfSubscription.hide()

                                if (mSubscriptionPlanModel != null) {
                                    if (mSubscriptionPlanModel.mStatus) {
                                        mTextViewBenefitsOfSubscriptionMessage.visibility =
                                            View.VISIBLE
                                        mWebViewBenefitsOfSubscription.visibility = View.GONE
                                        mButtonBenefitsOfSubscriptionPayToSubscribeNow.visibility =
                                            View.GONE
                                    } else {
                                        mTextViewBenefitsOfSubscriptionMessage.visibility =
                                            View.GONE
                                        mWebViewBenefitsOfSubscription.visibility = View.VISIBLE
                                        mButtonBenefitsOfSubscriptionPayToSubscribeNow.visibility =
                                            View.VISIBLE

                                        mWebViewBenefitsOfSubscription.loadUrl(
                                            mSubscriptionPlanModel.mLink
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

                        override fun onFailure(call: Call<SubscriptionPlanModel>, t: Throwable) {
                            ErrorUtils.parseOnFailureException(
                                it,
                                call,
                                t
                            )
                            mContentLoadingProgressBarBenefitsOfSubscription.hide()
                        }
                    })
            } else {
                mContentLoadingProgressBarBenefitsOfSubscription.hide()
                AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
            }
        }
    }
}