package com.uaer.app.Service

import com.uaer.app.Database.Models.registerRecode
import com.uaer.app.Models.Common.OTPSent
import com.uaer.app.Models.Request.loginRequest
import com.uaer.app.Models.Request.signUpReqVerify
import com.uaer.app.Models.Request.signUpRequest
import com.uaer.app.Models.Request.updateRequest
import com.uaer.app.Models.Response.loginResponse
import com.uaer.app.Models.Response.signUpResponse

interface LoginService {

    fun userLogin(loginRequest: loginRequest): loginResponse

    fun signUpSendOtp(signUpRequest: signUpRequest): OTPSent

    fun signUpVerifyOtp(signUpReqVerify: signUpReqVerify):signUpResponse

    fun updateUser(updateRequest: updateRequest): signUpResponse

    fun getUser(): registerRecode
}