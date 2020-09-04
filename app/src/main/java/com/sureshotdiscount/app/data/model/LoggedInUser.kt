package com.sureshotdiscount.app.data.model

import com.squareup.moshi.Json
import java.io.Serializable

class LoggedInUser : Serializable {

    @field:Json(name = "userLoginToken")
    var loginToken: String = ""

    @field:Json(name = "id")
    var id: String = ""

    @field:Json(name = "name")
    var name: String = ""

    @field:Json(name = "mobileNumber")
    var mobileNumber: String = ""

    @field:Json(name = "emailid")
    var emailid: String = ""

    @field:Json(name = "referralCode")
    var referralCode: String = ""

    @field:Json(name = "status")
    var status: Boolean = false

    @field:Json(name = "status_code")
    var status_code: Int = 0

    @field:Json(name = "title")
    var title: String = ""

    @field:Json(name = "message")
    var message: String = ""

    @field:Json(name = "balanceEarnings")
    var balanceEarnings: String = "0"

    @field:Json(name = "isSubcribed")
    var isSubcribed: Boolean = false

    @field:Json(name = "isKYCDone")
    var isKYCDone:Boolean = false
}