package com.sureshotdiscount.app.ui.referralslist

import com.squareup.moshi.Json

class LevelsModel(
    @field:Json(name = "status") val mStatus: Boolean,
    @field:Json(name = "message") val mMessage: String,
    @field:Json(name = "title") val mTitle: String,
    @field:Json(name = "status_code") val mStatusCode: Int,
    @field:Json(name = "level_name") val mLevelName: String,
    @field:Json(name = "subscribed") val mSubscribed: Int,
    @field:Json(name = "unsubscribed") val mUnsubscribed: Int,
    @field:Json(name = "response") val mResponse: List<LevelsDetailsModel>
)