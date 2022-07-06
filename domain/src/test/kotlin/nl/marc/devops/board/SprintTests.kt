package nl.marc.devops.board

import nl.marc.devops.board.sprint_states.CompletedSprintState
import nl.marc.devops.board.sprint_states.FinishedSprintState
import nl.marc.devops.fixtures.UsersFixture
import kotlin.test.*

class SprintTests {
    @Test
    fun `FR-2_6) Tasks should be readable in a planned sprint state`() {
        // Arrange
        val sprint = Sprint()
        val backlogItem = BacklogItem()
        backlogItem.title = "Add backlog"
        sprint.addTask(backlogItem)

        // Act
        val tasks = sprint.backlogItems

        // Assert
        assertEquals(1, tasks.size)
        assertContains(tasks, backlogItem)
    }

    @Test
    fun `FR-2_6) Tasks should be readable in a running sprint state`() {
        // Arrange
        val sprint = Sprint()
        val backlogItem = BacklogItem()
        backlogItem.title = "Add backlog"
        sprint.addTask(backlogItem)
        sprint.startSprint()

        // Act
        val tasks = sprint.backlogItems

        // Assert
        assertEquals(1, tasks.size)
        assertContains(tasks, backlogItem)
    }

    @Test
    fun `FR-2_6) Tasks should be readable in a completed sprint state`() {
        // Arrange
        val sprint = Sprint()
        val backlogItem = BacklogItem()
        backlogItem.title = "Add backlog"
        sprint.addTask(backlogItem)
        sprint.startSprint()
        sprint.markFinished()

        // Act
        val tasks = sprint.backlogItems

        // Assert
        assertEquals(1, tasks.size)
        assertContains(tasks, backlogItem)
    }

    @Test
    fun `FR-2_6) Sprint information should be readable when a sprint is in a started state`() {
        // Arrange
        val sprint = Sprint()
        sprint.name = "Sprint 1"
        sprint.scrumMaster = UsersFixture.defaultUser

        // Act
        val name = sprint.name
        val scrumMaster = sprint.scrumMaster

        // Assert
        assertEquals(name, "Sprint 1")
        assertEquals(scrumMaster, UsersFixture.defaultUser)
    }

    @Test
    fun `FR-2_6) Sprint information should be readable when a sprint is in a running state`() {
        // Arrange
        val sprint = Sprint()
        sprint.name = "Sprint 1"
        sprint.scrumMaster = UsersFixture.defaultUser
        sprint.startSprint()

        // Act
        val name = sprint.name
        val scrumMaster = sprint.scrumMaster

        // Assert
        assertEquals(name, "Sprint 1")
        assertEquals(scrumMaster, UsersFixture.defaultUser)
    }

    @Test
    fun `FR-2_6) Sprint information should be readable when a sprint is in a finished state`() {
        // Arrange
        val sprint = Sprint()
        sprint.name = "Sprint 1"
        sprint.scrumMaster = UsersFixture.defaultUser
        sprint.startSprint()
        sprint.markFinished()

        // Act
        val name = sprint.name
        val scrumMaster = sprint.scrumMaster

        // Assert
        assertEquals(name, "Sprint 1")
        assertEquals(scrumMaster, UsersFixture.defaultUser)
    }

    @Test
    fun `FR-2_6) Adding backlog items should not cause a crash when a sprint is in a started state`() {
        // Arrange
        val sprint = Sprint()
        val backlogItem = BacklogItem()
        backlogItem.title = "Add backlog"

        // Act
        sprint.addTask(backlogItem)
        val tasks = sprint.backlogItems

        // Assert
        assertEquals(1, tasks.size)
        assertContains(tasks, backlogItem)
    }

    @Test
    fun `FR-2_6) Sprint information should be writeable when a sprint is in a started state`() {
        // Arrange
        val sprint = Sprint()

        // Act
        sprint.name = "Sprint 1"
        sprint.scrumMaster = UsersFixture.defaultUser

        // Assert
        assertEquals(sprint.name, "Sprint 1")
        assertEquals(sprint.scrumMaster, UsersFixture.defaultUser)
    }

    @Test
    fun `FR-2_6) Adding backlog items should throw an IllegalStateException when a sprint is in a running state`() {
        // Arrange
        val sprint = Sprint()
        val backlogItem = BacklogItem()
        backlogItem.title = "Add backlog"
        sprint.startSprint()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            sprint.addTask(backlogItem)
        }
    }

    @Test
    fun `FR-2_6) Changing sprint information should throw an IllegalStateException when a sprint is in a running state`() {
        // Arrange
        val sprint = Sprint()
        sprint.startSprint()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            sprint.name = "Sprint 1"
            sprint.scrumMaster = UsersFixture.defaultUser
        }
        assertNull(sprint.name)
        assertNull(sprint.scrumMaster)
    }

    @Test
    fun `FR-2_6) Adding backlog items should throw an IllegalStateException when a sprint is in a finished state`() {
        // Arrange
        val sprint = Sprint()
        val backlogItem = BacklogItem()
        backlogItem.title = "Add backlog"
        sprint.startSprint()
        sprint.markFinished()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            sprint.addTask(backlogItem)
        }
    }

    @Test
    fun `FR-2_6) Changing sprint information should throw an IllegalStateException when a sprint is in a finished state`() {
        // Arrange
        val sprint = Sprint()
        sprint.startSprint()
        sprint.markFinished()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            sprint.name = "Sprint 1"
            sprint.scrumMaster = UsersFixture.defaultUser
        }
        assertNull(sprint.name)
        assertNull(sprint.scrumMaster)
    }

    @Test
    fun `FR-2_6) A sprint should be able to start and finish`() {
        // Arrange
        val sprint = Sprint()

        // Act
        sprint.startSprint()
        sprint.markFinished()

        // Assert
        assertIs<FinishedSprintState>(sprint.state)
    }

    @Test
    fun `FR-2_6) A sprint should be able to complete by pipeline when started and finished`() {
        // Arrange
        val sprint = Sprint()

        // Act
        sprint.startSprint()
        sprint.markFinished()
        sprint.onPipelineCompleted()

        // Assert
        assertIs<CompletedSprintState>(sprint.state)
    }

    @Test
    fun `FR-2_6) A sprint should be able to complete by document upload when started and finished`() {
        // Arrange
        val sprint = Sprint()

        // Act
        sprint.startSprint()
        sprint.markFinished()
        sprint.onDocumentAttached()

        // Assert
        assertIs<CompletedSprintState>(sprint.state)
    }

    @Test
    fun `FR-2_7) Closing a planned sprint should throw an IllegalStateException`() {
        // Arrange
        val sprint = Sprint()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            sprint.markFinished()
        }
    }

    @Test
    fun `FR-2_7) Opening a closed sprint should throw an IllegalStateException`() {
        // Arrange
        val sprint = Sprint()
        sprint.startSprint()
        sprint.markFinished()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            sprint.startSprint()
        }
    }

    @Test
    fun `FR-2_7) Closing a closed sprint should throw an IllegalStateException`() {
        // Arrange
        val sprint = Sprint()
        sprint.startSprint()
        sprint.markFinished()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            sprint.markFinished()
        }
    }

    @Test
    fun `FR-2_6) Starting a running sprint should throw an IllegalStateException`() {
        // Arrange
        val sprint = Sprint()
        sprint.startSprint()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            sprint.startSprint()
        }
    }
}
