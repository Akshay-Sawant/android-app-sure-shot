package com.sureshotdiscount.app.ui.subscriptionplan

import com.squareup.moshi.Json

class SubscriptionPlanModel(
    @field:Json(name = "subscriptionAmount") val mSubscriptionAmount: String,
    @field:Json(name = "subscriptionExpiryDate") val mSubscriptionExpiryDate: String
)