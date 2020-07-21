package com.sureshotdiscount.app.utils.server

import java.io.IOException

/**
 * Created by Innovins-21 on 031, 31-Jan-19, 10:41 AM.<br></br>
 * thrown when response status is 401 Unauthorized (RFC 7235)<br></br>
 * when this exception is thrown, call `Login.startLoginFlow(context);`
 */

class Server401ResponseException : IOException()
