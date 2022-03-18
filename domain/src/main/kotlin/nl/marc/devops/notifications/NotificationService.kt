package nl.marc.devops.notifications

import nl.marc.devops.accounts.User

class NotificationService(
    private val notificationsRepository: NotificationsRepository,
    private val notificationServiceFactory: NotificationServiceFactory
) {
    fun sendNotification(content: String, subject: String, recipient: User) {
        val channels = notificationsRepository.getNotificationChannelsForUser(recipient)
        for (channel in channels) {
            val notificationStrategy = notificationServiceFactory.createNotificationStrategy(channel)
            notificationStrategy.sendNotification(content, subject, channel.recipient)
        }
    }

    fun addChannelsToUser(user: User, vararg channels: NotificationChannel) {
        notificationsRepository.saveNotificationChannelsForUser(user, channels.toSet())
    }
}
