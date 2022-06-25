package nl.marc.devops.forum

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import nl.marc.devops.board.Task
import nl.marc.devops.board.TaskStateChange
import nl.marc.devops.projects.Role
import java.util.*
import kotlin.test.Test

class ThreadAssociatedTaskCloseObserverTests {
    @Test
    fun `FR-3_2) When a backlog item is completed, the associated thread should be marked as read-only`() {
        // Arrange
        val backlogItem = mockk<Task>()
        val backlogId = UUID.randomUUID()
        every { backlogItem.isComplete } returns true
        every { backlogItem.id } returns backlogId

        val thread = Thread(backlogItem)

        val threadService = mockk<ThreadService>()
        every { threadService.getThreadByBacklogId(any()) } returns thread
        justRun { threadService.markInactive(any()) }

        val observable = ThreadAssociatedTaskCloseObserver(threadService)

        // Act
        observable.notify(TaskStateChange(backlogItem, null, Role.PRODUCT_OWNER, false))

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) {
            threadService.markInactive(backlogId)
        }
    }

    @Test
    fun `FR-3_2) When a backlog item is moved between states, the thread close observer should not invoke`() {
        // Arrange
        val backlogItem = mockk<Task>()
        every { backlogItem.isComplete } returns false

        val thread = Thread(backlogItem)

        val threadService = mockk<ThreadService>()
        every { threadService.getThreadByBacklogId(any()) } returns thread
        justRun { threadService.markInactive(any()) }

        val observable = ThreadAssociatedTaskCloseObserver(threadService)

        // Act
        observable.notify(TaskStateChange(backlogItem, Role.DEVELOPERS, Role.TESTER, false))

        // Assert
        verify(exactly = INVOCATION_KIND_NEVER) {
            threadService.markInactive(thread.id)
        }
    }

    companion object {
        const val INVOCATION_KIND_NEVER = 0

        const val INVOCATION_KIND_ONCE = 1
    }
}
