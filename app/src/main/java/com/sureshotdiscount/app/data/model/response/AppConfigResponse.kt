package com.sureshotdiscount.app.data.model.response

import com.squareup.moshi.Json

class AppConfigResponse(@field:Json(name = "latestVersion") val latestVersion: Int)