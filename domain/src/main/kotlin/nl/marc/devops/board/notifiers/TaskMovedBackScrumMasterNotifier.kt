package nl.marc.devops.board.notifiers

import nl.marc.devops.accounts.User
import nl.marc.devops.board.Task
import nl.marc.devops.board.TaskStateObserver
import nl.marc.devops.notifications.NotificationService
import nl.marc.devops.projects.Role

class TaskMovedBackScrumMasterNotifier(
    private val scrumMaster: User,
    private val notificationService: NotificationService
) : TaskStateObserver {
    override fun onTaskMovedBack(task: Task, oldRole: Role, newRole: Role) {
        notificationService.sendNotification(
            "Task \"${task.title}\" moved from ${oldRole.name} to ${newRole.name}.",
            "Task moved back",
            scrumMaster
        )
    }

    override fun onTaskChangedAssignment(task: Task, newAssignedRole: Role) {
        // Ignore: scrum master shouldn't be notified of this
    }
}
