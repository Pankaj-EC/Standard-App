package com.uaer.app.Utils.ResponseAdvice

import java.io.Serializable

class StatusResponse:Serializable {

    var statusCode    = 0
    var statusMessage = ""
    var statusType    = Type.SUCCESS
    var hitCount      = 0

    enum class Type {
        ERROR, SUCCESS
    }

    constructor(errorCode: StatusCode,type: Type){
        statusCode    = errorCode.statusCode
        statusMessage = errorCode.statusMessage
        statusType    = type
    }
}