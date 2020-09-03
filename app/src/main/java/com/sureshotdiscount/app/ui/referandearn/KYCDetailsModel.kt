package com.sureshotdiscount.app.ui.referandearn

import com.squareup.moshi.Json

class KYCDetailsModel(
    @field:Json(name = "kycStatus") val mKycStatus: Boolean,
    @field:Json(name = "referralCode") val mReferralCode: String,
    @field:Json(name = "shareNow") val mShareNow: String
)