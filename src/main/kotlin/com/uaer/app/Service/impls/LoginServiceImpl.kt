package com.uaer.app.Service.impls

import com.uaer.app.Database.Models.registerRecode
import com.uaer.app.Database.Repository.loginRecodeRepository
import com.uaer.app.Database.Repository.registerRecodeRepository
import com.uaer.app.Exceptions.AppException
import com.uaer.app.Exceptions.AppStatusCodes
import com.uaer.app.Models.Common.userDetails
import com.uaer.app.Models.Request.loginRequest
import com.uaer.app.Models.Request.signUpRequest
import com.uaer.app.Models.Request.updateRequest
import com.uaer.app.Models.Response.loginResponse
import com.uaer.app.Models.Response.signUpResponse
import com.uaer.app.Security.JwtUtils
import com.uaer.app.Service.AuthenticationService
import com.uaer.app.Service.LoginService
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
    private val authenticationService: AuthenticationService
):LoginService {

    fun generateJwtAndAddSession(userDetails: userDetails): loginResponse {
        return loginResponse(
            userDetails.userId,
            jwtUtils.generateJwtToken(userDetails),
            LocalDateTime.now().toString()
        )
    }

    override fun userLogin(loginRequest: loginRequest): loginResponse {

        val user = registerRecodeRepository.findById(loginRequest.userId).orElseThrow {
            throw AppException(AppStatusCodes.USER_NOT_EXIST)
        }

        if (!passwordEncoder.matches(loginRequest.password, user.password)) {
            throw AppException(AppStatusCodes.WRONG_PASSWORD)
        }
        return generateJwtAndAddSession(
            userDetails = userDetails(
                userId = loginRequest.userId,
                channelId = "W",
                timeStemp = LocalDateTime.now(),
                UUID.randomUUID()
            )
        )
    }

    override fun userSignUp(signUpRequest: signUpRequest): signUpResponse {
        if(!commonUtils.isValidPassword(signUpRequest.password)){
            throw AppException(AppStatusCodes.INVALID_PASSWORD)
        }
        if(!commonUtils.isValidEmail(signUpRequest.email)){
            throw AppException(AppStatusCodes.INVALID_EMAIL)
        }
        val registerRecode = registerRecode(
            userId = signUpRequest.email,
            password = passwordEncoder.encode(signUpRequest.password),
            status = "A"
        )
        registerRecodeRepository.save(registerRecode)
        return signUpResponse(signUpRequest.email)
    }

    override fun updateUser(updateRequest: updateRequest): signUpResponse {
        val userId = authenticationService.user.userId
        val user = registerRecodeRepository.findById(userId).orElseThrow {
            throw AppException(AppStatusCodes.USER_NOT_EXIST)
        }

        val registerRecode = registerRecode(
            // Update user information based on updateRequest
             name = updateRequest.name
            ,mobile = updateRequest.mobile
            ,address = updateRequest.address
            ,pincode = updateRequest.pincode
            ,lastUpdateTime = LocalDateTime.now()
        )

        // Save the updated user
        registerRecodeRepository.save(registerRecode)

        return signUpResponse("User information updated successfully")
    }

}

