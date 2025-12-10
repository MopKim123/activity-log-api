package com.example.activity_log_api.service.impl

import com.example.activity_log_api.model.ActivityType
import com.example.activity_log_api.model.dto.ActivityTypeResponse
import com.example.activity_log_api.repository.ActivityTypeRepository
import com.example.activity_log_api.service.impl.ActivityTypeServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class ActivityTypeServiceImplTest {

    private val activityTypeRepository = mockk<ActivityTypeRepository>(relaxed = true)
    private val activityTypeService = ActivityTypeServiceImpl(activityTypeRepository)

    @Test
    fun `should return all activity types`() {
        // Arrange
        val type1 = ActivityType(id = 1L, name = "Study")
        val type2 = ActivityType(id = 2L, name = "Exercise")
        every { activityTypeRepository.findAll() } returns listOf(type1, type2)

        // Act
        val result = activityTypeService.getAll()

        // Assert
        assertEquals(2, result.size)
        assertEquals("Study", result[0].name)
        assertEquals("Exercise", result[1].name)
        verify(exactly = 1) { activityTypeRepository.findAll() }
    }

    @Test
    fun `should return activity type by id when FOUND`() {
        // Arrange
        val type = ActivityType(id = 1L, name = "Study")
        every { activityTypeRepository.findById(1L) } returns Optional.of(type)

        // Act
        val result = activityTypeService.getById(1L)

        // Assert
        assertNotNull(result)
        assertEquals("Study", result?.name)
        verify(exactly = 1) { activityTypeRepository.findById(1L) }
    }

    @Test
    fun `should return null when activity type NOT FOUND by id`() {
        // Arrange
        every { activityTypeRepository.findById(1L) } returns Optional.empty()

        // Act
        val result = activityTypeService.getById(1L)

        // Assert
        assertNull(result)
        verify(exactly = 1) { activityTypeRepository.findById(1L) }
    }
}
