package com.sureshotdiscount.app.ui.referralslist

import com.squareup.moshi.Json

class ReferralsListModel(
    @field:Json(name = "response") val mResponse: ReferralsListDetailsModel,
    @field:Json(name = "status") val mStatus: Boolean,
    @field:Json(name = "status_code") val mStatusCode: Int,
    @field:Json(name = "title") val mTitle: String,
    @field:Json(name = "message") val mMessage: String
)