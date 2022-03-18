package nl.marc.devops.notifications.sending_apis

interface EmailSender {
    fun sendEmail(
        from: String,
        to: String,
        subject: String,
        content: String
    )
}
