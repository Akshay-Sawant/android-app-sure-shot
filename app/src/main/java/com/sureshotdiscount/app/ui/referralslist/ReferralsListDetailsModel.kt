package com.sureshotdiscount.app.ui.referralslist

import com.squareup.moshi.Json

class ReferralsListDetailsModel(
    @field:Json(name = "balanceEarnings") val mBalanceEarnings: String,
    @field:Json(name = "referrals") val mReferrals: List<ReferralsModel>
)