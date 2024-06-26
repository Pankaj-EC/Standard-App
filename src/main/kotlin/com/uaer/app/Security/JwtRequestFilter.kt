package com.uaer.app.Security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.uaer.app.Exceptions.AppException
import com.uaer.app.Exceptions.AppStatusCodes
import com.uaer.app.Models.Common.userDetails
import com.uaer.app.Utils.ResponseAdvice.GlobleRespons
import com.uaer.app.Utils.ResponseAdvice.StatusResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime


@Component
class JwtRequestFilter(
    private val jwtUtils: JwtUtils
) : OncePerRequestFilter() {

    companion object {
        private val logger = LoggerFactory.getLogger(JwtRequestFilter::class.java)
    }
    private var error = false
    private val authenticationDetailsSource = WebAuthenticationDetailsSource()
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestURI = request.requestURI

        // Skip filter for login and signup endpoints
        if (requestURI == "/api/login" || requestURI == "/api/signUp" || requestURI == "/api/signUp/verify") {
            filterChain.doFilter(request, response)
            return
        }

        try {
            // 1. Extract the JWT token from the request
            val authorizationHeader = request.getHeader("Authorization")

            logger.info("JWT Filter ==========================================================================")
            logger.info(authorizationHeader)
            logger.info("JWT Filter ==========================================================================")

            // 2. Check if the Authorization header is present and starts with "Bearer "
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                // Extract the token from the Authorization header
                val jwtToken = authorizationHeader.substring(7)

                // 3. Validate the JWT token
                if (jwtUtils.validateJwtToken(jwtToken)) {
                    // 4. If the token is valid, extract user details from it
                    val userId = jwtUtils.extractUserId(jwtToken)
                    val uuid = jwtUtils.extractUuid(jwtToken)
                    val channelId = jwtUtils.extractChannelId(jwtToken)

                    // 5. Set authentication information in the Spring Security context
                    val userDetails = userDetails(userId!!, channelId!!, LocalDateTime.now(), uuid!!)
                    val auth = Auth(userDetails, userId)
                    auth.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = auth

                    // 6. Continue the filter chain
                    filterChain.doFilter(request, response)
                } else {
                    error = true
                    throw AppException(AppStatusCodes.INVALID)
                }
            } else {
                throw AppException(AppStatusCodes.INVALID)
            }
        } catch (ex: Exception) {
            if(error){
                handleException(response, AppException(AppStatusCodes.SESSION_EXPIRED))
            }else{
                handleException(response, AppException(AppStatusCodes.INVALID))
            }
        }
    }

    fun Any.toJson(): String = jacksonObjectMapper().writeValueAsString(this)
    private fun handleException(response: HttpServletResponse, ex: AppException) {
        error = false
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        val responseBody = GlobleRespons(
            data = null,
            status = StatusResponse(ex.statuscode, StatusResponse.Type.ERROR)
        )
        response.writer.write(responseBody.toJson())
    }
}