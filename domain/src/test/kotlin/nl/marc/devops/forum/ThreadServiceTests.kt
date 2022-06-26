package nl.marc.devops.forum

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import nl.marc.devops.board.Task
import java.util.*
import kotlin.test.Test

class ThreadServiceTests {
    @Test
    fun `FR-3_2) When a backlog item is completed, the associated thread should not be deleted but marked as inactive`() {
        // Arrange
        val backlogItem = mockk<Task>()
        val backlogId = UUID.randomUUID()
        every { backlogItem.isComplete } returns true
        every { backlogItem.id } returns backlogId

        val thread = Thread(backlogItem)

        val threadRepository = mockk<ThreadRepository>()
        val threadService = ThreadService(threadRepository)
        every { threadRepository.getThreadByBacklogId(any()) } returns thread
        justRun { threadRepository.updateThread(any()) }

        // Act
        threadService.markInactive(backlogId)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) {
            threadRepository.updateThread(match {
                it.id == thread.id && it.backlogItem == thread.backlogItem && !it.isActive && it.messages == thread.messages
            })
        }
    }

    companion object {
        const val INVOCATION_KIND_ONCE = 1
    }
}
