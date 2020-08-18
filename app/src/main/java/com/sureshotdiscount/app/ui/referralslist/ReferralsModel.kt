package com.sureshotdiscount.app.ui.referralslist

import com.squareup.moshi.Json

class ReferralsModel(
    @field:Json(name = "referralId") val mReferralId: Int,
    @field:Json(name = "referralLevel") val mReferralLevel: Int,
    @field:Json(name = "referralAmount") val mReferralAmount: Int
)