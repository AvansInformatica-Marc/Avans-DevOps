package nl.marc.devops.notifications.sending_strategy

import nl.marc.devops.notifications.sending_apis.WebhookSender

class PrivateWebhookNotificationStrategy(webhookSender: WebhookSender) : WebhookNotificationStrategy(webhookSender) {
    override fun buildMessage(content: String, subject: String, recipient: String): String {
        return "{\"subject\": \"${subject}\", \"content\": ${content}\"}"
    }

    override fun getWebhookUrl(recipient: String): String {
        return recipient
    }

    companion object {
        const val CHANNEL_NAME = "PRIVATE_WEBHOOK"
    }
}
