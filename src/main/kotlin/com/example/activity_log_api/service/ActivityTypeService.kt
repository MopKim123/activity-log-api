package com.example.activity_log_api.service

import com.example.activity_log_api.model.dto.ActivityTypeResponse

interface ActivityTypeService {
    fun getAll(): List<ActivityTypeResponse>
    fun getById(id: Long): ActivityTypeResponse?
}