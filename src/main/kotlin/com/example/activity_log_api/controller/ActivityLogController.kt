package com.example.activity_log_api.controller

import com.example.activity_log_api.model.dto.ActivityLogFilterRequest
import com.example.activity_log_api.model.dto.ActivityLogRequest
import com.example.activity_log_api.model.dto.ActivityLogResponse
import com.example.activity_log_api.service.ActivityLogService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/logs")
class ActivityLogController(
    private val activityLogService: ActivityLogService
) {

    @GetMapping
    fun getAll(): ResponseEntity<List<ActivityLogResponse>> =
        ResponseEntity.ok(activityLogService.getAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ActivityLogResponse> =
        activityLogService.getById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @GetMapping("/user/{userId}")
    fun getByUser(
        @PathVariable userId: Long,
        @RequestParam(required = false) activityTypeId: Long?,
        @RequestParam(required = false) startDate: String?,
        @RequestParam(required = false) endDate: String?
    ): ResponseEntity<List<ActivityLogResponse>> {
        val filter = ActivityLogFilterRequest().apply {
            this.activityTypeId = activityTypeId
            this.startDate = startDate?.let { java.time.LocalDateTime.parse(it) }
            this.endDate = endDate?.let { java.time.LocalDateTime.parse(it) }
        }
        return ResponseEntity.ok(activityLogService.getLogsByUser(userId, filter))
    }

    @PostMapping
    fun create(@Valid @RequestBody request: ActivityLogRequest): ResponseEntity<ActivityLogResponse> =
        ResponseEntity.ok(activityLogService.create(request))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestParam description: String?): ResponseEntity<ActivityLogResponse> =
        ResponseEntity.ok(activityLogService.update(id, description))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        activityLogService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
