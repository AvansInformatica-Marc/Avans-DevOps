package nl.marc.devops.board.notifiers

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import nl.marc.devops.board.BacklogItem
import nl.marc.devops.board.BacklogItemStateChange
import nl.marc.devops.fixtures.UsersFixture
import nl.marc.devops.notifications.NotificationService
import nl.marc.devops.projects.Role
import org.junit.jupiter.api.Test

class BacklogItemMovedBackScrumMasterNotifierTests {
    @Test
    fun `FR-1_10) The scrum master should be notified when a task has been moved backward`() {
        // Arrange
        val user = UsersFixture.defaultUser

        val notificationService = mockk<NotificationService>()
        justRun { notificationService.sendNotification(any(), any(), any()) }

        val notifier = BacklogItemMovedBackScrumMasterNotifier(user, notificationService)

        val backlogItem = BacklogItem()
        backlogItem.title = "Test notifications"

        // Act
        notifier.notify(BacklogItemStateChange(backlogItem, Role.TESTER, Role.DEVELOPERS, true))

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), user) }
    }

    @Test
    fun `FR-1_10) The scrum master should not be notified when a task has been moved forward`() {
        // Arrange
        val user = UsersFixture.defaultUser

        val notificationService = mockk<NotificationService>()
        justRun { notificationService.sendNotification(any(), any(), any()) }

        val notifier = BacklogItemMovedBackScrumMasterNotifier(user, notificationService)

        val backlogItem = BacklogItem()
        backlogItem.title = "Test notifications"

        // Act
        notifier.notify(BacklogItemStateChange(backlogItem, Role.TESTER, Role.DEVELOPERS, false))

        // Assert
        verify(exactly = INVOCATION_KIND_NEVER) { notificationService.sendNotification(any(), any(), user) }
    }

    @Test
    fun `FR-1_10) The scrum master should be notified when a task has been moved backward and does not have an origin role assigned`() {
        // Arrange
        val user = UsersFixture.defaultUser

        val notificationService = mockk<NotificationService>()
        justRun { notificationService.sendNotification(any(), any(), any()) }

        val notifier = BacklogItemMovedBackScrumMasterNotifier(user, notificationService)

        val backlogItem = BacklogItem()
        backlogItem.title = "Test notifications"

        // Act
        notifier.notify(BacklogItemStateChange(backlogItem, null, Role.DEVELOPERS, true))

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { notificationService.sendNotification(any(), any(), user) }
    }

    companion object {
        const val INVOCATION_KIND_NEVER = 0

        const val INVOCATION_KIND_ONCE = 1
    }
}
