package nl.marc.devops.notifications

import nl.marc.devops.notifications.sending_strategy.SendNotificationStrategy
import java.util.*

data class NotificationChannel(
    val sender: SendNotificationStrategy,
    val recipient: String,
    val id: UUID = UUID.randomUUID()
)
