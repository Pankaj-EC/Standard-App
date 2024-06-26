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

        val body: String
        val subject: String

        when (about) {
            "R" -> {
                body = "Dear User, \nYour registration verification Otp is : $otp \n \nRegards, \nApp."
                subject = "Registration Verification OTP"
            }
            "PU" -> {
                body = "Dear User, \nYour password update Otp is : $otp \n \nRegards, \nApp."
                subject = "Password update Verification OTP"
            }
            else -> {
                body = "Thank you"
                subject = "App Mail"
            }
        }
        emailService.sendEmail(email,body, subject)
        return OTPSent("NA", email)
    }

    override fun validateOTPObject(email: String, otp: String, obj: Any, userId:String, about: String) {
        cacheService.validateOTPObject(email, otp, obj)

        val body: String
        val subject: String

        when (about) {
            "R" -> {
                body = "Dear User, \nYour registration verification has been done your userID is : $userId \nYou can use userid or emailid for login.\n \nRegards, \nApp."
                subject = "Registration Done"

            }
            "PU" -> {
                body = "Dear User, \nYour password updated successfully \n \nRegards, \nApp."
                subject = "Password updated successfully"
            }
            else -> {
                body = "Thank you"
                subject = "App Mail"
            }
        }
        emailService.sendEmail(email,body, subject)
    }
}