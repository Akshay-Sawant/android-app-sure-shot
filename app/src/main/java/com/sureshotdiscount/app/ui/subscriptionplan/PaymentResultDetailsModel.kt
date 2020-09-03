package com.sureshotdiscount.app.ui.subscriptionplan

import com.squareup.moshi.Json

class PaymentResultDetailsModel(
    @field:Json(name = "paymentStatus") val mPaymentStatus: Boolean,
    @field:Json(name = "rechargeFor") val mRechargeFor: String,
    @field:Json(name = "mobileNumber") val mMobileNumber: String,
    @field:Json(name = "orderId") val mOrderId: String
)