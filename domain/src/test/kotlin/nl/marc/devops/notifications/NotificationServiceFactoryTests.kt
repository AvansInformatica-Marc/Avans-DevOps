package nl.marc.devops.notifications

import io.mockk.mockk
import nl.marc.devops.notifications.sending_apis.EmailSender
import nl.marc.devops.notifications.sending_apis.WebhookSender
import nl.marc.devops.notifications.sending_strategy.EmailNotificationStrategy
import nl.marc.devops.notifications.sending_strategy.PrivateWebhookNotificationStrategy
import nl.marc.devops.notifications.sending_strategy.SlackNotificationStrategy
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class NotificationServiceFactoryTests {
    @Test
    fun `AD-30, AD-101) createNotificationStrategy will create email strategy when channel has type EMAIL`() {
        // Arrange
        val channel = NotificationChannel(EmailNotificationStrategy.CHANNEL_NAME, "notifications@avans-devops.nl")
        val emailSender = mockk<EmailSender>()
        val webhookSender = mockk<WebhookSender>()
        val factory = NotificationServiceFactory(webhookSender, emailSender)

        // Act
        val strategy = factory.createNotificationStrategy(channel)

        // Assert
        assertIs<EmailNotificationStrategy>(strategy)
    }

    @Test
    fun `AD-30, AD-101) createNotificationStrategy will create slack strategy when channel has type SLACK`() {
        // Arrange
        val channel = NotificationChannel(SlackNotificationStrategy.CHANNEL_NAME, "slack://avans-devops.nl/#notifications")
        val emailSender = mockk<EmailSender>()
        val webhookSender = mockk<WebhookSender>()
        val factory = NotificationServiceFactory(webhookSender, emailSender)

        // Act
        val strategy = factory.createNotificationStrategy(channel)

        // Assert
        assertIs<SlackNotificationStrategy>(strategy)
    }

    @Test
    fun `AD-30, AD-101) createNotificationStrategy will create a private webhook strategy when channel has type PRIVATE_WEBHOOK`() {
        // Arrange
        val channel = NotificationChannel(PrivateWebhookNotificationStrategy.CHANNEL_NAME, "https://webhook.avans-devops.nl/")
        val emailSender = mockk<EmailSender>()
        val webhookSender = mockk<WebhookSender>()
        val factory = NotificationServiceFactory(webhookSender, emailSender)

        // Act
        val strategy = factory.createNotificationStrategy(channel)

        // Assert
        assertIs<PrivateWebhookNotificationStrategy>(strategy)
    }

    @Test
    fun `AD-30, AD-102) createNotificationStrategy will throw an error when notification type is invalid`() {
        // Arrange
        val channel = NotificationChannel("aojgrsnjsnlvnklv", "avans-devops.nl")
        val emailSender = mockk<EmailSender>()
        val webhookSender = mockk<WebhookSender>()
        val factory = NotificationServiceFactory(webhookSender, emailSender)

        // Act & Assert
        assertFailsWith<IllegalArgumentException> {
            factory.createNotificationStrategy(channel)
        }
    }
}
