package com.sureshotdiscount.app.data.model.response

import com.squareup.moshi.Json

class APIActionResponse {

    @field:Json(name = "status")
    val isActionSuccess: Boolean = false

    @field:Json(name = "status_code")
    val status_code: Int = 0

    @field:Json(name = "title")
    val title: String = "Server busy!"

    @field:Json(name = "message")
    val message: String =
        "We are unable to process the request at the moment, please try again after a while"
}