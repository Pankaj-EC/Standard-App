package com.uaer.app.Service

interface CacheService {

    /**
     * AUTH
     */


    /**
     * OTP
     */
    fun setOTPObject(userId: String, otp: String, obj: Any)
    fun validateOTPObject(userId: String, otp: String, obj: Any)
}