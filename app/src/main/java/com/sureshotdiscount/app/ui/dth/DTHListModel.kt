package com.sureshotdiscount.app.ui.dth

import com.squareup.moshi.Json

class DTHListModel(
    @field:Json(name = "companyId") val mCompanyId: String,
    @field:Json(name = "companyName") val mCompanyName: String,
    @field:Json(name = "companyCode") val mCompanyCode: String,
    @field:Json(name = "companyLogoUrl") val mCompanyLogoUrl: String
)