package com.example.activity_log_api.service.impl

import com.example.activity_log_api.model.ActivityLog
import com.example.activity_log_api.model.ActivityType
import com.example.activity_log_api.model.User
import com.example.activity_log_api.model.dto.ActivityLogFilterRequest
import com.example.activity_log_api.model.dto.ActivityLogRequest
import com.example.activity_log_api.repository.ActivityLogRepository
import com.example.activity_log_api.repository.ActivityTypeRepository
import com.example.activity_log_api.repository.UserRepository
import com.example.activity_log_api.service.impl.ActivityLogServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

class ActivityLogServiceImplTest {

    private val activityLogRepository = mockk<ActivityLogRepository>(relaxed = true)
    private val userRepository = mockk<UserRepository>(relaxed = true)
    private val activityTypeRepository = mockk<ActivityTypeRepository>(relaxed = true)

    private val activityLogService = ActivityLogServiceImpl(
        activityLogRepository,
        userRepository,
        activityTypeRepository
    )

    @Test
    fun `should return all activity logs`() {
        // Arrange
        val log1 = ActivityLog(id = 1L, description = "Test 1")
        val log2 = ActivityLog(id = 2L, description = "Test 2")
        every { activityLogRepository.findAll() } returns listOf(log1, log2)

        // Act
        val result = activityLogService.getAll(null)

        // Assert
        assertEquals(2, result.size)
        assertEquals("Test 1", result[0].description)
        verify(exactly = 1) { activityLogRepository.findAll() }
    }

    @Test
    fun `should return activity log by id when FOUND`() {
        // Arrange
        val log = ActivityLog(id = 1L, description = "Test")
        every { activityLogRepository.findById(1L) } returns Optional.of(log)

        // Act
        val result = activityLogService.getById(1L)

        // Assert
        assertNotNull(result)
        assertEquals("Test", result?.description)
        verify(exactly = 1) { activityLogRepository.findById(1L) }
    }

    @Test
    fun `should return null when activity log NOT FOUND by id`() {
        every { activityLogRepository.findById(1L) } returns Optional.empty()

        val result = activityLogService.getById(1L)

        assertNull(result)
        verify(exactly = 1) { activityLogRepository.findById(1L) }
    }

    @Test
    fun `should create new activity log successfully`() {
        // Arrange
        val request = ActivityLogRequest(userId = 1L, activityTypeId = 2L, description = "New log")
        val user = User(id = 1L)
        val type = ActivityType(id = 2L)
        val savedLog = ActivityLog(id = 10L, description = "New log", user = user, activityType = type, createdAt = LocalDateTime.now())

        every { userRepository.findById(1L) } returns Optional.of(user)
        every { activityTypeRepository.findById(2L) } returns Optional.of(type)
        every { activityLogRepository.save(any()) } answers { firstArg() }

        val result = activityLogService.create(request)

        assertEquals("New log", result.description)
        verify(exactly = 1) { userRepository.findById(1L) }
        verify(exactly = 1) { activityTypeRepository.findById(2L) }
        verify(exactly = 1) { activityLogRepository.save(any()) }
    }

    @Test
    fun `should update activity log successfully`() {
        // Arrange
        val log = ActivityLog(id = 1L, description = "Old", activityType = ActivityType(id = 1L))
        val newType = ActivityType(id = 2L)
        every { activityLogRepository.findById(1L) } returns Optional.of(log)
        every { activityTypeRepository.findById(2L) } returns Optional.of(newType)
        every { activityLogRepository.save(any()) } answers { firstArg() }

        val result = activityLogService.update(1L, "Updated", 2L)

        assertEquals("Updated", result.description)
        assertEquals(2L, result.activityTypeId)
        verify(exactly = 1) { activityLogRepository.findById(1L) }
        verify(exactly = 1) { activityTypeRepository.findById(2L) }
        verify(exactly = 1) { activityLogRepository.save(any()) }
    }

    @Test
    fun `should delete activity log by id`() {
        // Act
        activityLogService.delete(1L)

        // Assert
        verify(exactly = 1) { activityLogRepository.deleteById(1L) }
    }
}
