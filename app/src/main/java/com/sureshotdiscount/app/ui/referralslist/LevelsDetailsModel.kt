package com.sureshotdiscount.app.ui.referralslist

import com.squareup.moshi.Json

class LevelsDetailsModel(
    @field:Json(name = "id") val mId: String,
    @field:Json(name = "contactNumber") val mContactNumber: String,
    @field:Json(name = "status") val mStatus: String
)