package com.example.activity_log_api.service

import com.example.activity_log_api.model.dto.UserRequest
import com.example.activity_log_api.model.dto.UserResponse

interface UserService {
    fun getById(id: Long): UserResponse?
    fun register(request: UserRequest): UserResponse
    fun login(username: String, password: String): UserResponse?
}