package com.sureshotdiscount.app.ui.aboutus

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

class AboutUsFragment : Fragment(R.layout.fragment_about_us) {

    private lateinit var mTextViewAboutUsMessage: TextView
    private lateinit var mWebViewAboutUs: WebView

    private lateinit var mContentLoadingProgressBarAboutUs: ContentLoadingProgressBar

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewAboutUsMessage = view.findViewById(R.id.textViewAboutUsMessage)
        mWebViewAboutUs = view.findViewById(R.id.webViewAboutUs)

        mContentLoadingProgressBarAboutUs = view.findViewById(R.id.contentLoadingProgressBarAboutUs)
    }

    override fun onResume() {
        super.onResume()
        mContentLoadingProgressBarAboutUs.show()
        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
        onLoadWebView()
    }

    private fun onLoadWebView() {
        context?.let {
            mWebViewAboutUs.webViewClient = object : WebViewClient() {

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    mWebViewAboutUs.loadData(
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
            onLoadAboutUs()
        }
    }

    private fun onLoadAboutUs() {
        context?.let {
            if (APIClient.isNetworkConnected(it)) {
                APIClient.apiInterface
                    .aboutUs(
                        mSharedPreferenceUtils.getLoggedInUser().loginToken
                    )
                    .enqueue(object : Callback<AboutUsModel> {
                        override fun onResponse(
                            call: Call<AboutUsModel>,
                            response: Response<AboutUsModel>
                        ) {
                            if (response.isSuccessful) {
                                val mAboutUsModel: AboutUsModel? = response.body()
                                mContentLoadingProgressBarAboutUs.hide()

                                if (mAboutUsModel != null) {
                                    if (mAboutUsModel.mStatus) {
                                        mTextViewAboutUsMessage.visibility =
                                            View.VISIBLE
                                        mTextViewAboutUsMessage.text =
                                            mAboutUsModel.mMessage
                                        mWebViewAboutUs.visibility = View.GONE
                                    } else {
                                        mTextViewAboutUsMessage.visibility =
                                            View.GONE
                                        mWebViewAboutUs.visibility = View.VISIBLE

                                        mWebViewAboutUs.loadUrl(
                                            mAboutUsModel.mLink
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

                        override fun onFailure(call: Call<AboutUsModel>, t: Throwable) {
                            ErrorUtils.parseOnFailureException(
                                it,
                                call,
                                t
                            )
                            mContentLoadingProgressBarAboutUs.hide()
                        }
                    })
            } else {
                mContentLoadingProgressBarAboutUs.hide()
                AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
            }
        }
    }
}