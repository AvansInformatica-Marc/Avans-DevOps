package nl.marc.devops.notifications.sending_apis

interface WebhookSender {
    fun send(url: String, content: String)
}
