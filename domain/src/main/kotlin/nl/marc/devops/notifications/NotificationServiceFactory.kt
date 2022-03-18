package nl.marc.devops.notifications

import nl.marc.devops.notifications.sending_apis.EmailSender
import nl.marc.devops.notifications.sending_apis.WebhookSender
import nl.marc.devops.notifications.sending_strategy.EmailNotificationStrategy
import nl.marc.devops.notifications.sending_strategy.PrivateWebhookNotificationStrategy
import nl.marc.devops.notifications.sending_strategy.SendNotificationStrategy
import nl.marc.devops.notifications.sending_strategy.SlackNotificationStrategy

class NotificationServiceFactory(
    private val webhookSender: WebhookSender,
    private val emailSender: EmailSender
) {
    fun createNotificationStrategy(channel: NotificationChannel): SendNotificationStrategy {
        return createNotificationStrategy(channel.channelName)
    }

    fun createNotificationStrategy(type: String): SendNotificationStrategy {
        return when(type) {
            EmailNotificationStrategy.CHANNEL_NAME -> createEmailNotificationStrategy()
            SlackNotificationStrategy.CHANNEL_NAME -> createSlackNotificationStrategy()
            PrivateWebhookNotificationStrategy.CHANNEL_NAME -> createPrivateWebhookNotificationStrategy()
            else -> throw IllegalArgumentException("Type $type is not registered!")
        }
    }

    private fun createEmailNotificationStrategy(): SendNotificationStrategy {
        return EmailNotificationStrategy(emailSender)
    }

    private fun createSlackNotificationStrategy(): SendNotificationStrategy {
        return SlackNotificationStrategy(webhookSender)
    }

    private fun createPrivateWebhookNotificationStrategy(): SendNotificationStrategy {
        return PrivateWebhookNotificationStrategy(webhookSender)
    }
}
