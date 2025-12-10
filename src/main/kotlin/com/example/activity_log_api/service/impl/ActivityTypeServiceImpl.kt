package com.example.activity_log_api.service.impl

import com.example.activity_log_api.service.ActivityTypeService
import com.example.activity_log_api.model.dto.ActivityTypeResponse
import com.example.activity_log_api.model.activityTypeMapper
import com.example.activity_log_api.model.activityTypeMapper.toResponse
import com.example.activity_log_api.repository.ActivityTypeRepository
import org.springframework.stereotype.Service

@Service
class ActivityTypeServiceImpl(
    private val activityTypeRepository: ActivityTypeRepository
): ActivityTypeService {

    override fun getAll(): List<ActivityTypeResponse> =
        activityTypeRepository.findAll().map { it.toResponse() }

    override fun getById(id: Long): ActivityTypeResponse? =
        activityTypeRepository.findById(id).orElse(null)?.toResponse()
}