package com.example.activity_log_api.service.impl

import com.example.activity_log_api.model.dto.UserRequest
import com.example.activity_log_api.model.dto.UserResponse
import com.example.activity_log_api.model.userMapper
import com.example.activity_log_api.model.userMapper.toEntity
import com.example.activity_log_api.model.userMapper.toResponse
import com.example.activity_log_api.repository.UserRepository
import com.example.activity_log_api.service.UserService
import com.example.activity_log_api.util.JwtUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil
): UserService {

    override fun getById(id: Long): UserResponse? =
        userRepository.findById(id).orElse(null)?.toResponse("")

    @Transactional
    override fun register(request: UserRequest): UserResponse {
        userRepository.findByUsername(request.username!!)?.let {
            throw IllegalArgumentException("Username already exists")
        }
        userRepository.findByEmail(request.email!!)?.let {
            throw IllegalArgumentException("Email already exists")
        }

        return request.toEntity().let { userRepository.save(it).toResponse("") }
    }

    override fun login(username: String, password: String): UserResponse? {
        val user = userRepository.findByUsername(username)
            ?.takeIf { it.password == password } ?: return null

        // Generate JWT using username
        val token = jwtUtil.generateToken(user.username!!)

        // Return user response with token
        return user.toResponse(token)
    }
}
