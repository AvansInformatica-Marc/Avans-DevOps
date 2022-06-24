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
        val channel = NotificationChannel("EMAIL", "notifications@avans-devops.nl")
        val repository = mockk<NotificationsRepository>()
        justRun { repository.saveNotificationChannelsForUser(any(), any()) }
        val notificationFactory = mockk<NotificationServiceFactory>()
        val notificationServiceImpl = NotificationServiceImpl(repository, notificationFactory)

        // Act
        notificationServiceImpl.addChannelsToUser(user, channel)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { repository.saveNotificationChannelsForUser(any(), any()) }
    }

    @Test
    fun `FR-1_4) When adding multiple notification channels, the repository gets updated`() {
        // Arrange
        val user = UsersFixture.defaultUser
        val emailChannel = NotificationChannel("EMAIL", "notifications@avans-devops.nl")
        val slackChannel = NotificationChannel("SLACK", "slack://avans-devops/#notifications")
        val repository = mockk<NotificationsRepository>()
        justRun { repository.saveNotificationChannelsForUser(any(), any()) }
        val notificationFactory = mockk<NotificationServiceFactory>()
        val notificationServiceImpl = NotificationServiceImpl(repository, notificationFactory)

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
        val channel = NotificationChannel("EMAIL", recipient)

        val repository = mockk<NotificationsRepository>()
        every { repository.getNotificationChannelsForUser(any()) } returns setOf(channel)

        val notificationFactory = mockk<NotificationServiceFactory>()
        val notificationStrategy = mockk<SendNotificationStrategy>()
        every { notificationFactory.createNotificationStrategy(any<NotificationChannel>()) } returns notificationStrategy
        justRun { notificationStrategy.sendNotification(any(), any(), any()) }

        val notificationServiceImpl = NotificationServiceImpl(repository, notificationFactory)

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
        val emailChannel = NotificationChannel("EMAIL", emailRecipient)
        val slackChannel = NotificationChannel("SLACK", slackRecipient)

        val repository = mockk<NotificationsRepository>()
        every { repository.getNotificationChannelsForUser(any()) } returns setOf(emailChannel, slackChannel)

        val emailStrategy = mockk<SendNotificationStrategy>()
        justRun { emailStrategy.sendNotification(any(), any(), any()) }
        val slackStrategy = mockk<SendNotificationStrategy>()
        justRun { slackStrategy.sendNotification(any(), any(), any()) }

        val notificationFactory = mockk<NotificationServiceFactory>()
        every { notificationFactory.createNotificationStrategy(emailChannel) } returns emailStrategy
        every { notificationFactory.createNotificationStrategy(slackChannel) } returns slackStrategy

        val notificationServiceImpl = NotificationServiceImpl(repository, notificationFactory)

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
        val notificationFactory = mockk<NotificationServiceFactory>()
        val notificationStrategy = mockk<SendNotificationStrategy>()
        every { notificationFactory.createNotificationStrategy(any<NotificationChannel>()) } returns notificationStrategy
        val notificationServiceImpl = NotificationServiceImpl(repository, notificationFactory)

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
