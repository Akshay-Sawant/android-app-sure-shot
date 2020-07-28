package com.sureshotdiscount.app.ui.prepaid

import com.squareup.moshi.Json

class CircleListModel(
    @field:Json(name = "id") val mCircleId: String,
    @field:Json(name = "circle_name") val mCircleName: String,
    @field:Json(name = "circle_code") val mCircleCode: String
)