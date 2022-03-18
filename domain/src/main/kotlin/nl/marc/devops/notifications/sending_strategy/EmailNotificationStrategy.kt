package nl.marc.devops.notifications.sending_strategy

import nl.marc.devops.notifications.sending_apis.EmailSender

class EmailNotificationStrategy(private val emailSender: EmailSender) : SendNotificationStrategy {
    override fun sendNotification(content: String, subject: String, recipient: String) {
        emailSender.sendEmail(
            "noreply@avans-devops.nl",
            recipient,
            subject,
            content
        )
    }

    companion object {
        const val CHANNEL_NAME = "EMAIL"
    }
}
