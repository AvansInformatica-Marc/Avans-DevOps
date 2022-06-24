package nl.marc.devops.board.notifiers

import nl.marc.devops.accounts.User
import nl.marc.devops.board.TaskStateChange
import nl.marc.devops.board.TaskStateObserver
import nl.marc.devops.notifications.NotificationService

class TaskMovedBackScrumMasterNotifier(
    private val scrumMaster: User,
    private val notificationService: NotificationService
) : TaskStateObserver {
    override fun notify(taskStateChange: TaskStateChange) {
        if (taskStateChange.wasMovedBack) {
            notificationService.sendNotification(
                "Task \"${taskStateChange.task.title}\" moved from ${taskStateChange.oldRole?.name ?: "unknown"} to ${taskStateChange.newRole.name}.",
                "Task moved back",
                scrumMaster
            )
        }
    }
}
