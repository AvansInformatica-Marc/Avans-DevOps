package nl.marc.devops.board

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import nl.marc.devops.board.task_states.CompletedTaskState
import nl.marc.devops.fixtures.UsersFixture
import nl.marc.devops.projects.Role
import kotlin.test.*

class TaskTests {
    @Test
    fun `FR-2_3) It should be possible to assign a developer to a task and re-assign it later`() {
        // Arrange
        val task = Task()
        val developers = UsersFixture.generateUsers(2)
        task.developer = developers.first()

        // Act
        task.developer = developers.last()

        // Assert
        assertEquals(developers.last(), task.developer)
    }

    @Test
    fun `FR-2_8, 1) A test should be able to move from planned to done when it is never passed back`() {
        // Arrange
        val task = Task()

        // Act
        task.startDevelopment()
        task.setPlannedForTesting()
        task.setTestingInProgress()
        task.testingSucceeded()
        task.passesDefinitionOfDone()

        // Assert
        assertIs<CompletedTaskState>(task.state)
        assertTrue(task.isComplete)
    }

    @Test
    fun `FR-2_8, 2) A test should be able to move from planned to done even when it fails testing once`() {
        // Arrange
        val task = Task()

        // Act
        task.startDevelopment()
        task.setPlannedForTesting()
        task.setTestingInProgress()
        task.testingFailed()
        task.startDevelopment()
        task.setDevelopmentCompleted()
        task.setTestingInProgress()
        task.testingSucceeded()
        task.passesDefinitionOfDone()

        // Assert
        assertIs<CompletedTaskState>(task.state)
        assertTrue(task.isComplete)
    }

    @Test
    fun `FR-2_8, 3) A test should be able to move from planned to done even when it fails the DoD once`() {
        // Arrange
        val task = Task()

        // Act
        task.startDevelopment()
        task.setPlannedForTesting()
        task.setTestingInProgress()
        task.testingSucceeded()
        task.failedDefinitionOfDone()
        task.setTestingInProgress()
        task.testingSucceeded()
        task.passesDefinitionOfDone()

        // Assert
        assertIs<CompletedTaskState>(task.state)
        assertTrue(task.isComplete)
    }

    @Test
    fun `FR-2_8, 4) When a task is still planned, it should not be movable to done`() {
        // Arrange
        val task = Task()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            task.passesDefinitionOfDone()
        }
    }

    @Test
    fun `FR-2_8, 5) When a task is still in progress, it should not be movable to done`() {
        // Arrange
        val task = Task()
        task.startDevelopment()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            task.passesDefinitionOfDone()
        }
    }

    @Test
    fun `FR-2_8, 6) When a task is still ready for testing, it should not be movable to done`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        task.setPlannedForTesting()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            task.passesDefinitionOfDone()
        }
    }

    @Test
    fun `FR-2_8, 7) When a task is still being tested, it should not be movable to done`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        task.setPlannedForTesting()
        task.setTestingInProgress()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            task.passesDefinitionOfDone()
        }
    }

    @Test
    fun `FR-2_8, 8) A planned task should not be able to move to ready for testing`() {
        // Arrange
        val task = Task()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            task.setPlannedForTesting()
        }
    }

    @Test
    fun `FR-2_8, 9) A task ready for testing should not be able to move to tested`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        task.setPlannedForTesting()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            task.testingSucceeded()
        }
    }

    @Test
    fun `FR-2_8, 10) A planned task should not be able to move to tested`() {
        // Arrange
        val task = Task()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            task.testingSucceeded()
        }
    }

    @Test
    fun `FR-2_8, 11) A task in development should not be able to move to tested`() {
        // Arrange
        val task = Task()
        task.startDevelopment()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            task.testingSucceeded()
        }
    }

    @Test
    fun `FR-2_8, 12) A task that is being tested should not be able to move back to in development`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        task.setDevelopmentCompleted()
        task.setTestingInProgress()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            task.startDevelopment()
        }
    }

    @Test
    fun `FR-2_8, 13) A task that has been tested should not be able to move back to in development`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        task.setDevelopmentCompleted()
        task.setTestingInProgress()
        task.testingSucceeded()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            task.startDevelopment()
        }
    }

    @Test
    fun `FR-2_8, 14) A task that has been completed should not be able to move back to testing`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        task.setDevelopmentCompleted()
        task.setTestingInProgress()
        task.testingSucceeded()
        task.passesDefinitionOfDone()

        // Act & Assert
        assertTrue(task.isComplete)
        assertFailsWith<IllegalStateException> {
            task.setTestingInProgress()
        }
    }

    @Test
    fun `FR-2_8, 15) A task that has been completed should not be able to move back to in development`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        task.setDevelopmentCompleted()
        task.setTestingInProgress()
        task.testingSucceeded()
        task.passesDefinitionOfDone()

        // Act & Assert
        assertTrue(task.isComplete)
        assertFailsWith<IllegalStateException> {
            task.startDevelopment()
        }
    }

    @Test
    fun `FR-2_9) Observers should be notified when a task is added to ready for testing`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        val observer = mockk<TaskStateObserver>()
        justRun { observer.notify(any()) }
        task.addObserver(observer)

        // Act
        task.setPlannedForTesting()

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
        val task = Task()
        task.startDevelopment()
        task.setPlannedForTesting()
        task.setTestingInProgress()
        val observer = mockk<TaskStateObserver>()
        justRun { observer.notify(any()) }
        task.addObserver(observer)

        // Act
        task.testingFailed()

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
