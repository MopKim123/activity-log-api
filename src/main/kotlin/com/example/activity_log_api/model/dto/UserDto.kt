package com.example.activity_log_api.model.dto

import java.time.LocalDateTime

class UserRequest {
    var username: String? = null
    var email: String? = null
    var password: String? = null
}

class UserResponse {
    var id: Long? = null
    var username: String? = null
    var email: String? = null
}