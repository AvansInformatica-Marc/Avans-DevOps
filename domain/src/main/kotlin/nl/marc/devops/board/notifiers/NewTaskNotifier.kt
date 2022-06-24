package nl.marc.devops.board.notifiers

import nl.marc.devops.board.TaskStateChange
import nl.marc.devops.board.TaskStateObserver
import nl.marc.devops.notifications.NotificationService
import nl.marc.devops.projects.GetUserByRoleService

class NewTaskNotifier(
    private val getUserByRoleService: GetUserByRoleService,
    private val notificationService: NotificationService
) : TaskStateObserver {
    override fun notify(taskStateChange: TaskStateChange) {
        val users = getUserByRoleService.getUsersByRole(taskStateChange.newRole)
        for (user in users) {
            notificationService.sendNotification(
                "Task \"${taskStateChange.task.title}\" was assigned to your team.",
                "New task",
                user
            )
        }
    }
}
