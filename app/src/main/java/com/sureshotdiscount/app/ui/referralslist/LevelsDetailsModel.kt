package com.sureshotdiscount.app.ui.referralslist

import com.squareup.moshi.Json

class LevelsDetailsModel(
    @field:Json(name = "id") val mId: Int,
    @field:Json(name = "mobile_no") val mMobileNo: String,
    @field:Json(name = "status") val mStatus: String
)