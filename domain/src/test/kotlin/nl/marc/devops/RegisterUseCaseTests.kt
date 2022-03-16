package nl.marc.devops

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class RegisterUseCaseTests {
    @Test
    fun `AD-88, AD-93) When registering a user should be added to the repository`() {
        // Arrange
        val name = "ABC"
        val email = "test@avans.nl"
        val password = "ABC123"
        val passwordHash = password.hashCode().toString(16)
        val user = User("1234", name, email, passwordHash)
        val userRepository = mockk<UserRepository>()
        every { userRepository.addUser(any(), any(), any()) } returns user
        val registerUseCase = RegisterUseCase(userRepository)

        // Act
        registerUseCase.register(name, email, password)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { userRepository.addUser(any(), any(), any()) }
    }

    @Test
    fun `AD-88, AD-94) When registering a password should be hashed`() {
        // Arrange
        val name = "ABC"
        val email = "test@avans.nl"
        val password = "ABC123"
        val passwordHash = password.hashCode().toString(16)
        val user = User("1234", name, email, passwordHash)
        val userRepository = mockk<UserRepository>()
        every { userRepository.addUser(any(), any(), any()) } returns user
        val registerUseCase = RegisterUseCase(userRepository)

        // Act
        registerUseCase.register(name, email, password)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { userRepository.addUser(any(), any(), passwordHash) }
    }

    companion object {
        const val INVOCATION_KIND_ONCE = 1
    }
}
