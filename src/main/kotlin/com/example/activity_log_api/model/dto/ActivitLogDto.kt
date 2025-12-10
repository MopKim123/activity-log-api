package com.example.activity_log_api.model.dto

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime


data class ActivityLogRequest (
    @field:NotNull
    var userId: Long,

    @field:NotNull
    var activityTypeId: Long,

    var description: String? = null
)

data class ActivityLogResponse (
    var id: Long,
    var userId: Long,
    var activityTypeId: Long? = null,
    var description: String? = null,
    var createdAt: LocalDateTime? = null,
)

data class ActivityLogFilterRequest (
    var userId: Long? = null,
    var activityTypeId: Long? = null,
    var startDate: LocalDateTime? = null,
    var endDate: LocalDateTime? = null
)