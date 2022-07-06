package nl.marc.devops.notifications

import nl.marc.devops.accounts.User

class NotificationServiceImpl(
    private val notificationsRepository: NotificationsRepository
) : NotificationService {
    override fun sendNotification(content: String, subject: String, recipient: User) {
        val channels = notificationsRepository.getNotificationChannelsForUser(recipient)
        for (channel in channels) {
            channel.sender.sendNotification(content, subject, channel.recipient)
        }
    }

    override fun addChannelsToUser(user: User, vararg channels: NotificationChannel) {
        notificationsRepository.saveNotificationChannelsForUser(user, channels.toSet())
    }
}
