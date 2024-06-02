package com.uaer.app.Utils.ResponseAdvice

data class GlobleRespons<T>(
    var data: T,
    var status: StatusResponse
)
