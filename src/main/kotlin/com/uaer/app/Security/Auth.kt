package com.uaer.app.Security

import com.uaer.app.Models.Common.userDetails
import org.springframework.security.authentication.AbstractAuthenticationToken
import java.lang.IllegalArgumentException
import kotlin.jvm.Throws

class Auth(
    private val principal: userDetails,
    private val credential: String

):AbstractAuthenticationToken(null)
{
    init{
        super.setAuthenticated(true)
    }

    override fun getCredentials(): String {
        return credential
    }

    override fun getPrincipal(): userDetails {
        return principal
    }

    @Throws(IllegalArgumentException::class)
    override fun setAuthenticated(authenticated: Boolean) {
        require(!isAuthenticated){"NA"}
        super.setAuthenticated(false)
    }

}