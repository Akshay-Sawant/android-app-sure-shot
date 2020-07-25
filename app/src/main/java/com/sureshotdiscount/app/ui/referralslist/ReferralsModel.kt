package com.sureshotdiscount.app.ui.referralslist

import com.squareup.moshi.Json

class ReferralsModel(
    @field:Json(name = "referralId") val mReferralId: String,
    @field:Json(name = "referralLevel") val mReferralLevel: String,
    @field:Json(name = "referralAmount") val mReferralAmount: String
)