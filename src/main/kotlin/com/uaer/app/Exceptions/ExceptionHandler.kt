package com.uaer.app.Exceptions

import com.uaer.app.Utils.ResponseAdvice.GlobleRespons
import com.uaer.app.Utils.ResponseAdvice.StatusResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionHandler {

    companion object{
        private val logger = LoggerFactory.getLogger(ExceptionHandler::class.java)
    }

    @ExceptionHandler(Exception::class)
    fun handelException(ex:Exception, request: WebRequest):ResponseEntity<GlobleRespons<Nothing?>> {
        logger.error("EXCEPTION ==========================================================================")
        logger.error(ex.toString())
        logger.error("EXCEPTION ==========================================================================")
        val response = if(ex is BaseException){
            GlobleRespons(null, StatusResponse(ex.statuscode,StatusResponse.Type.ERROR))
        } else {
            GlobleRespons(null, StatusResponse(AppStatusCodes.UNHANDLED_EXCEPTION,StatusResponse.Type.ERROR))
        }
        val httpStatus = if (ex is AppException) HttpStatus.UNAUTHORIZED else HttpStatus.INTERNAL_SERVER_ERROR

        return ResponseEntity(response, HttpHeaders(),httpStatus)
        }
    }