package nl.marc.devops.board

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import nl.marc.devops.board.task_states.CompletedBacklogItemState
import nl.marc.devops.fixtures.UsersFixture
import nl.marc.devops.projects.Role
import kotlin.test.*

class BaclogItemTests {
    @Test
    fun `FR-2_3) It should be possible to assign a developer to a task and re-assign it later`() {
        // Arrange
        val backlogItem = BacklogItem()
        val developers = UsersFixture.generateUsers(2)
        backlogItem.developer = developers.first()

        // Act
        backlogItem.developer = developers.last()

        // Assert
        assertEquals(developers.last(), backlogItem.developer)
    }

    @Test
    fun `FR-2_8, 1) A test should be able to move from planned to done when it is never passed back`() {
        // Arrange
        val backlogItem = BacklogItem()

        // Act
        backlogItem.startDevelopment()
        backlogItem.setPlannedForTesting()
        backlogItem.setTestingInProgress()
        backlogItem.testingSucceeded()
        backlogItem.passesDefinitionOfDone()

        // Assert
        assertIs<CompletedBacklogItemState>(backlogItem.state)
        assertTrue(backlogItem.isComplete)
    }

    @Test
    fun `FR-2_8, 2) A test should be able to move from planned to done even when it fails testing once`() {
        // Arrange
        val backlogItem = BacklogItem()

        // Act
        backlogItem.startDevelopment()
        backlogItem.setPlannedForTesting()
        backlogItem.setTestingInProgress()
        backlogItem.testingFailed()
        backlogItem.startDevelopment()
        backlogItem.setDevelopmentCompleted()
        backlogItem.setTestingInProgress()
        backlogItem.testingSucceeded()
        backlogItem.passesDefinitionOfDone()

        // Assert
        assertIs<CompletedBacklogItemState>(backlogItem.state)
        assertTrue(backlogItem.isComplete)
    }

    @Test
    fun `FR-2_8, 3) A test should be able to move from planned to done even when it fails the DoD once`() {
        // Arrange
        val backlogItem = BacklogItem()

        // Act
        backlogItem.startDevelopment()
        backlogItem.setPlannedForTesting()
        backlogItem.setTestingInProgress()
        backlogItem.testingSucceeded()
        backlogItem.failedDefinitionOfDone()
        backlogItem.setTestingInProgress()
        backlogItem.testingSucceeded()
        backlogItem.passesDefinitionOfDone()

        // Assert
        assertIs<CompletedBacklogItemState>(backlogItem.state)
        assertTrue(backlogItem.isComplete)
    }

    @Test
    fun `FR-2_8, 4) When a task is still planned, it should not be movable to done`() {
        // Arrange
        val backlogItem = BacklogItem()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            backlogItem.passesDefinitionOfDone()
        }
    }

    @Test
    fun `FR-2_8, 5) When a task is still in progress, it should not be movable to done`() {
        // Arrange
        val backlogItem = BacklogItem()
        backlogItem.startDevelopment()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            backlogItem.passesDefinitionOfDone()
        }
    }

    @Test
    fun `FR-2_8, 6) When a task is still ready for testing, it should not be movable to done`() {
        // Arrange
        val backlogItem = BacklogItem()
        backlogItem.startDevelopment()
        backlogItem.setPlannedForTesting()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            backlogItem.passesDefinitionOfDone()
        }
    }

    @Test
    fun `FR-2_8, 7) When a task is still being tested, it should not be movable to done`() {
        // Arrange
        val backlogItem = BacklogItem()
        backlogItem.startDevelopment()
        backlogItem.setPlannedForTesting()
        backlogItem.setTestingInProgress()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            backlogItem.passesDefinitionOfDone()
        }
    }

    @Test
    fun `FR-2_8, 8) A planned task should not be able to move to ready for testing`() {
        // Arrange
        val backlogItem = BacklogItem()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            backlogItem.setPlannedForTesting()
        }
    }

    @Test
    fun `FR-2_8, 9) A task ready for testing should not be able to move to tested`() {
        // Arrange
        val backlogItem = BacklogItem()
        backlogItem.startDevelopment()
        backlogItem.setPlannedForTesting()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            backlogItem.testingSucceeded()
        }
    }

    @Test
    fun `FR-2_8, 10) A planned task should not be able to move to tested`() {
        // Arrange
        val backlogItem = BacklogItem()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            backlogItem.testingSucceeded()
        }
    }

    @Test
    fun `FR-2_8, 11) A task in development should not be able to move to tested`() {
        // Arrange
        val backlogItem = BacklogItem()
        backlogItem.startDevelopment()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            backlogItem.testingSucceeded()
        }
    }

    @Test
    fun `FR-2_8, 12) A task that is being tested should not be able to move back to in development`() {
        // Arrange
        val backlogItem = BacklogItem()
        backlogItem.startDevelopment()
        backlogItem.setDevelopmentCompleted()
        backlogItem.setTestingInProgress()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            backlogItem.startDevelopment()
        }
    }

    @Test
    fun `FR-2_8, 13) A task that has been tested should not be able to move back to in development`() {
        // Arrange
        val backlogItem = BacklogItem()
        backlogItem.startDevelopment()
        backlogItem.setDevelopmentCompleted()
        backlogItem.setTestingInProgress()
        backlogItem.testingSucceeded()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            backlogItem.startDevelopment()
        }
    }

    @Test
    fun `FR-2_8, 14) A task that has been completed should not be able to move back to testing`() {
        // Arrange
        val backlogItem = BacklogItem()
        backlogItem.startDevelopment()
        backlogItem.setDevelopmentCompleted()
        backlogItem.setTestingInProgress()
        backlogItem.testingSucceeded()
        backlogItem.passesDefinitionOfDone()

        // Act & Assert
        assertTrue(backlogItem.isComplete)
        assertFailsWith<IllegalStateException> {
            backlogItem.setTestingInProgress()
        }
    }

    @Test
    fun `FR-2_8, 15) A task that has been completed should not be able to move back to in development`() {
        // Arrange
        val backlogItem = BacklogItem()
        backlogItem.startDevelopment()
        backlogItem.setDevelopmentCompleted()
        backlogItem.setTestingInProgress()
        backlogItem.testingSucceeded()
        backlogItem.passesDefinitionOfDone()

        // Act & Assert
        assertTrue(backlogItem.isComplete)
        assertFailsWith<IllegalStateException> {
            backlogItem.startDevelopment()
        }
    }

    @Test
    fun `FR-2_9) Observers should be notified when a task is added to ready for testing`() {
        // Arrange
        val backlogItem = BacklogItem()
        backlogItem.startDevelopment()
        val observer = mockk<BacklogItemStateObserver>()
        justRun { observer.notify(any()) }
        backlogItem.addObserver(observer)

        // Act
        backlogItem.setPlannedForTesting()

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) {
            observer.notify(match {
                it.newRole == Role.TESTER && !it.wasMovedBack
            })
        }
    }

    @Test
    fun `FR-2_10) Observers should be notified when a task is moved back`() {
        // Arrange
        val backlogItem = BacklogItem()
        backlogItem.startDevelopment()
        backlogItem.setPlannedForTesting()
        backlogItem.setTestingInProgress()
        val observer = mockk<BacklogItemStateObserver>()
        justRun { observer.notify(any()) }
        backlogItem.addObserver(observer)

        // Act
        backlogItem.testingFailed()

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) {
            observer.notify(match {
                it.oldRole == Role.TESTER && it.newRole == Role.DEVELOPERS && it.wasMovedBack
            })
        }
    }

    companion object {
        const val INVOCATION_KIND_ONCE = 1
    }
}
