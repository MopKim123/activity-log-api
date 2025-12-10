package com.example.activity_log_api.controller

import com.example.activity_log_api.model.dto.ActivityTypeResponse
import com.example.activity_log_api.service.ActivityTypeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/activity-types")
class ActivityTypeController(
    private val activityTypeService: ActivityTypeService
) {

    @GetMapping
    fun getAll(): ResponseEntity<List<ActivityTypeResponse>> =
        ResponseEntity.ok(activityTypeService.getAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ActivityTypeResponse> =
        activityTypeService.getById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
}
