package com.uaer.app.Exceptions

import com.uaer.app.Utils.ResponseAdvice.StatusCode

class AppStatusCodes {

    companion object{

        fun createResponseCode(code: Int, message: String):StatusCode{
            return StatusCode(code,message)
        }

        val SUCCESS : StatusCode = createResponseCode(1111,"SUCCESS")
        val INVALID : StatusCode = createResponseCode(1001,"INVALID REQUEST")
        val SESSION_EXPIRED : StatusCode = createResponseCode(1002,"SESSION EXPIRED")

        val UNHANDLED_EXCEPTION : StatusCode = createResponseCode(9999,"Unhandled exception")

        val INVALID_EMAIL    :  StatusCode = createResponseCode(2000,"INVALID EMAIL ID")
        val INVALID_PASSWORD :  StatusCode = createResponseCode(2001,"INVALID PASSWORD")
        val USER_NOT_EXIST   :  StatusCode = createResponseCode(2001,"USER NOT EXIST")
        val USER_ALREADY_REGISTERED :  StatusCode = createResponseCode(2001,"USER ALREADY EXIST")
        val WRONG_PASSWORD   :  StatusCode = createResponseCode(2001,"WRONG PASSWORD")


    }
}