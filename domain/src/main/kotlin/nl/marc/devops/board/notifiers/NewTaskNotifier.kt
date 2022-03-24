package nl.marc.devops.board.notifiers

import nl.marc.devops.board.Task
import nl.marc.devops.board.TaskStateObserver
import nl.marc.devops.notifications.NotificationService
import nl.marc.devops.projects.GetUserByRoleService
import nl.marc.devops.projects.Role

class NewTaskNotifier(
    private val getUserByRoleService: GetUserByRoleService,
    private val notificationService: NotificationService
) : TaskStateObserver {
    fun notifyAssignment(task: Task, role: Role) {
        val users = getUserByRoleService.getUsersByRole(role)
        for (user in users) {
            notificationService.sendNotification(
                "Task \"${task.title}\" was assigned to your team.",
                "New task",
                user
            )
        }
    }

    override fun onTaskMovedBack(task: Task, oldRole: Role, newRole: Role) {
        notifyAssignment(task, newRole)
    }

    override fun onTaskChangedAssignment(task: Task, newAssignedRole: Role) {
        notifyAssignment(task, newAssignedRole)
    }
}
