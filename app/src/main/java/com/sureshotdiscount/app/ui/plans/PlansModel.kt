package com.sureshotdiscount.app.ui.plans

import com.squareup.moshi.Json

class PlansModel(
    @field:Json(name = "response") val mResponse: List<PlansListModel>,
    @field:Json(name = "status") val mStatus: Boolean,
    @field:Json(name = "status_code") val mStatusCode: Int,
    @field:Json(name = "title") val mTitle: String,
    @field:Json(name = "message") val mMessage: String
)