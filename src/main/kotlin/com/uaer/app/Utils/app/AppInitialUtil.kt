package com.uaer.app.Utils.app

import com.uaer.app.Security.JwtUtils
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import redis.clients.jedis.Jedis

@Component
class AppInitialUtil(
    private val jwtUtils: JwtUtils
){
    companion object{
        protected var logger = LoggerFactory.getLogger(AppInitialUtil::class.java)
    }

    @PostConstruct
    fun init() {
        logger.info("APP Successfully Started")
        jwtUtils.initializeSecretKey()
        logger.info("JWT Key Successfully generated")
    }

}