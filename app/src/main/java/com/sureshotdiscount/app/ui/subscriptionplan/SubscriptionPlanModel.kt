package com.sureshotdiscount.app.ui.subscriptionplan

import com.squareup.moshi.Json

class SubscriptionPlanModel(
    @field:Json(name = "status") val mStatus: Boolean,
    @field:Json(name = "subscriptionAmount") val mSubscriptionAmount: Int,
    @field:Json(name = "message") val mMessage: String,
    @field:Json(name = "title") val mTitle: String,
    @field:Json(name = "link") val mLink: String,
    @field:Json(name = "status_code") val mStatusCode: Int,
    @field:Json(name = "ref_code") val mReferenceCode: String,
    @field:Json(name = "response") val mResponse: SubscriptionPlanDetailsModel
)