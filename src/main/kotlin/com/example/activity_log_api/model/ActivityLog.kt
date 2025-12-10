package com.example.activity_log_api.model

import com.example.activity_log_api.model.dto.ActivityLogRequest
import com.example.activity_log_api.model.dto.ActivityLogResponse
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "activity_logs")
class ActivityLog (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = User(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_type_id", nullable = false)
    var activityType: ActivityType = ActivityType(),

    var description: String? = null,

    var createdAt: LocalDateTime? = null
)

object activityLogMapper {
    fun Long.toEntity(user: User, log: ActivityLog): ActivityLog {
        log.user = user
        return log
    }

    fun Long.toEntity(activityType: ActivityType, log: ActivityLog): ActivityLog {
        log.activityType = activityType
        return log
    }

    fun ActivityLogRequest.toEntity(user: User, activityType: ActivityType): ActivityLog {
        val log = ActivityLog(
            user = user,
            activityType = activityType,
            description = this.description,
        )
        return log
    }

    fun ActivityLog.toResponse(): ActivityLogResponse {
        val res = ActivityLogResponse(
            id = this.id,
            userId = this.user.id,
            activityTypeId = activityType.id,
            description = this.description,
            createdAt = this.createdAt
        )
        return res
    }
}