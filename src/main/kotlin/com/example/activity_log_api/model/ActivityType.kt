package com.example.activity_log_api.model

import com.example.activity_log_api.model.dto.ActivityTypeRequest
import com.example.activity_log_api.model.dto.ActivityTypeResponse
import jakarta.persistence.*
import java.time.LocalDateTime
@Entity
@Table(name = "activity_types")
class ActivityType (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    var name: String? = null
)

object activityTypeMapper {
    fun ActivityTypeRequest.toEntity(type: ActivityType = ActivityType()): ActivityType {
        type.name = this.name
        return type
    }

    fun ActivityType.toResponse(): ActivityTypeResponse {
        val res = ActivityTypeResponse()
        res.id = this.id
        res.name = this.name
        return res
    }
}
