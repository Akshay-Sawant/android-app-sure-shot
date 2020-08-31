package com.sureshotdiscount.app.ui.subscriptionplan

import com.squareup.moshi.Json

class BenefitsOfSubscriptionModel(
    @field:Json(name = "status") val mStatus: Boolean,
    @field:Json(name = "status_code") val mStatusCode: Int,
    @field:Json(name = "data") val mData: BenefitsOfSubscriptionDetailsModel,
    @field:Json(name = "title") val mTitle: String,
    @field:Json(name = "message") val message: String
)