package nl.marc.devops.board

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import nl.marc.devops.board.task_states.*
import nl.marc.devops.fixtures.UsersFixture
import nl.marc.devops.projects.Role
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class TaskTests {
    @Test
    fun `AD-9, AD-124) It should be possible to assign a developer to a task and re-assign it later`() {
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
    fun `AD-19, AD-125) The default task state should be planned`() {
        // Arrange/Act
        val task = Task()

        // Assert
        assertIs<PlannedTaskState>(task.state)
    }

    @Test
    fun `AD-21, AD-126) Tasks are movable from planned to in development`() {
        // Arrange
        val task = Task()

        // Act
        task.startDevelopment()

        // Assert
        assertIs<InDevelopmentTaskState>(task.state)
    }

    @Test
    fun `AD-21, AD-127) Tasks are movable from in development to ready for testing`() {
        // Arrange
        val task = Task()
        task.startDevelopment()

        // Act
        task.setDevelopmentCompleted()

        // Assert
        assertIs<ReadyForTestingTaskState>(task.state)
    }

    @Test
    fun `AD-21, AD-128) Tasks are movable from ready for testing to testing`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        task.setPlannedForTesting()

        // Act
        task.setTestingInProgress()

        // Assert
        assertIs<TestingInProgressTaskState>(task.state)
    }

    @Test
    fun `AD-21, AD-129) Tasks are movable from testing to tested`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        task.setPlannedForTesting()
        task.setTestingInProgress()

        // Act
        task.testingSucceeded()

        // Assert
        assertIs<TestingCompleteTaskState>(task.state)
    }

    @Test
    fun `AD-26, AD-132) Tasks are movable from tested to done when passing the DoD`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        task.setPlannedForTesting()
        task.setTestingInProgress()
        task.testingSucceeded()

        // Act
        task.passesDefinitionOfDone()

        // Assert
        assertIs<CompletedTaskState>(task.state)
    }

    @Test
    fun `AD-22, AD-131) Tasks where testing is in progress should be able to fail and move back to planned for development`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        task.setPlannedForTesting()
        task.setTestingInProgress()

        // Act
        task.testingFailed()

        // Assert
        assertIs<PlannedTaskState>(task.state)
    }

    @Test
    fun `AD-29, AD-133) Tasks are movable from tested to ready for testing when failing the DoD`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        task.setPlannedForTesting()
        task.setTestingInProgress()
        task.testingSucceeded()

        // Act
        task.failedDefinitionOfDone()

        // Assert
        assertIs<ReadyForTestingTaskState>(task.state)
    }

    @Test
    fun `AD-26, AD-135) When a task is still planned, it should not be movable to done`() {
        // Arrange
        val task = Task()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            task.passesDefinitionOfDone()
        }
    }

    @Test
    fun `AD-26, AD-135) When a task is still in progress, it should not be movable to done`() {
        // Arrange
        val task = Task()
        task.startDevelopment()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            task.passesDefinitionOfDone()
        }
    }

    @Test
    fun `AD-26, AD-135) When a task is still ready for testing, it should not be movable to done`() {
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
    fun `AD-26, AD-135) When a task is still being tested, it should not be movable to done`() {
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
    fun `AD-134, AD-136) A planned task should not be able to move to ready for testing`() {
        // Arrange
        val task = Task()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            task.setPlannedForTesting()
        }
    }

    @Test
    fun `AD-134, AD-137) A task ready for testing should not be able to move to tested`() {
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
    fun `AD-20, AD-138) Observers should be notified when a task is added to ready for testing`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        val observer = mockk<TaskStateObserver>()
        justRun { observer.onTaskChangedAssignment(any(), any()) }
        task.addObserver(observer)

        // Act
        task.setPlannedForTesting()

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { observer.onTaskChangedAssignment(any(), Role.TESTER) }
    }

    @Test
    fun `AD-23, AD-140) Observers should be notified when a task is moved back`() {
        // Arrange
        val task = Task()
        task.startDevelopment()
        task.setPlannedForTesting()
        task.setTestingInProgress()
        val observer = mockk<TaskStateObserver>()
        justRun { observer.onTaskChangedAssignment(any(), any()) }
        justRun { observer.onTaskMovedBack(any(), any(), any()) }
        task.addObserver(observer)

        // Act
        task.testingFailed()

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { observer.onTaskMovedBack(any(), Role.TESTER, Role.DEVELOPERS) }
    }

    companion object {
        const val INVOCATION_KIND_ONCE = 1
    }
}
