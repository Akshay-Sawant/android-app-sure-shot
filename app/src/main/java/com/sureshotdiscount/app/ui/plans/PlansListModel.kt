package com.sureshotdiscount.app.ui.plans

import com.squareup.moshi.Json

class PlansListModel(
    @field:Json(name = "plan_id") val mPlanId: String,
    @field:Json(name = "operator_code") val mOperatorCode: String,
    @field:Json(name = "operator_name") val mOperatorName: String,
    @field:Json(name = "circle_code") val mCircleCode: String,
    @field:Json(name = "circle_name") val mCircleName: String,
    @field:Json(name = "plan_type") val mPlanType: String,
    @field:Json(name = "amount") val mAmount: String,
    @field:Json(name = "talktime") val mTalktime: String,
    @field:Json(name = "validity") val mValidity: String,
    @field:Json(name = "description") val mDescription: String,
    @field:Json(name = "created_on") val mCreatedOn: String,
    @field:Json(name = "updated_on") val mUpdatedOn: String
)