package com.example.activity_log_api.service.impl

import com.example.activity_log_api.model.ActivityLog
import com.example.activity_log_api.model.dto.ActivityLogFilterRequest
import com.example.activity_log_api.model.dto.ActivityLogRequest
import com.example.activity_log_api.model.dto.ActivityLogResponse
import com.example.activity_log_api.model.activityLogMapper
import com.example.activity_log_api.model.activityLogMapper.toEntity
import com.example.activity_log_api.model.activityLogMapper.toResponse
import com.example.activity_log_api.repository.ActivityLogRepository
import com.example.activity_log_api.repository.ActivityTypeRepository
import com.example.activity_log_api.repository.UserRepository
import com.example.activity_log_api.service.ActivityLogService
import com.example.activity_log_api.specification.ActivityLogSpecification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ActivityLogServiceImpl(
    private val activityLogRepository: ActivityLogRepository,
    private val userRepository: UserRepository,
    private val activityTypeRepository: ActivityTypeRepository
): ActivityLogService  {

    override fun getAll(filters: ActivityLogFilterRequest?): List<ActivityLogResponse> {
        val logs = filters?.let {
            activityLogRepository.findAll(ActivityLogSpecification.toSpec(it))
        } ?: activityLogRepository.findAll()

        return logs.map { it.toResponse() }
    }

    override fun getById(id: Long): ActivityLogResponse? =
        activityLogRepository.findById(id).orElse(null)?.toResponse()

    override fun getLogsByUser(userId: Long, filters: ActivityLogFilterRequest?): List<ActivityLogResponse> {
        val filter = filters?.apply { this.userId = userId } ?: ActivityLogFilterRequest().also { it.userId = userId }

        return activityLogRepository
            .findAll(ActivityLogSpecification.toSpec(filter))
            .map { it.toResponse() }
    }


    @Transactional
    override fun create(request: ActivityLogRequest): ActivityLogResponse {
        val user = userRepository.findById(request.userId!!).orElseThrow { IllegalArgumentException("User not found") }
        val activityType = activityTypeRepository.findById(request.activityTypeId!!).orElseThrow { IllegalArgumentException("Activity type not found") }

        val log = request.toEntity(user, activityType).apply { createdAt = LocalDateTime.now() }
        return activityLogRepository.save(log).toResponse()
    }

    @Transactional
    override fun update(id: Long, description: String?): ActivityLogResponse {
        val log = activityLogRepository.findById(id).orElseThrow { IllegalArgumentException("Log not found") }
        log.description = description
        return activityLogRepository.save(log).toResponse()
    }

    @Transactional
    override fun delete(id: Long) {
        activityLogRepository.deleteById(id)
    }
}