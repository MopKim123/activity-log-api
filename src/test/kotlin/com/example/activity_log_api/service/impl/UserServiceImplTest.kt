package com.example.activity_log_api.service.impl

import com.example.activity_log_api.model.userMapper.toEntity
import com.example.activity_log_api.repository.UserRepository
import com.example.activity_log_api.util.JwtUtil
import com.example.studentapi.util.TestData
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.Optional

@ExtendWith(MockKExtension::class)
class UserServiceImplTest {

    @MockK
    lateinit var userRepository: UserRepository
    @MockK
    lateinit var jwtUtil: JwtUtil
    @InjectMockKs
    lateinit var userService: UserServiceImpl


    @BeforeEach
    fun setup() = MockKAnnotations.init(this)



    @Test
    fun `should return user when FOUND by id`() {
        // Arrange
        val user = TestData.userData(id = 1L)
        every { userRepository.findById(1L) } returns Optional.of(user)

        // Act
        val result = userService.getById(1L)

        // Assert
        assertEquals(user.username, result?.username)
        assertNotNull(result)
        verify(exactly = 1) { userRepository.findById(1L) }
    }

    @Test
    fun `should return null when user NOT FOUND by id`() {
        // Arrange
        every { userRepository.findById(1L) } returns Optional.empty()

        // Act
        val result = userService.getById(1L)

        // Assert
        assertNull(result)
        verify(exactly = 1) { userRepository.findById(1L) }
    }

    @Test
    fun `should register new user successfully`() {
        // Arrange
        val request = TestData.userRequest(username = "newUser", email = "test@example.com")
        every { userRepository.findByUsername(request.username!!) } returns null
        every { userRepository.findByEmail(request.email!!) } returns null

        // Mock save for any User object
        every { userRepository.save(any()) } answers { firstArg() }

        // Act
        val result = userService.register(request)

        // Assert
        assertEquals(request.username, result.username)
        verify(exactly = 1) { userRepository.findByUsername(request.username!!) }
        verify(exactly = 1) { userRepository.findByEmail(request.email!!) }
        verify(exactly = 1) { userRepository.save(any()) }
    }


    @Test
    fun `should throw exception when registering with existing username`() {
        // Arrange
        val request = TestData.userRequest(username = "existingUser", email = "test@example.com")
        every { userRepository.findByUsername(request.username!!) } returns TestData.userData()

        // Act & Assert
        assertThrows<IllegalArgumentException> { userService.register(request) }
        verify(exactly = 1) { userRepository.findByUsername(request.username!!) }
        verify(exactly = 0) { userRepository.findByEmail(any()) }
    }

    @Test
    fun `should login successfully and return token`() {
        // Arrange
        val user = TestData.userData(username = "user1", password = "pass123")
        every { userRepository.findByUsername("user1") } returns user
        every { jwtUtil.generateToken("user1") } returns "token123"

        // Act
        val result = userService.login("user1", "pass123")

        // Assert
        assertEquals("token123", result?.token)
        assertEquals(user.username, result?.username)
        verify(exactly = 1) { userRepository.findByUsername("user1") }
        verify(exactly = 1) { jwtUtil.generateToken("user1") }
    }

    @Test
    fun `should return null when login with wrong password`() {
        // Arrange
        val user = TestData.userData(username = "user1", password = "pass123")
        every { userRepository.findByUsername("user1") } returns user

        // Act
        val result = userService.login("user1", "wrongpass")

        // Assert
        assertNull(result)
        verify(exactly = 1) { userRepository.findByUsername("user1") }
        verify(exactly = 0) { jwtUtil.generateToken(any()) }
    }

}