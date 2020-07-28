package com.sureshotdiscount.app.ui.mobile

import com.squareup.moshi.Json

class MobileRechargeListModel(
    @field:Json(name = "companyId") val mCompanyId: String,
    @field:Json(name = "companyName") val mCompanyName: String,
    @field:Json(name = "companyCode") val mCompanyCode: String,
    @field:Json(name = "companyLogoUrl") val mCompanyLogoUrl: String
)