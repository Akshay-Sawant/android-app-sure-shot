package com.sureshotdiscount.app.ui.subscriptionplan

import com.squareup.moshi.Json

class BenefitsOfSubscriptionDetailsModel(
    @field:Json(name = "order_id") val mOrderId: String,
    @field:Json(name = "customer_id") val mCustomerId: String,
    @field:Json(name = "salt") val mSalt: String,
    @field:Json(name = "merchant_trxnId") val mMerchantTrxnId: String,
    @field:Json(name = "merchant_payment_amount") val mMerchantPaymentAmount: String,
    @field:Json(name = "merchant_productInfo") val mMerchantProductInfo: String,
    @field:Json(name = "customer_firstName") val mCustomerFirstName: String,
    @field:Json(name = "customer_email_id") val mCustomerEmailId: String,
    @field:Json(name = "customer_phone") val customer_phone: String,
    @field:Json(name = "merchant_key") val mMerchantKey: String,
    @field:Json(name = "customers_unique_id") val mCustomersUniqueId: String,
    @field:Json(name = "unique_ref_no") val mUniqueReferenceNo: String
)