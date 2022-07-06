package nl.marc.devops.notifications

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import nl.marc.devops.fixtures.UsersFixture
import nl.marc.devops.notifications.sending_strategy.SendNotificationStrategy
import kotlin.test.Test

class NotificationServiceImplTests {
    @Test
    fun `FR-1_4) When adding a notification channel, the repository gets updated`() {
        // Arrange
        val user = UsersFixture.defaultUser
        val channel = NotificationChannel(mockk(), "notifications@avans-devops.nl")
        val repository = mockk<NotificationsRepository>()
        justRun { repository.saveNotificationChannelsForUser(any(), any()) }
        val notificationServiceImpl = NotificationServiceImpl(repository)

        // Act
        notificationServiceImpl.addChannelsToUser(user, channel)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { repository.saveNotificationChannelsForUser(any(), any()) }
    }

    @Test
    fun `FR-1_4) When adding multiple notification channels, the repository gets updated`() {
        // Arrange
        val user = UsersFixture.defaultUser
        val emailChannel = NotificationChannel(mockk(), "notifications@avans-devops.nl")
        val slackChannel = NotificationChannel(mockk(), "slack://avans-devops/#notifications")
        val repository = mockk<NotificationsRepository>()
        justRun { repository.saveNotificationChannelsForUser(any(), any()) }
        val notificationServiceImpl = NotificationServiceImpl(repository)

        // Act
        notificationServiceImpl.addChannelsToUser(user, emailChannel, slackChannel)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { repository.saveNotificationChannelsForUser(any(), any()) }
    }

    @Test
    fun `FR-1_4) When sending a notification to a single channel, sendNotification will be called`() {
        // Arrange
        val user = UsersFixture.defaultUser
        val recipient = "notifications@avans-devops.nl"

        val notificationStrategy = mockk<SendNotificationStrategy>()
        justRun { notificationStrategy.sendNotification(any(), any(), any()) }

        val channel = NotificationChannel(notificationStrategy, recipient)

        val repository = mockk<NotificationsRepository>()
        every { repository.getNotificationChannelsForUser(any()) } returns setOf(channel)

        val notificationServiceImpl = NotificationServiceImpl(repository)

        // Act
        notificationServiceImpl.sendNotification("Build failed", "Pipeline", user)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { notificationStrategy.sendNotification(any(), any(), recipient) }
    }

    @Test
    fun `FR-1_4) When sending a notification to a multiple channels, sendNotification will be called multiple times`() {
        // Arrange
        val user = UsersFixture.defaultUser
        val emailRecipient = "notifications@avans-devops.nl"
        val slackRecipient = "slack://avans-devops/#notifications"

        val emailStrategy = mockk<SendNotificationStrategy>()
        justRun { emailStrategy.sendNotification(any(), any(), any()) }
        val slackStrategy = mockk<SendNotificationStrategy>()
        justRun { slackStrategy.sendNotification(any(), any(), any()) }

        val emailChannel = NotificationChannel(emailStrategy, emailRecipient)
        val slackChannel = NotificationChannel(slackStrategy, slackRecipient)

        val repository = mockk<NotificationsRepository>()
        every { repository.getNotificationChannelsForUser(any()) } returns setOf(emailChannel, slackChannel)

        val notificationServiceImpl = NotificationServiceImpl(repository)

        // Act
        notificationServiceImpl.sendNotification("Build failed", "Pipeline", user)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { emailStrategy.sendNotification(any(), any(), emailRecipient) }
        verify(exactly = INVOCATION_KIND_ONCE) { slackStrategy.sendNotification(any(), any(), slackRecipient) }
    }

    @Test
    fun `FR-1_4) When no users have channels set up, the service should stop quietly`() {
        // Arrange
        val user = UsersFixture.defaultUser
        val repository = mockk<NotificationsRepository>()
        every { repository.getNotificationChannelsForUser(any()) } returns emptySet()
        val notificationStrategy = mockk<SendNotificationStrategy>()
        val notificationServiceImpl = NotificationServiceImpl(repository)

        // Act
        notificationServiceImpl.sendNotification("Build failed", "Pipeline", user)

        // Assert
        verify(exactly = INVOCATION_KIND_NEVER) { notificationStrategy.sendNotification(any(), any(), any()) }
    }

    companion object {
        const val INVOCATION_KIND_NEVER = 0

        const val INVOCATION_KIND_ONCE = 1
    }
}
