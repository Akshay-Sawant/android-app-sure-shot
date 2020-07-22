package com.sureshotdiscount.app.ui.mobile

import com.squareup.moshi.Json

class MobileRechargeModel(
    @field:Json(name = "companyId") val mCompanyId: String,
    @field:Json(name = "companyName") val mCompanyName: String,
    @field:Json(name = "companyLogoUrl") val mCompanyLogoUrl: Int
)
