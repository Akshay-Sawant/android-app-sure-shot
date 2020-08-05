package com.sureshotdiscount.app.ui.subscriptionplan

import com.squareup.moshi.Json

class SubscriptionPlanDetailsModel(
    @field:Json(name = "subscriptionAmount") val mSubscriptionAmount: String,
    @field:Json(name = "subscriptionExpiryDate") val mSubscriptionExpiryDate: String
)