package com.sureshotdiscount.app.ui.rechargeHistory

import com.squareup.moshi.Json

class RechargeHistoryDetailsModel(
    @field:Json(name = "referenceNumber") val mReferenceNumber: String,
    @field:Json(name = "rechargeDate") val mRechargeDate: String,
    @field:Json(name = "mobileNumber") val mMobileNumber: String,
    @field:Json(name = "rechargeAmount") val mRechargeAmount: String,
    @field:Json(name = "transactionId") val mTransactionId: String,
    @field:Json(name = "paymentMode") val mPaymentMode: String,
    @field:Json(name = "recharge_status") val mRechargeStatus: String,
    @field:Json(name = "recharge_code") val mRechargeCode: Long,
    @field:Json(name = "operator") val mOperator: String,
    @field:Json(name = "circle") val mCircle: String,
    @field:Json(name = "type") val mType: String,
    @field:Json(name = "type_code") val mTypeCode: Long
)