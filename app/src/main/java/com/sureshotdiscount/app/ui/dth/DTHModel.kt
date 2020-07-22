package com.sureshotdiscount.app.ui.dth

import com.squareup.moshi.Json

class DTHModel(
    @field:Json(name = "companyId") val mCompanyId: String,
    @field:Json(name = "companyName") val mCompanyName: String,
    @field:Json(name = "companyLogoUrl") val mCompanyLogoUrl: Int
)