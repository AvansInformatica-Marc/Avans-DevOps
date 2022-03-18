package nl.marc.devops.notifications.sending_strategy

interface SendNotificationStrategy {
    fun sendNotification(content: String, subject: String, recipient: String)
}
