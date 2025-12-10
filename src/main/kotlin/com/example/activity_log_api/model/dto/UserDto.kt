package com.example.activity_log_api.model.dto

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

class UserRequest {
    @field:NotBlank
    var username: String? = null

    @field:NotBlank
    var email: String? = null

    @field:NotBlank
    var password: String? = null
}

data class LoginRequest (
    @field:NotBlank
    var username: String,

    @field:NotBlank
    var password: String
)

class UserResponse {
    var id: Long? = null
    var username: String? = null
    var email: String? = null
}