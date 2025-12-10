package com.example.activity_log_api.repository

import com.example.activity_log_api.model.ActivityLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface ActivityLogRepository : JpaRepository<ActivityLog, Long>, JpaSpecificationExecutor<ActivityLog>
