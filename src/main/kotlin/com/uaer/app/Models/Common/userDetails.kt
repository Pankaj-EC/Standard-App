package com.uaer.app.Models.Common

import java.time.LocalDateTime
import java.util.UUID

data class userDetails(
    var userId: String,
    var channelId: String,
    var timeStemp: LocalDateTime,
    var uuid: UUID
)
