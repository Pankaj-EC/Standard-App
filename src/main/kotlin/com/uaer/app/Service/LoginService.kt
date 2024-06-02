package com.uaer.app.Service

import com.uaer.app.Models.Request.loginRequest
import com.uaer.app.Models.Request.signUpRequest
import com.uaer.app.Models.Request.updateRequest
import com.uaer.app.Models.Response.loginResponse
import com.uaer.app.Models.Response.signUpResponse

interface LoginService {

    fun userLogin(loginRequest: loginRequest):loginResponse

    fun userSignUp(signUpRequest: signUpRequest):signUpResponse

    fun updateUser(updateRequest: updateRequest):signUpResponse
}