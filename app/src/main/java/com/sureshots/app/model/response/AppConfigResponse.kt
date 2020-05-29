package com.sureshots.app.model.response

import com.squareup.moshi.Json

class AppConfigResponse(@field:Json(name = "latestVersion") val latestVersion: Int)