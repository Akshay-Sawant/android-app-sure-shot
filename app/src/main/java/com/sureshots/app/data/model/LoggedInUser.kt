package com.sureshots.app.data.model

import com.squareup.moshi.Json
import java.io.Serializable

class LoggedInUser : Serializable {

    @field:Json(name = "loginToken")
    var loginToken: String = ""

    @field:Json(name = "userId")
    var userId: String = ""

    @field:Json(name = "userName")
    var userName: String = ""

    @field:Json(name = "fullName")
    var fullName: String = ""

    /*@field:Json(name = "firstName")
    var firstName: String = ""

    @field:Json(name = "lastName")
    var lastName: String = ""*/

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

    @field:Json(name = "referenceCode")
    var referenceCode: String = ""

    var isSocialLogin: Boolean = false
}