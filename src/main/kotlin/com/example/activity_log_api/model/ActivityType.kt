package com.example.activity_log_api.model

import jakarta.persistence.*
import java.time.LocalDateTime
@Entity
@Table(name = "activity_types")
class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true, nullable = false)
    var name: String? = null
}

object userMapper {
    fun User.toDto(): UserDto {
        return UserDto(
            id = this.id,
            username = this.username,
            email = this.email
        )
    }

    fun UserDto.toEntity(): User {
        val user = User()
        user.id = this.id
        user.username = this.username
        user.email = this.email
        return user
    }
}
