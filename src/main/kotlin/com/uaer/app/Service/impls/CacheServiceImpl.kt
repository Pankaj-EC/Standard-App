package com.uaer.app.Service.impls

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.uaer.app.Exceptions.AppException
import com.uaer.app.Exceptions.AppStatusCodes
import com.uaer.app.Service.CacheService
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class CacheServiceImpl(private val redisTemplate: RedisTemplate<String, Any>) : CacheService {

    companion object {
        const val REDIS_TABLE_SEPARATOR = "::"
        const val APP_OTP = "APP:OTP"
        const val OTP_EXPIRATION_MINUTES = 30L
        const val REQUEST_EXPIRATION_MINUTES = 3L
        const val MAX_OTP_ATTEMPTS = 3
    }

    private val objectMapper = jacksonObjectMapper()
    private val logger = LoggerFactory.getLogger(CacheServiceImpl::class.java)

    override fun setOTPObject(userId: String, otp: String, obj: Any) {
        val valueOps: ValueOperations<String, Any> = redisTemplate.opsForValue()
        val userOtpKey = "$APP_OTP$REDIS_TABLE_SEPARATOR$userId"
        val userCountKey = "$userOtpKey$REDIS_TABLE_SEPARATOR:count"

        // Check OTP count
        val count = valueOps.get(userCountKey) as Int? ?: 0
        if (count >= MAX_OTP_ATTEMPTS) {
            throw AppException(AppStatusCodes.MAX_OTP_SENT)
        }

        // Store OTP and associated object
        val otpData = mapOf("otp" to otp, "object" to obj)
        valueOps.set(userOtpKey, objectMapper.writeValueAsString(otpData), REQUEST_EXPIRATION_MINUTES, TimeUnit.MINUTES)

        // Increment the count
        valueOps.increment(userCountKey)
        redisTemplate.expire(userCountKey, OTP_EXPIRATION_MINUTES, TimeUnit.MINUTES)
    }

    override fun validateOTPObject(userId: String, otp: String, obj: Any) {
        val valueOps: ValueOperations<String, Any> = redisTemplate.opsForValue()
        val userOtpKey = "$APP_OTP$REDIS_TABLE_SEPARATOR$userId"
        val userCountKey = "$userOtpKey$REDIS_TABLE_SEPARATOR:count"

        // Retrieve the stored OTP and object
        val otpDataJson = valueOps.get(userOtpKey) as String? ?: throw AppException(AppStatusCodes.INVALID_OTP_REQUEST)
        val otpData: Map<String, Any> = objectMapper.readValue(otpDataJson)

        val storedOtp = otpData["otp"] as String
        val storedObj = otpData["object"] as Map<*, *>

        // Validate OTP
        if (storedOtp != otp) {
            throw AppException(AppStatusCodes.INVALID_OTP)
        }

        // Convert provided object to a map and remove the OTP field
        val map:Map<*,*> = ObjectMapper().convertValue(obj,MutableMap::class.java)

        val providedObjWithoutOtp = map.toMutableMap()
        providedObjWithoutOtp.remove("otp")

        // Compare the objects excluding the OTP field
        if (storedObj != providedObjWithoutOtp) {
            throw AppException(AppStatusCodes.INVALID_OTP_FOR_REQUEST)
        }

        // Remove the OTP and count from Redis
        redisTemplate.delete(userOtpKey)
        redisTemplate.delete(userCountKey)
    }
}
