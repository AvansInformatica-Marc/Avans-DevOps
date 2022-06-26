package nl.marc.devops.notifications

import nl.marc.devops.accounts.User

interface NotificationService {
    fun sendNotification(content: String, subject: String, recipient: User)

    fun addChannelsToUser(user: User, vararg channels: NotificationChannel)
}
