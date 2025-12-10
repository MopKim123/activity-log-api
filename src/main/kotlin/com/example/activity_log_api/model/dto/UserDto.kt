package com.example.activity_log_api.model.dto

import jakarta.validation.constraints.NotBlank

data class UserRequest (
    @field:NotBlank
    var username: String,

    @field:NotBlank
    var email: String,

    @field:NotBlank
    var password: String
)

data class LoginRequest (
    @field:NotBlank
    var username: String,

    @field:NotBlank
    var password: String
)

data class UserResponse(
    var id: Long? = null,
    var username: String? = null,
    var email: String? = null,
    var token: String? = null
)