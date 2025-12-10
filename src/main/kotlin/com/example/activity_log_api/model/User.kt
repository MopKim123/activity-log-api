package com.example.activity_log_api.model

import com.example.activity_log_api.model.dto.ActivityTypeResponse
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true, nullable = false)
    var username: String? = null

    @Column(unique = true, nullable = false)
    var email: String? = null

    @Column(nullable = false)
    var password: String? = null
}

object activityTypeMapper {
    fun ActivityType.toResponse(): ActivityTypeResponse {
        val res = ActivityTypeResponse()
        res.id = this.id
        res.name = this.name
        return res
    }
}
