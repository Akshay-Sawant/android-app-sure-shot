package com.sureshotdiscount.app.ui.privacypolicy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrivacyAndPolicyFragment : Fragment(R.layout.fragment_privacy_and_policy) {

    private lateinit var mTextViewPrivacyAndPolicyMessage: TextView
    private lateinit var mWebViewPrivacyAndPolicy: WebView

    private lateinit var mContentLoadingProgressBarPrivacyAndPolicy: ContentLoadingProgressBar

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewPrivacyAndPolicyMessage = view.findViewById(R.id.textViewPrivacyAndPolicyMessage)
        mWebViewPrivacyAndPolicy = view.findViewById(R.id.webViewPrivacyAndPolicy)

        mContentLoadingProgressBarPrivacyAndPolicy =
            view.findViewById(R.id.contentLoadingProgressBarPrivacyAndPolicy)
    }

    override fun onResume() {
        super.onResume()
        mContentLoadingProgressBarPrivacyAndPolicy.show()
        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
        onLoadWebView()
    }

    private fun onLoadWebView() {
        context?.let {
            mWebViewPrivacyAndPolicy.webViewClient = object : WebViewClient() {

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    mWebViewPrivacyAndPolicy.loadData(
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
            onLoadPrivacyAndPolicy()
        }
    }

    private fun onLoadPrivacyAndPolicy() {
        context?.let {
            if (APIClient.isNetworkConnected(it)) {
                APIClient.apiInterface
                    .privacyPolicy(
                        mSharedPreferenceUtils.getLoggedInUser().loginToken
                    )
                    .enqueue(object : Callback<PrivacyAndPolicyModel> {
                        override fun onResponse(
                            call: Call<PrivacyAndPolicyModel>,
                            response: Response<PrivacyAndPolicyModel>
                        ) {
                            if (response.isSuccessful) {
                                val mPrivacyAndPolicyModel: PrivacyAndPolicyModel? = response.body()
                                mContentLoadingProgressBarPrivacyAndPolicy.hide()

                                if (mPrivacyAndPolicyModel != null) {
                                    if (mPrivacyAndPolicyModel.mStatus) {
                                        mTextViewPrivacyAndPolicyMessage.visibility =
                                            View.VISIBLE
                                        mTextViewPrivacyAndPolicyMessage.text =
                                            mPrivacyAndPolicyModel.mMessage
                                        mWebViewPrivacyAndPolicy.visibility = View.GONE
                                    } else {
                                        mTextViewPrivacyAndPolicyMessage.visibility =
                                            View.GONE
                                        mWebViewPrivacyAndPolicy.visibility = View.VISIBLE

                                        mWebViewPrivacyAndPolicy.loadUrl(
                                            mPrivacyAndPolicyModel.mLink
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

                        override fun onFailure(call: Call<PrivacyAndPolicyModel>, t: Throwable) {
                            ErrorUtils.parseOnFailureException(
                                it,
                                call,
                                t
                            )
                            mContentLoadingProgressBarPrivacyAndPolicy.hide()
                        }
                    })
            } else {
                mContentLoadingProgressBarPrivacyAndPolicy.hide()
                AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
            }
        }
    }
}