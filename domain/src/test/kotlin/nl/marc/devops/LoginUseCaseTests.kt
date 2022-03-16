package nl.marc.devops

import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LoginUseCaseTests {
    @Test
    fun `AD-87, AD-90) login returns a user when a valid email and password has been supplied`() {
        // Arrange
        val email = "test@avans.nl"
        val password = "MySecretPassword"
        val expectedUser = User("ABCD", email, password.hashCode().toString(16))
        val userRepositoryMock = mockk<UserRepository>()
        every { userRepositoryMock.findUserByEmail(email) } returns expectedUser
        val loginUseCase = LoginUseCase(userRepositoryMock)

        // Act
        val actualUser = loginUseCase.login(email, password)

        // Assert
        assertEquals(expectedUser, actualUser)
    }

    @Test
    fun `AD-87, AD-91) login throws an error when an invalid email was supplied`() {
        // Arrange
        val email = "test@avans.nl"
        val password = "MySecretPassword"
        val userRepositoryMock = mockk<UserRepository>()
        every { userRepositoryMock.findUserByEmail(any()) } returns null
        val loginUseCase = LoginUseCase(userRepositoryMock)

        // Act & Assert
        assertFailsWith(IllegalArgumentException::class) {
            loginUseCase.login("test.invalid@avans.nl", password)
        }
    }

    @Test
    fun `AD-87, AD-92) login throws an error when an invalid password was supplied`() {
        // Arrange
        val email = "test@avans.nl"
        val password = "MySecretPassword"
        val expectedUser = User("ABCD", email, password.hashCode().toString(16))
        val userRepositoryMock = mockk<UserRepository>()
        every { userRepositoryMock.findUserByEmail(email) } returns expectedUser
        val loginUseCase = LoginUseCase(userRepositoryMock)

        // Act
        assertFailsWith(IllegalArgumentException::class) {
            loginUseCase.login(email, "SomeInvalidPassword")
        }
    }
}
