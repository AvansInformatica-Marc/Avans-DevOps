package nl.marc.devops.notifications

import nl.marc.devops.accounts.User

interface NotificationsRepository {
    fun getNotificationChannelsForUser(user: User): Set<NotificationChannel>

    fun saveNotificationChannelsForUser(user: User, channels: Set<NotificationChannel>)
}
