package nl.marc.devops.notifications.sending_strategy

import nl.marc.devops.notifications.sending_apis.WebhookSender

abstract class WebhookNotificationStrategy(private val webhookSender: WebhookSender): SendNotificationStrategy {
    override fun sendNotification(content: String, subject: String, recipient: String) {
        val url = getWebhookUrl(recipient)
        val message = buildMessage(content, subject, recipient)
        webhookSender.send(url, message)
    }

    abstract fun buildMessage(content: String, subject: String, recipient: String): String

    abstract fun getWebhookUrl(recipient: String): String
}
