package com.sureshotdiscount.app.ui.referralslist

import com.squareup.moshi.Json

class LevelsModel(
    @field:Json(name = "levelId") val mLevelId: String,
    @field:Json(name = "unsubscribed") val mUnsubscribed: String,
    @field:Json(name = "subscribed") val mSubscribed: String,
    @field:Json(name = "levelsDetails") val mLevelsDetails: List<LevelsDetailsModel>
)