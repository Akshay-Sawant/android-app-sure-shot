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

    @field:Json(name = "referralid")
    var referralid: String = ""

    @field:Json(name = "status")
    var status: Boolean = false

    @field:Json(name = "status_code")
    var status_code: Int = 0

    @field:Json(name = "title")
    var title: String = ""

    @field:Json(name = "message")
    var message: String = ""

    /*@field:Json(name = "userId")
    var userId: String = ""

    @field:Json(name = "userName")
    var userName: String = ""

    @field:Json(name = "fullName")
    var fullName: String = ""

    *//*@field:Json(name = "firstName")
    var firstName: String = ""

    @field:Json(name = "lastName")
    var lastName: String = ""*//*

    @field:Json(name = "countryCode")
    var countryCode: String = ""

    @field:Json(name = "mobile")
    var mobile: String = ""

    @field:Json(name = "isMobileVerified")
    var isMobileVerified: Boolean = false

    @field:Json(name = "alternateMobileCountryCode")
    var alternateMobileCountryCode: String = ""

    @field:Json(name = "alternateMobile")
    var alternateMobile: String = ""

    @field:Json(name = "isAlternateMobileVerified")
    var isAlternateMobileVerified: Boolean = false

    @field:Json(name = "email")
    var email: String = ""

    @field:Json(name = "isEmailVerified")
    var isEmailVerified: Boolean = false

    @field:Json(name = "profileImage")
    var profileImage: String = ""

    @field:Json(name = "referralId")
    var referralId: String = ""

    var isSocialLogin: Boolean = false*/
}