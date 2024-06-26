package com.uaer.app.Service

import com.uaer.app.Models.Common.OTPSent

interface OtpService {

    fun sendOTP(email: String, obj: Any, about: String):OTPSent
    fun validateOTPObject(email: String, otp: String, obj: Any, userId:String, about: String)
}