package com.uaer.app.Database.Models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "Register_Recode")
data class registerRecode(
    @Id
    @Column(name = "USER_ID")
    var userId:     String="",
    @Column(name = "PASSWORD")
    var password:   String="",
    @Column(name = "NAME")
    var name:       String="",
    @Column(name = "MOBILE_NO")
    var mobile:     String="",
    @Column(name = "ADDRESS")
    var address:    String="",
    @Column(name = "PIN_CODE")
    var pincode:    String="",
    @Column(name = "STATUS")
    var status:     String ="",
    @Column(name = "REGISTER_TIME_STAMP")
    var loginTime:      LocalDateTime = LocalDateTime.now(),
    @Column(name = "UPDATE_TIME_STAMP")
    var lastUpdateTime: LocalDateTime = LocalDateTime.now()
)
