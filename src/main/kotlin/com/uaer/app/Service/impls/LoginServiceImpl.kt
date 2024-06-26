package com.uaer.app.Service.impls

import com.uaer.app.Database.Models.registerRecode
import com.uaer.app.Database.Repository.registerRecodeRepository
import com.uaer.app.Exceptions.AppException
import com.uaer.app.Exceptions.AppStatusCodes
import com.uaer.app.Models.Common.OTPSent
import com.uaer.app.Models.Common.userDetails
import com.uaer.app.Models.Request.*
import com.uaer.app.Models.Response.loginResponse
import com.uaer.app.Models.Response.signUpResponse
import com.uaer.app.Security.JwtUtils
import com.uaer.app.Service.AuthenticationService
import com.uaer.app.Service.LoginService
import com.uaer.app.Service.OtpService
import com.uaer.app.Utils.app.CommonUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class LoginServiceImpl(
    var registerRecodeRepository: registerRecodeRepository,
    private var jwtUtils: JwtUtils,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val commonUtils: CommonUtils,
    private val authenticationService: AuthenticationService,
    private val otpService: OtpService
):LoginService {

    fun generateJwtAndAddSession(userDetails: userDetails): loginResponse {
        return loginResponse(
            userDetails.userId,
            jwtUtils.generateJwtToken(userDetails),
            LocalDateTime.now().toString()
        )
    }

    private fun generateNewUserId(): String {
        val lastUser = registerRecodeRepository.findTopByOrderByUserIdDesc()
        return if (lastUser != null) {
            val lastUserId = lastUser.userId.toInt()
            String.format("%06d", lastUserId + 1)
        } else {
            "000000"
        }
    }

    override fun userLogin(loginRequest: loginRequest): loginResponse {

        val user: registerRecode = if (commonUtils.isValidEmail(loginRequest.userId)) {
            // If the provided ID is an email, search by email
            registerRecodeRepository.findByEmail(loginRequest.userId)
                    ?: throw AppException(AppStatusCodes.USER_NOT_EXIST)
        } else {
            // Otherwise, search by user ID
            registerRecodeRepository.findById(loginRequest.userId).orElseThrow {
                throw AppException(AppStatusCodes.USER_NOT_EXIST)
            }
        }

        if (!passwordEncoder.matches(loginRequest.password, user.password)) {
            throw AppException(AppStatusCodes.WRONG_PASSWORD)
        }
        return generateJwtAndAddSession(
                userDetails = userDetails(
                        userId = user.userId,
                        channelId = "W",
                        timeStemp = LocalDateTime.now(),
                        UUID.randomUUID()
                )
        )
    }

    override fun signUpSendOtp(signUpRequest: signUpRequest): OTPSent {
        if(!commonUtils.isValidPassword(signUpRequest.password)){
            throw AppException(AppStatusCodes.INVALID_PASSWORD)
        }
        if(!commonUtils.isValidEmail(signUpRequest.email)){
            throw AppException(AppStatusCodes.INVALID_EMAIL)
        }

        val user = registerRecodeRepository.findByEmail(signUpRequest.email)
        if (user != null) {
            throw AppException(AppStatusCodes.USER_ALREADY_REGISTERED)
        }

        //Send OTP
        return otpService.sendOTP(signUpRequest.email, signUpRequest, "R")
    }

    override fun signUpVerifyOtp(signUpReqVerify: signUpReqVerify): signUpResponse {

        val user = registerRecodeRepository.findByEmail(signUpReqVerify.email)
        if (user != null) {
            throw AppException(AppStatusCodes.USER_ALREADY_REGISTERED)
        }
        val userId = generateNewUserId()
        otpService.validateOTPObject(signUpReqVerify.email, signUpReqVerify.otp, signUpReqVerify, userId,"R")
        val registerRecode = registerRecode(
                userId = userId,
                password = passwordEncoder.encode(signUpReqVerify.password),
                status = "A", email = signUpReqVerify.email
        )
        registerRecodeRepository.save(registerRecode)
        return signUpResponse(signUpReqVerify.email)
    }

    override fun updateUser(updateRequest: updateRequest): signUpResponse {
        val userId = authenticationService.user.userId
        val registerRecode = registerRecodeRepository.findById(userId).orElseThrow {
            throw AppException(AppStatusCodes.USER_NOT_EXIST)
        }
        registerRecode.name = updateRequest.name
        registerRecode.mobile = updateRequest.mobile
        registerRecode.address = updateRequest.address
        registerRecode.pincode = updateRequest.pincode
        registerRecode.lastUpdateTime = LocalDateTime.now()
        registerRecodeRepository.save(registerRecode)
        return signUpResponse("User $userId information updated successfully")
    }

    override fun getUser(): registerRecode {
        val userId = authenticationService.user.userId
        val registerRecode = registerRecodeRepository.findById(userId).orElseThrow {
            throw AppException(AppStatusCodes.USER_NOT_EXIST)
        }
       return registerRecode
    }

    override fun updatePasswordSendOtp(passUpdateReqest: passUpdateReqest): OTPSent {
        val userId = authenticationService.user.userId
        val registerRecode = registerRecodeRepository.findById(userId).orElseThrow {
            throw AppException(AppStatusCodes.USER_NOT_EXIST)
        }
        if(!commonUtils.isValidPassword(passUpdateReqest.password)){
            throw AppException(AppStatusCodes.INVALID_PASSWORD)
        }

        //Send OTP
        return otpService.sendOTP(registerRecode.email, passUpdateReqest, "PU")
    }

    override fun updatePasswordVerifyOtp(passUpdateReqVerify: passUpdateReqVerify): signUpResponse {
        val userId = authenticationService.user.userId
        val registerRecode = registerRecodeRepository.findById(userId).orElseThrow {
            throw AppException(AppStatusCodes.USER_NOT_EXIST)
        }
        otpService.validateOTPObject(registerRecode.email, passUpdateReqVerify.otp, passUpdateReqVerify, userId, "PU")
        if(!commonUtils.isValidPassword(passUpdateReqVerify.password)){
            throw AppException(AppStatusCodes.INVALID_PASSWORD)
        }
        registerRecode.password = passwordEncoder.encode(passUpdateReqVerify.password)
        registerRecode.lastUpdateTime = LocalDateTime.now()
        registerRecodeRepository.save(registerRecode)
        return signUpResponse("User $userId password updated successfully")
    }

}

