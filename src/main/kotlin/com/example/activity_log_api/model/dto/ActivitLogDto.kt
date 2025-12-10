package com.example.activity_log_api.model.dto

import java.time.LocalDateTime

class ActivityLogRequest {
    var userId: Long? = null
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