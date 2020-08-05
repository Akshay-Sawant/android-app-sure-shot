package com.sureshotdiscount.app.ui.rechargeHistory

import com.squareup.moshi.Json

class RechargeHistoryDetailsModel(
    @field:Json(name = "id") val mId: String,
    @field:Json(name = "mobileNumber") val mMobileNumber: String,
    @field:Json(name = "rechargeAmount") val mRechargeAmount: String,
    @field:Json(name = "rechargeDate") val mRechargeDate: String,
    @field:Json(name = "paymentMode") val mPaymentMode: String,
    @field:Json(name = "referenceNumber") val mReferenceNumber: String
)