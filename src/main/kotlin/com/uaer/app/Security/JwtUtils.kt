package com.uaer.app.Security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import com.uaer.app.Models.Common.userDetails
import org.springframework.stereotype.Component
import redis.clients.jedis.Jedis
import java.security.SecureRandom
import java.util.*


@Component
class JwtUtils (
    private val jedis: Jedis
) {

    companion object{
        private const val ISSUER     ="issuer"
        private const val USER_ID    ="userId"
        private const val CHANNEL_ID ="channelId"
        private const val SESSION_ID ="uuid"
        private const val DEFAULT_KEY="iELVqXtWsYw_7xxDSEwyscPPQraaRUBB2ilqwfyshc4"
    }

    fun generateSecretKey(): String {
        val secureRandom = SecureRandom()
        val keyBytes = ByteArray(32) // 256-bit key
        secureRandom.nextBytes(keyBytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes)
    }

    fun initializeSecretKey() {
        val secretKey = generateSecretKey()
        jedis.set("jwt_secret_key", secretKey)
    }

    fun generateJwtToken(userDetails: userDetails): String {
        val secret = jedis.get("jwt_secret_key") ?: DEFAULT_KEY
        println("KEY: $secret")
        val algorithm = Algorithm.HMAC256(secret)
        val now = System.currentTimeMillis()
        return JWT.create()
            .withIssuer(ISSUER)
            .withClaim(USER_ID, userDetails.userId)
            .withClaim(CHANNEL_ID, userDetails.channelId)
            .withClaim(SESSION_ID, userDetails.uuid.toString())
            .withIssuedAt(Date(now))
            .withExpiresAt(Date(now + 1000 * 60 * 60)) // Token valid for 1 hour
            .sign(algorithm)
    }

    fun validateJwtToken(token: String): Boolean {
        return try {
            val secret = jedis.get("jwt_secret_key") ?: DEFAULT_KEY
            val algorithm = Algorithm.HMAC256(secret)
            val verifier: JWTVerifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
            verifier.verify(token)
            !isTokenExpired(token,secret)
        } catch (exception: JWTVerificationException) {
            false
        }
    }

    fun isTokenExpired(token: String, secret: String): Boolean {
        return try {
            val algorithm = Algorithm.HMAC256(secret)
            val verifier: JWTVerifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
            val decodedJWT: DecodedJWT = verifier.verify(token)
            decodedJWT.expiresAt.before(Date())
        } catch (exception: JWTVerificationException) {
            true
        }
    }

    fun extractUserId(token: String): String? {
        return extractClaim(token, USER_ID)
    }

    fun extractUuid(token: String): UUID {
        return UUID.fromString(extractClaim(token, SESSION_ID) as String)
    }

    fun extractChannelId(token: String): String? {
        return extractClaim(token,  CHANNEL_ID)
    }

    private fun extractClaim(token: String, claim: String): String? {
        return try {
            val secret = jedis.get("jwt_secret_key") ?: DEFAULT_KEY
            val algorithm = Algorithm.HMAC256(secret)
            val verifier: JWTVerifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
            val decodedJWT: DecodedJWT = verifier.verify(token)
            decodedJWT.getClaim(claim).asString()
        } catch (exception: JWTVerificationException) {
            null
        }
    }
}