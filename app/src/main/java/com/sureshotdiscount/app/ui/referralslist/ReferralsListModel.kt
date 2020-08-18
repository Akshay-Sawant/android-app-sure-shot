package com.sureshotdiscount.app.ui.referralslist

import com.squareup.moshi.Json

class ReferralsListModel(
    @field:Json(name = "status") val mStatus: Boolean,
    @field:Json(name = "subscriptionAmount") val mSubscriptionAmount: String,
    @field:Json(name = "message") val mMessage: String,
    @field:Json(name = "title") val mTitle: String,
    @field:Json(name = "link") val mLink: String,
    @field:Json(name = "status_code") val mStatusCode: Int,
    @field:Json(name = "ref_code") val mRefCode: String,
    @field:Json(name = "balanceEarnings")val mBalanceEarnings:Int,
    @field:Json(name = "referrals") val mReferrals: List<ReferralsModel>
)