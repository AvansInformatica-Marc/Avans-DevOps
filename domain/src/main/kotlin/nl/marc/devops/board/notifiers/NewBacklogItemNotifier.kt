package nl.marc.devops.board.notifiers

import nl.marc.devops.board.BacklogItemStateChange
import nl.marc.devops.board.BacklogItemStateObserver
import nl.marc.devops.notifications.NotificationService
import nl.marc.devops.projects.GetUserByRoleService

class NewBacklogItemNotifier(
    private val getUserByRoleService: GetUserByRoleService,
    private val notificationService: NotificationService
) : BacklogItemStateObserver {
    override fun notify(backlogItemStateChange: BacklogItemStateChange) {
        val users = getUserByRoleService.getUsersByRole(backlogItemStateChange.newRole)
        for (user in users) {
            notificationService.sendNotification(
                "Task \"${backlogItemStateChange.backlogItem.title}\" was assigned to your team.",
                "New task",
                user
            )
        }
    }
}
