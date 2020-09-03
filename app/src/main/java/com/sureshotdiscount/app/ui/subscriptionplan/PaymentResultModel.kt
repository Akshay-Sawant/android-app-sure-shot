package com.sureshotdiscount.app.ui.subscriptionplan

import com.squareup.moshi.Json

class PaymentResultModel(
    @field:Json(name = "status") val mStatus: Boolean,
    @field:Json(name = "data") val mPaymentResultDetailsModel: PaymentResultDetailsModel,
    @field:Json(name = "status_code") val mStatusCode: Int,
    @field:Json(name = "title") val mTitle: String,
    @field:Json(name = "message") val mMessage: String
)