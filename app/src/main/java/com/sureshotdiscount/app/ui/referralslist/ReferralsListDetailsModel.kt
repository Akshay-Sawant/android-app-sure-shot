package com.sureshotdiscount.app.ui.referralslist

import com.squareup.moshi.Json

class ReferralsListDetailsModel(
    @field:Json(name = "balanceEarnings") val mBalanceEarnings: Int,
    @field:Json(name = "referrals") val mReferrals: List<ReferralsModel>
)