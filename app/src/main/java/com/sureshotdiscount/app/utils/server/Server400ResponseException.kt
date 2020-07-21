package com.sureshotdiscount.app.utils.server

import com.sureshotdiscount.app.data.model.response.APIErrorResponse
import java.io.IOException

/**
 * Created by Innovins-21 on 031, 31-Jan-19, 12:08 PM.<br></br>
 * Bad Request
 */
class Server400ResponseException internal constructor(val apiErrorResponse: APIErrorResponse?) : IOException()
