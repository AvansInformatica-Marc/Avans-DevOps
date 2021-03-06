package nl.marc.devops.board.notifiers

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import nl.marc.devops.accounts.User
import nl.marc.devops.board.BacklogItem
import nl.marc.devops.board.BacklogItemStateChange
import nl.marc.devops.fixtures.UsersFixture
import nl.marc.devops.notifications.NotificationService
import nl.marc.devops.projects.UserRoleService
import nl.marc.devops.projects.Role
import org.junit.jupiter.api.Test

class NewBacklogItemNotifierTests {
    @Test
    fun `FR-2_9, 1) The notifier should run without problems when no users are associated with the group`() {
        // Arrange
        val userRoleService = mockk<UserRoleService>()
        val users = emptySet<User>()
        every { userRoleService.getUsersByRole(any()) } returns users

        val notificationService = mockk<NotificationService>()
        justRun { notificationService.sendNotification(any(), any(), any()) }

        val notifier = NewBacklogItemNotifier(userRoleService, notificationService)

        val backlogItem = BacklogItem()
        backlogItem.title = "Test notifications"

        // Act
        notifier.notify(BacklogItemStateChange(backlogItem, null, Role.TESTER, false))

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { userRoleService.getUsersByRole(Role.TESTER) }
        verify(exactly = INVOCATION_KIND_NEVER) { notificationService.sendNotification(any(), any(), any()) }
    }

    @Test
    fun `FR-2_9, 2) Notifications should be sent to a single user when a task is moved forward and the group only has 1 user`() {
        // Arrange
        val userRoleService = mockk<UserRoleService>()
        val users = UsersFixture.generateUsers(1).toSet()
        every { userRoleService.getUsersByRole(any()) } returns users

        val notificationService = mockk<NotificationService>()
        justRun { notificationService.sendNotification(any(), any(), any()) }

        val notifier = NewBacklogItemNotifier(userRoleService, notificationService)

        val backlogItem = BacklogItem()
        backlogItem.title = "Test notifications"

        // Act
        notifier.notify(BacklogItemStateChange(backlogItem, null, Role.TESTER, false))

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { userRoleService.getUsersByRole(Role.TESTER) }
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), any()) }
    }

    @Test
    fun `FR-2_9, 3A) Notifications should be sent to all associated users when a task is moved forward`() {
        // Arrange
        val userRoleService = mockk<UserRoleService>()
        val users = UsersFixture.generateUsers(2).toSet()
        every { userRoleService.getUsersByRole(any()) } returns users

        val notificationService = mockk<NotificationService>()
        justRun { notificationService.sendNotification(any(), any(), any()) }

        val notifier = NewBacklogItemNotifier(userRoleService, notificationService)

        val backlogItem = BacklogItem()
        backlogItem.title = "Test notifications"

        // Act
        notifier.notify(BacklogItemStateChange(backlogItem, null, Role.TESTER, false))

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { userRoleService.getUsersByRole(Role.TESTER) }
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), users.first()) }
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), users.last()) }
    }

    @Test
    fun `FR-2_9, 3B) Notifications should be sent to all associated users when a task is moved back`() {
        // Arrange
        val userRoleService = mockk<UserRoleService>()
        val users = UsersFixture.generateUsers(2).toSet()
        every { userRoleService.getUsersByRole(any()) } returns users

        val notificationService = mockk<NotificationService>()
        justRun { notificationService.sendNotification(any(), any(), any()) }

        val notifier = NewBacklogItemNotifier(userRoleService, notificationService)

        val backlogItem = BacklogItem()
        backlogItem.title = "Test notifications"

        // Act
        notifier.notify(BacklogItemStateChange(backlogItem, Role.DEVELOPERS, Role.TESTER, true))

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { userRoleService.getUsersByRole(Role.TESTER) }
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), users.first()) }
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), users.last()) }
    }

    companion object {
        const val INVOCATION_KIND_NEVER = 0

        const val INVOCATION_KIND_ONCE = 1
    }
}
