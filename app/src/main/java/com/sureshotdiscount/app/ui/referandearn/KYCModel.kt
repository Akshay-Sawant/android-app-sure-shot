package com.sureshotdiscount.app.ui.referandearn

import com.squareup.moshi.Json

class KYCModel(
    @field:Json(name = "status") val mStatus: Boolean,
    @field:Json(name = "data") val mKYCDetailsModel: KYCDetailsModel,
    @field:Json(name = "status_code") val mStatusCode: Int,
    @field:Json(name = "title") val mTitle: String,
    @field:Json(name = "message") val mMessage: String
)