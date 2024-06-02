package com.uaer.app.Database.Models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "Login_Recode")
data class loginRecode(
    @Id
    @Column(name = "SESSION_ID")
    var sessionId: String="",
    @Column(name = "USER_ID")
    var userId:    String="",
    @Column(name = "CHANNEL_ID")
    var channelId:   String="",
    @Column(name = "TIME_STAMP")
    var loginTime: LocalDateTime =LocalDateTime.now(),
    @Column(name = "STATUS")
    var status:    String =""
)
