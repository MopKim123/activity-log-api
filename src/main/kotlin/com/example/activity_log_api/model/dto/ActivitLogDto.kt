package com.example.activity_log_api.model.dto

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime


class ActivityLogRequest {
    @field:NotNull
    var userId: Long? = null

    @field:NotNull
    var activityTypeId: Long? = null

    var description: String? = null
}

class ActivityLogResponse {
    var id: Long? = null
    var userId: Long? = null
    var activityTypeId: Long? = null
    var description: String? = null
    var createdAt: LocalDateTime? = null
}

class ActivityLogFilterRequest {
    var userId: Long? = null
    var activityTypeId: Long? = null
    var startDate: LocalDateTime? = null
    var endDate: LocalDateTime? = null
}