package nl.marc.devops.notifications

import java.util.*

data class NotificationChannel(
    val channelName: String,
    val recipient: String,
    val id: UUID = UUID.randomUUID()
)
