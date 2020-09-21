package com.sureshotdiscount.app.ui.privacypolicy

import com.squareup.moshi.Json

class PrivacyAndPolicyModel(
    @field:Json(name = "status") val mStatus: Boolean,
    @field:Json(name = "message") val mMessage: String,
    @field:Json(name = "title") val mTitle: String,
    @field:Json(name = "link") val mLink: String
)