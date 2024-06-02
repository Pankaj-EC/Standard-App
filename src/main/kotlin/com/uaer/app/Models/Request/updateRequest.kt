package com.uaer.app.Models.Request

import jakarta.persistence.Column
import java.time.LocalDateTime

data class updateRequest(
    var name:       String="",
    var mobile:     String="",
    var address:    String="",
    var pincode:    String="",
    var lastUpdateTime: LocalDateTime = LocalDateTime.now()
)
