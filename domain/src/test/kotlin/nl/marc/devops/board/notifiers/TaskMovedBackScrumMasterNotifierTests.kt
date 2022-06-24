package nl.marc.devops.board.notifiers

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import nl.marc.devops.board.Task
import nl.marc.devops.board.TaskStateChange
import nl.marc.devops.fixtures.UsersFixture
import nl.marc.devops.notifications.NotificationService
import nl.marc.devops.projects.Role
import org.junit.jupiter.api.Test

class TaskMovedBackScrumMasterNotifierTests {
    @Test
    fun `FR-1_10) The scrum master should be notified when a task has been moved backward`() {
        // Arrange
        val user = UsersFixture.defaultUser

        val notificationService = mockk<NotificationService>()
        justRun { notificationService.sendNotification(any(), any(), any()) }

        val notifier = TaskMovedBackScrumMasterNotifier(user, notificationService)

        val task = Task()
        task.title = "Test notifications"

        // Act
        notifier.notify(TaskStateChange(task, Role.TESTER, Role.DEVELOPERS, true))

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), user) }
    }
    @Test
    fun `FR-1_10) The scrum master should not be notified when a task has been moved forward`() {
        // Arrange
        val user = UsersFixture.defaultUser

        val notificationService = mockk<NotificationService>()
        justRun { notificationService.sendNotification(any(), any(), any()) }

        val notifier = TaskMovedBackScrumMasterNotifier(user, notificationService)

        val task = Task()
        task.title = "Test notifications"

        // Act
        notifier.notify(TaskStateChange(task, Role.TESTER, Role.DEVELOPERS, false))

        // Assert
        verify(exactly = INVOCATION_KIND_NEVER) { notificationService.sendNotification(any(), any(), user) }
    }

    companion object {
        const val INVOCATION_KIND_NEVER = 0

        const val INVOCATION_KIND_ONCE = 1
    }
}
