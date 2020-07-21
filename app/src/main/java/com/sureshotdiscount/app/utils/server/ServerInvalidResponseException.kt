package com.sureshotdiscount.app.utils.server

import java.io.IOException

/**
 * Created by Innovins-21 on 031, 31-Jan-19, 12:10 PM.
 */
class ServerInvalidResponseException(message: String) : IOException(message) {
    companion object {

        /**
         * server returned 500
         */
        const val ERROR_500_RESPONSE = "ERROR_500_RESPONSE"

        /**
         * server returned 400 with a blank response :/
         */
        const val ERROR_400_BLANK_RESPONSE = "ERROR_400_BLANK_RESPONSE"

        /**
         * unexpected status code returned
         */
        const val ERROR_UNEXPECTED_STATUS_CODE = "ERROR_UNEXPECTED_STATUS_CODE"

        /**
         * server returned 200 with a blank response :/
         */
        const val ERROR_200_BLANK_RESPONSE = "ERROR_200_BLANK_RESPONSE"
    }
}
