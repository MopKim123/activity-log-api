package com.example.activity_log_api.repository

import com.example.activity_log_api.model.ActivityType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityTypeRepository : JpaRepository<ActivityType, Long> {
    fun findByName(name: String): ActivityType?
}
