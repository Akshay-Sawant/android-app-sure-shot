package com.sureshotdiscount.app.ui.referralslist

import com.squareup.moshi.Json

class ReferralsListModel(
    @field:Json(name = "balanceEarnings") val mBalanceEarnings: String,
    @field:Json(name = "referrals") val mReferrals: List<LevelsDetailsModel>
)