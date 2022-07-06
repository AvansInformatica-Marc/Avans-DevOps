package nl.marc.devops.board.notifiers

import nl.marc.devops.accounts.User
import nl.marc.devops.board.BacklogItemStateChange
import nl.marc.devops.board.BacklogItemStateObserver
import nl.marc.devops.notifications.NotificationService

class BacklogItemMovedBackScrumMasterNotifier(
    private val scrumMaster: User,
    private val notificationService: NotificationService
) : BacklogItemStateObserver {
    override fun notify(backlogItemStateChange: BacklogItemStateChange) {
        if (backlogItemStateChange.wasMovedBack) {
            val message = if (backlogItemStateChange.oldRole == null) {
                "Task \"${backlogItemStateChange.backlogItem.title}\" moved to ${backlogItemStateChange.newRole.name}."
            } else {
                "Task \"${backlogItemStateChange.backlogItem.title}\" moved from ${backlogItemStateChange.oldRole.name} to ${backlogItemStateChange.newRole.name}."
            }

            notificationService.sendNotification(
                message,
                "Task moved back",
                scrumMaster
            )
        }
    }
}
