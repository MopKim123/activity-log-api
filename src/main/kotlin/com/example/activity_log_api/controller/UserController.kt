package com.example.activity_log_api.controller

import com.example.activity_log_api.model.dto.UserRequest
import com.example.activity_log_api.model.dto.UserResponse
import com.example.activity_log_api.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<UserResponse> =
        userService.getById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: UserRequest): ResponseEntity<UserResponse> =
        ResponseEntity.ok(userService.register(request))

    @PostMapping("/login")
    fun login(@RequestParam username: String, @RequestParam password: String): ResponseEntity<UserResponse> =
        userService.login(username, password)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.badRequest().build()
}
