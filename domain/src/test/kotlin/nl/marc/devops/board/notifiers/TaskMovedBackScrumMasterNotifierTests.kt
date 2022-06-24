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
    fun `AD-23, AD-141) Notifications should be sent to the scrum master`() {
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

    companion object {
        const val INVOCATION_KIND_ONCE = 1
    }
}
