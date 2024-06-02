package com.uaer.app.Utils.ResponseAdvice

import com.uaer.app.Exceptions.AppStatusCodes
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@ControllerAdvice
class GlobleResponseAdvice: ResponseBodyAdvice<Any>{

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        @Nullable
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        if(returnType.containingClass.isAnnotationPresent(RestController::class.java)){
           if(body !is GlobleRespons<*>){
               return GlobleRespons(body, StatusResponse(AppStatusCodes.SUCCESS,StatusResponse.Type.SUCCESS))
           }
        }
        return body
    }
}