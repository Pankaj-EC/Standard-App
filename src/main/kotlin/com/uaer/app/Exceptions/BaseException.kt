package com.uaer.app.Exceptions

import com.uaer.app.Utils.ResponseAdvice.StatusCode
import com.uaer.app.Utils.ResponseAdvice.StatusResponse
import java.text.MessageFormat

open class BaseException: RuntimeException {

    var code = 0
    var type = StatusResponse.Type.ERROR
    lateinit var statuscode: StatusCode

    constructor(message: String, code: Int):super(message){
        this.code =code
    }

    constructor(err: StatusCode): this(err.statusMessage, err.statusCode){
        statuscode = err
    }

    constructor(err: StatusCode, param: String): this(
        MessageFormat.format(err.statusMessage,param),
        err.statusCode
    ){
        statuscode =err
    }

}