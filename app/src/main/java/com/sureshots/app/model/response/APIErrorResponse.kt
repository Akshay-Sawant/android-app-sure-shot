package com.sureshots.app.model.response

import com.squareup.moshi.Json

/**
 * Created by Innovins-21 on 031, 31-Jan-19, 10:55 AM.
 */

class APIErrorResponse {

    @field:Json(name = "title")
    val title: String = "Server busy!"

    @field:Json(name = "message")
    val message: String = "We are unable to process the request at the moment, please try again after a while"
}
