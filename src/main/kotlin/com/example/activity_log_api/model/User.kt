package com.example.activity_log_api.model

import com.example.activity_log_api.model.dto.UserRequest
import com.example.activity_log_api.model.dto.UserResponse
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(unique = true, nullable = false)
    var username: String = "",

    @Column(unique = true, nullable = false)
    var email: String? = null,

    @Column(nullable = false)
    var password: String? = null
)

object userMapper {
    fun UserRequest.toEntity(): User {
        val user = User(
            username = this.username,
            email = this.email,
            password = this.password
        )
        return user
    }

    fun User.toResponse(token: String): UserResponse {
        val res = UserResponse()
        res.id = this.id
        res.username = this.username
        res.email = this.email
        res.token = token
        return res
    }
}
