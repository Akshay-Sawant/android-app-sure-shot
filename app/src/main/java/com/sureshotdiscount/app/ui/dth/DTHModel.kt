package com.sureshotdiscount.app.ui.dth

import com.squareup.moshi.Json

class DTHModel(
    @field:Json(name = "response") val mResponse: List<DTHListModel>,
    @field:Json(name = "status") val mStatus: Boolean,
    @field:Json(name = "status_code") val mStatusCode: Int,
    @field:Json(name = "title") val mTitle: String,
    @field:Json(name = "message") val mMessage: String
)