package nl.marc.devops.accounts

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AccountServiceTests {
    @Test
    fun `AD-87, AD-90) login returns a user when a valid email and password has been supplied`() {
        // Arrange
        val email = "test@avans.nl"
        val password = "MySecretPassword"
        val expectedUser = User("ABCD", email, password.hashCode().toString(16))
        val userRepositoryMock = mockk<UserRepository>()
        every { userRepositoryMock.findUserByEmail(email) } returns expectedUser
        val accountService = AccountService(userRepositoryMock)

        // Act
        val actualUser = accountService.login(email, password)

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
        val accountService = AccountService(userRepositoryMock)

        // Act & Assert
        assertFailsWith(IllegalArgumentException::class) {
            accountService.login("test.invalid@avans.nl", password)
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
        val accountService = AccountService(userRepositoryMock)

        // Act
        assertFailsWith(IllegalArgumentException::class) {
            accountService.login(email, "SomeInvalidPassword")
        }
    }

    @Test
    fun `AD-88, AD-93) When registering a user should be added to the repository`() {
        // Arrange
        val name = "ABC"
        val email = "test@avans.nl"
        val password = "ABC123"
        val passwordHash = password.hashCode().toString(16)
        val user = User(name, email, passwordHash)
        val userRepository = mockk<UserRepository>()
        every { userRepository.addUser(any()) } returns user
        val accountService = AccountService(userRepository)

        // Act
        accountService.register(name, email, password)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { userRepository.addUser(any()) }
    }

    @Test
    fun `AD-88, AD-94) When registering a password should be hashed`() {
        // Arrange
        val name = "ABC"
        val email = "test@avans.nl"
        val password = "ABC123"
        val passwordHash = password.hashCode().toString(16)
        val user = User(name, email, passwordHash)
        val userRepository = mockk<UserRepository>()
        every { userRepository.addUser(any()) } returns user
        val accountService = AccountService(userRepository)

        // Act
        accountService.register(name, email, password)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { userRepository.addUser(user) }
    }

    companion object {
        const val INVOCATION_KIND_ONCE = 1
    }
}
