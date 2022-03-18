package nl.marc.devops.notifications.sending_strategy

import nl.marc.devops.notifications.sending_apis.WebhookSender

class SlackNotificationStrategy(webhookSender: WebhookSender) : WebhookNotificationStrategy(webhookSender) {
    override fun buildMessage(content: String, subject: String, recipient: String): String {
        return "{\"message\": \"${subject}: ${content}\"}"
    }

    override fun getWebhookUrl(recipient: String): String {
        return "${BASE_URL}$recipient"
    }

    companion object {
        const val BASE_URL = "https://webhooks.slack.com/"

        const val CHANNEL_NAME = "SLACK"
    }
}
