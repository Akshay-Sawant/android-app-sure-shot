package com.sureshotdiscount.app.ui.rechargeHistory

import com.squareup.moshi.Json

class RechargeHistoryModel(
    @field:Json(name = "status") val mStatus: Boolean,
    @field:Json(name = "status_code") val mStatusCode: Int,
    @field:Json(name = "title") val mTitle: String,
    @field:Json(name = "message") val mMessage: String,
    @field:Json(name = "response") val mResponse: List<RechargeHistoryDetailsModel>
)