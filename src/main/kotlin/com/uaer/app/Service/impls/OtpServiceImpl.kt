package com.uaer.app.Service.impls

import com.uaer.app.Models.Common.OTPSent
import com.uaer.app.Service.CacheService
import com.uaer.app.Service.EmailService
import com.uaer.app.Service.OtpService
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class OtpServiceImpl(
        private val cacheService: CacheService,
        private val emailService: EmailService
): OtpService {

    override fun sendOTP(email: String, obj:  Any, about: String): OTPSent {
        val otp = Random.nextInt(100000, 1000000)
        cacheService.setOTPObject(email, otp.toString(),obj)

        val body = "Dear User, \nYour registration verification Otp is : $otp \n \nRegards, \nApp."
        val subject = "Registration Verification OTP"
        emailService.sendEmail(email,body, subject)
        return OTPSent("NA", email)
    }

    override fun validateOTPObject(email: String, otp: String, obj: Any, userId:String) {
        cacheService.validateOTPObject(email, otp, obj)

        val body = "Dear User, \nYour registration verification has been done your userID is : $userId \nYou can use userid or emailid for login.\n \nRegards, \nApp."
        val subject = "Registration Done"
        emailService.sendEmail(email,body, subject)
    }
}