package com.sureshotdiscount.app.ui.rechargedetails

import com.squareup.moshi.Json

class InitiateRechargeModel(
    @field:Json(name = "data") val mInitiateRechargeDetailsModel: InitiateRechargeDetailsModel,
    @field:Json(name = "status") val mStatus: Boolean,
    @field:Json(name = "status_code") val mStatusCode: Int,
    @field:Json(name = "title") val mTitle: String,
    @field:Json(name = "message") val mMessage: String
)