package com.example.activity_log_api.service

import com.example.activity_log_api.model.dto.ActivityLogFilterRequest
import com.example.activity_log_api.model.dto.ActivityLogRequest
import com.example.activity_log_api.model.dto.ActivityLogResponse

interface ActivityLogService {
    fun getAll(filters: ActivityLogFilterRequest? = null): List<ActivityLogResponse>
    fun getById(id: Long): ActivityLogResponse?
    fun create(request: ActivityLogRequest): ActivityLogResponse
    fun delete(id: Long)
    fun getLogsByUser(userId: Long, filters: ActivityLogFilterRequest?): List<ActivityLogResponse>
    fun update(id: Long, description: String?, typeId: Long): ActivityLogResponse
}