package com.sureshotdiscount.app.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.widget.TextView

const val DEVELOPMENT_URL = "http://shareittofriends.com/"
const val PRODUCTION_URL = "http://15.207.47.140/"

const val MIDDLE_URL = DEVELOPMENT_URL + "demo/sureshotdiscount/"
const val PRODUCTION_MIDDLE_URL = PRODUCTION_URL + "appApi/"

const val AUTH = "auth"
const val RECHARGE_DATA = "recharge_data"
const val D2H_DATA = "d2h_data"
const val SUBSCRIPTION = "subscription"
const val CONTACT_US = "contact_us"
const val REFERRAL = "referral"
const val CMS = "cms"

const val API_VERSION = "demo/sureshotdiscount/auth" // api version dir, do not add / in the end
// const val API_VERSION = "app/v1" // api version dir, do not add / in the end

const val IS_DEBUG_ON = true // TODO: false on live

const val INTENT_EXTRA_OPEN_AS_EDIT_FORM = "INTENT_EXTRA_OPEN_AS_EDIT_FORM"
const val INTENT_EXTRA_SELECTED_ROW: String = "INTENT_EXTRA_SELECTED_ROW"
// const val INTENT_STARTED_BY_MAIN_ACTIVITY = "started_by_main_activity"
// const val INTENT_KEY_SWIPE_TAB_TO_NEXT_SCREEN = "INTENT_KEY_SWIPE_TAB_TO_NEXT_SCREEN"

const val REQUEST_CODE_PICK_IMAGE_PERMISSION = 10001
const val REQUEST_CODE_CAPTURE_IMAGE_PERMISSION = 10002
// const val REQUEST_CODE_TURN_ON_LOCATION_PERMISSION = 10003

const val REQUEST_CODE_PICK_IMAGE = 10011
const val REQUEST_CODE_CAPTURE_IMAGE = 10012
// const val REQUEST_CODE_GET_LOCATION = 10013


class Constant

fun onDecorateText(mText: String, mStart: Int, mTextView: TextView, mColor: Int) {
    val mSpannable: Spannable = SpannableString(mText)
    mSpannable.setSpan(
        ForegroundColorSpan(mColor), mStart, mText.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    mSpannable.setSpan(UnderlineSpan(), mStart, mText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    mTextView.text = mSpannable
}