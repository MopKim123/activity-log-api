package com.example.activity_log_api.service.impl

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
    fun `should return user when FOUND by username`() {
        // Arrange
        val user = TestData.userData(username = "newUser")
        every { userRepository.findByUsername(user.username) } returns Optional.of(user)

        // Act
        val result = userService.findByUsername(user.username)

        // Assert
        assertEquals(user.username, result?.username)
        assertThat(result).isNotNull
        verify(exactly = 1){ userRepository.findByUsername(user.username) }
    }

}