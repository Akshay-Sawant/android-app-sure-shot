package com.sureshotdiscount.app.ui.signin

import com.squareup.moshi.Json
import com.sureshotdiscount.app.data.model.LoggedInUser

class SignInModel(
    @field:Json(name = "status") val mStatus: Boolean,
    @field:Json(name = "status_code") val mStatusCode: Int,
    @field:Json(name = "data") val mLoggedInUser: LoggedInUser,
    @field:Json(name = "message") val mMessage: String,
    @field:Json(name = "title") val mTitle: String
)