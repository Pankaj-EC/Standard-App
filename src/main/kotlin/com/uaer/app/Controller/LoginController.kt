package com.uaer.app.Controller

import com.uaer.app.Database.Models.registerRecode
import com.uaer.app.Models.Common.OTPSent
import com.uaer.app.Models.Request.*
import com.uaer.app.Models.Response.loginResponse
import com.uaer.app.Models.Response.signUpResponse
import com.uaer.app.Service.LoginService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("/api")
class LoginController(
    private val loginService: LoginService
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: loginRequest):loginResponse{
        return loginService.userLogin(loginRequest)
    }

    @PostMapping("/signUp")
    fun signUpSendOtp(@RequestBody signUpRequest: signUpRequest):OTPSent{
        return loginService.signUpSendOtp(signUpRequest)
    }

    @PostMapping("/signUp/verify")
    fun signUpVerifyOtp(@RequestBody signUpReqVerify: signUpReqVerify):signUpResponse{
        return loginService.signUpVerifyOtp(signUpReqVerify)
    }

    @PostMapping("/update")
    fun updateUser(@RequestBody updateRequest: updateRequest):signUpResponse{
        return loginService.updateUser(updateRequest)
    }

    @GetMapping("/getUser")
    fun updateUser():registerRecode{
        return loginService.getUser()
    }

    @PostMapping("/passwordUpdate")
    fun updatePasswordSendOtp(@RequestBody passUpdateReqest: passUpdateReqest):OTPSent{
        return loginService.updatePasswordSendOtp(passUpdateReqest)
    }

    @PostMapping("/passwordUpdate/verify")
    fun updatePasswordVerifyOtp(@RequestBody passUpdateReqVerify: passUpdateReqVerify):signUpResponse{
        return loginService.updatePasswordVerifyOtp(passUpdateReqVerify)
    }


}