package com.uaer.app.Database

import com.uaer.app.Database.Models.registerRecode
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository


@Repository
class CustomeRepositry(
//    @Qualifier("postgreJdbcTemplate")
//    val jdbcTemplate: NamedParameterJdbcTemplate
){
//    fun findUser(userId: String): List<registerRecode?> {
//        return try {
//            jdbcTemplate.query(QueryConstants.GET_CUSTOMER_DETAILS,MapSqlParameterSource("USER_ID", userId),
//                BeanPropertyRowMapper(registerRecode::class.java))
//        }catch (e:Exception){
//            emptyList()
//        }
//    }

}