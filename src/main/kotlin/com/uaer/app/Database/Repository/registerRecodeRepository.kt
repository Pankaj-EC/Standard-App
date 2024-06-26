package com.uaer.app.Database.Repository

import com.uaer.app.Database.Models.registerRecode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface registerRecodeRepository:JpaRepository<registerRecode,String> {
    fun findTopByOrderByUserIdDesc(): registerRecode?
    fun findByEmail(email: String): registerRecode?
}