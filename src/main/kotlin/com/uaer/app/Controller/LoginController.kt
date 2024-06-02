package com.uaer.app.Controller

import com.uaer.app.Models.Request.loginRequest
import com.uaer.app.Models.Request.signUpRequest
import com.uaer.app.Models.Request.updateRequest
import com.uaer.app.Models.Response.loginResponse
import com.uaer.app.Models.Response.signUpResponse
import com.uaer.app.Service.LoginService
import org.springframework.web.bind.annotation.CrossOrigin
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
    fun signUp(@RequestBody signUpRequest: signUpRequest):signUpResponse{
        return loginService.userSignUp(signUpRequest)
    }

    @PostMapping("/update")
    fun updateUser(@RequestBody updateRequest: updateRequest):signUpResponse{
        return loginService.updateUser(updateRequest)
    }

}