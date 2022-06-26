package nl.marc.devops.board.notifiers

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import nl.marc.devops.accounts.User
import nl.marc.devops.board.Task
import nl.marc.devops.board.TaskStateChange
import nl.marc.devops.fixtures.UsersFixture
import nl.marc.devops.notifications.NotificationService
import nl.marc.devops.projects.GetUserByRoleService
import nl.marc.devops.projects.Role
import org.junit.jupiter.api.Test

class NewTaskNotifierTests {
    @Test
    fun `FR-2_9, 1) The notifier should run without problems when no users are associated with the group`() {
        // Arrange
        val getUserByRoleService = mockk<GetUserByRoleService>()
        val users = emptySet<User>()
        every { getUserByRoleService.getUsersByRole(any()) } returns users

        val notificationService = mockk<NotificationService>()
        justRun { notificationService.sendNotification(any(), any(), any()) }

        val notifier = NewTaskNotifier(getUserByRoleService, notificationService)

        val task = Task()
        task.title = "Test notifications"

        // Act
        notifier.notify(TaskStateChange(task, null, Role.TESTER, false))

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { getUserByRoleService.getUsersByRole(Role.TESTER) }
        verify(exactly = INVOCATION_KIND_NEVER) { notificationService.sendNotification(any(), any(), any()) }
    }

    @Test
    fun `FR-2_9, 2) Notifications should be sent to a single user when a task is moved forward and the group only has 1 user`() {
        // Arrange
        val getUserByRoleService = mockk<GetUserByRoleService>()
        val users = UsersFixture.generateUsers(1).toSet()
        every { getUserByRoleService.getUsersByRole(any()) } returns users

        val notificationService = mockk<NotificationService>()
        justRun { notificationService.sendNotification(any(), any(), any()) }

        val notifier = NewTaskNotifier(getUserByRoleService, notificationService)

        val task = Task()
        task.title = "Test notifications"

        // Act
        notifier.notify(TaskStateChange(task, null, Role.TESTER, false))

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { getUserByRoleService.getUsersByRole(Role.TESTER) }
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), any()) }
    }

    @Test
    fun `FR-2_9, 3A) Notifications should be sent to all associated users when a task is moved forward`() {
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
        notifier.notify(TaskStateChange(task, null, Role.TESTER, false))

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { getUserByRoleService.getUsersByRole(Role.TESTER) }
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), users.first()) }
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), users.last()) }
    }

    @Test
    fun `FR-2_9, 3B) Notifications should be sent to all associated users when a task is moved back`() {
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
        notifier.notify(TaskStateChange(task, Role.DEVELOPERS, Role.TESTER, true))

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { getUserByRoleService.getUsersByRole(Role.TESTER) }
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), users.first()) }
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), users.last()) }
    }

    companion object {
        const val INVOCATION_KIND_NEVER = 0

        const val INVOCATION_KIND_ONCE = 1
    }
}
