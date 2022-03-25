package nl.marc.devops.board.notifiers

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import nl.marc.devops.board.Task
import nl.marc.devops.fixtures.UsersFixture
import nl.marc.devops.notifications.NotificationService
import nl.marc.devops.projects.GetUserByRoleService
import nl.marc.devops.projects.Role
import org.junit.jupiter.api.Test

class NewTaskNotifierTests {
    @Test
    fun `AD-20, AD-139) Notifications should be sent to all users`() {
        // Arrange
        val getUserByRoleService = mockk<GetUserByRoleService>()
        val users = UsersFixture.generateUsers(2).toSet()
        every { getUserByRoleService.getUsersByRole(any()) } returns users

        val notificationService = mockk<NotificationService>()
        justRun { notificationService.sendNotification(any(), any(), any()) }

        val notifier = NewTaskNotifier(getUserByRoleService, notificationService)

        val task = Task()
        task.title = "Test notifications"

        // Act
        notifier.onTaskChangedAssignment(task, Role.TESTER)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { getUserByRoleService.getUsersByRole(Role.TESTER) }
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), users.first()) }
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), users.last()) }
    }

    @Test
    fun `AD-20, AD-142) Notifications should be sent to all users when a task is moved back`() {
        // Arrange
        val getUserByRoleService = mockk<GetUserByRoleService>()
        val users = UsersFixture.generateUsers(2).toSet()
        every { getUserByRoleService.getUsersByRole(any()) } returns users

        val notificationService = mockk<NotificationService>()
        justRun { notificationService.sendNotification(any(), any(), any()) }

        val notifier = NewTaskNotifier(getUserByRoleService, notificationService)

        val task = Task()
        task.title = "Test notifications"

        // Act
        notifier.onTaskMovedBack(task, Role.DEVELOPERS, Role.TESTER)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { getUserByRoleService.getUsersByRole(Role.TESTER) }
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), users.first()) }
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), users.last()) }
    }

    companion object {
        const val INVOCATION_KIND_ONCE = 1
    }
}
