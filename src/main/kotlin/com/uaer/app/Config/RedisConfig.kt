package com.uaer.app.Config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.Jedis

@Configuration
class RedisConfig {

    @Bean
    fun jedis(): Jedis {
        return Jedis("localhost", 6379)
    }
}
