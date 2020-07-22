package com.sureshotdiscount.app.data.model

import com.squareup.moshi.Json

class MobileRechargeModel(
    @field:Json(name = "companyId") val mCompanyId: String,
    @field:Json(name = "companyName") val mCompanyName: String,
    @field:Json(name = "companyLogoUrl") val mCompanyLogoUrl: Int
)
