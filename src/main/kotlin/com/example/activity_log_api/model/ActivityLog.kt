package com.example.activity_log_api.model

import com.example.activity_log_api.model.dto.ActivityLogRequest
import com.example.activity_log_api.model.dto.ActivityLogResponse
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "activity_logs")
class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_type_id", nullable = false)
    var activityType: ActivityType? = null

    var description: String? = null

    var createdAt: LocalDateTime? = null
}

object activityLogMapper {
    fun Long.toEntity(user: User, log: ActivityLog = ActivityLog()): ActivityLog {
        log.user = user
        return log
    }

    fun Long.toEntity(activityType: ActivityType, log: ActivityLog = ActivityLog()): ActivityLog {
        log.activityType = activityType
        return log
    }

    fun ActivityLogRequest.toEntity(user: User, activityType: ActivityType): ActivityLog {
        val log = ActivityLog()
        log.user = user
        log.activityType = activityType
        log.description = this.description
        return log
    }

    fun ActivityLog.toResponse(): ActivityLogResponse {
        val res = ActivityLogResponse()
        res.id = this.id
        res.userId = this.user?.id
        res.activityTypeId = this.activityType?.id
        res.description = this.description
        res.createdAt = this.createdAt
        return res
    }
}