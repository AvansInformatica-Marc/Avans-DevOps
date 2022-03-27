package nl.marc.devops.notifications.sending_strategy

import nl.marc.devops.notifications.sending_apis.WebhookSender

class PrivateWebhookNotificationStrategy(webhookSender: WebhookSender) : WebhookNotificationStrategy(webhookSender) {
    override fun buildMessage(content: String, subject: String, recipient: String): String {
        return "<message>\n" +
                "  <subject>$subject</subject>\n" +
                "  <content>$content</content>\n" +
                "</message>"
    }

    override fun getWebhookUrl(recipient: String): String {
        return recipient
    }

    companion object {
        const val CHANNEL_NAME = "PRIVATE_WEBHOOK"
    }
}
