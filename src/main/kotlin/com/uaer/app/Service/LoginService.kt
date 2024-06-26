package com.uaer.app.Service

import com.uaer.app.Database.Models.registerRecode
import com.uaer.app.Models.Common.OTPSent
import com.uaer.app.Models.Request.*
import com.uaer.app.Models.Response.loginResponse
import com.uaer.app.Models.Response.signUpResponse

interface LoginService {

    fun userLogin(loginRequest: loginRequest): loginResponse

    fun signUpSendOtp(signUpRequest: signUpRequest): OTPSent

    fun signUpVerifyOtp(signUpReqVerify: signUpReqVerify):signUpResponse

    fun updateUser(updateRequest: updateRequest): signUpResponse

    fun getUser(): registerRecode

    fun updatePasswordSendOtp(passUpdateReqest: passUpdateReqest): OTPSent

    fun updatePasswordVerifyOtp(passUpdateReqVerify: passUpdateReqVerify):signUpResponse

}