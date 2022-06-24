package nl.marc.devops.board

import io.mockk.mockk
import nl.marc.devops.board.sprint_states.FinishedSprintState
import nl.marc.devops.fixtures.UsersFixture
import java.util.*
import kotlin.test.*

class SprintTests {
    @Test
    fun `FR-2_6) Tasks should be readable in a planned sprint state`() {
        // Arrange
        val sprint = Sprint()
        val task = Task()
        task.title = "Add backlog"
        sprint.addTask(task)

        // Act
        val tasks = sprint.tasks

        // Assert
        assertEquals(1, tasks.size)
        assertContains(tasks, task)
    }

    @Test
    fun `FR-2_6) Tasks should be readable in a running sprint state`() {
        // Arrange
        val sprint = Sprint()
        sprint.sprintInfo = mockk()
        val task = Task()
        task.title = "Add backlog"
        sprint.addTask(task)
        sprint.startSprint()

        // Act
        val tasks = sprint.tasks

        // Assert
        assertEquals(1, tasks.size)
        assertContains(tasks, task)
    }

    @Test
    fun `FR-2_6) Tasks should be readable in a completed sprint state`() {
        // Arrange
        val sprint = Sprint()
        sprint.sprintInfo = mockk()
        val task = Task()
        task.title = "Add backlog"
        sprint.addTask(task)
        sprint.startSprint()
        sprint.markFinished()

        // Act
        val tasks = sprint.tasks

        // Assert
        assertEquals(1, tasks.size)
        assertContains(tasks, task)
    }

    @Test
    fun `FR-2_6) When adding sprint information, the info should be readable when a sprint is still planned`() {
        // Arrange
        val sprint = Sprint()
        val sprintInfo = Sprint.Information(UsersFixture.defaultUser, "Sprint 1", Date(), Date())
        sprint.sprintInfo = sprintInfo

        // Act
        val info = sprint.sprintInfo

        // Assert
        assertEquals(info, sprintInfo)
    }

    @Test
    fun `FR-2_6) When adding sprint information, the info should be readable when a sprint is running`() {
        // Arrange
        val sprint = Sprint()
        val sprintInfo = Sprint.Information(UsersFixture.defaultUser, "Sprint 1", Date(), Date())
        sprint.sprintInfo = sprintInfo
        sprint.startSprint()

        // Act
        val info = sprint.sprintInfo

        // Assert
        assertEquals(info, sprintInfo)
    }

    @Test
    fun `FR-2_6) When adding sprint information, the info should be readable when a sprint is finished`() {
        // Arrange
        val sprint = Sprint()
        val sprintInfo = Sprint.Information(UsersFixture.defaultUser, "Sprint 1", Date(), Date())
        sprint.sprintInfo = sprintInfo
        sprint.startSprint()
        sprint.markFinished()

        // Act
        val info = sprint.sprintInfo

        // Assert
        assertEquals(info, sprintInfo)
    }


    @Test
    fun `FR-2_6) Adding backlog items to planned sprints should not cause a crash`() {
        // Arrange
        val sprint = Sprint()
        val task = Task()
        task.title = "Add backlog"

        // Act
        sprint.addTask(task)
        val tasks = sprint.tasks

        // Assert
        assertEquals(1, tasks.size)
        assertContains(tasks, task)
    }

    @Test
    fun `FR-2_6) Adding sprint information to planned sprints should not cause a crash`() {
        // Arrange
        val sprint = Sprint()
        val sprintInfo = Sprint.Information(UsersFixture.defaultUser, "Sprint 1", Date(), Date())

        // Act
        sprint.sprintInfo = sprintInfo

        // Assert
        assertEquals(sprintInfo, sprint.sprintInfo)
    }

    @Test
    fun `FR-2_6) When adding backlog items to running sprints should throw an IllegalStateException`() {
        // Arrange
        val sprint = Sprint()
        sprint.sprintInfo = mockk()
        val task = Task()
        task.title = "Add backlog"
        sprint.startSprint()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            sprint.addTask(task)
        }
    }

    @Test
    fun `FR-2_6) When adding sprint information to running sprints should throw an IllegalStateException`() {
        // Arrange
        val sprint = Sprint()
        sprint.sprintInfo = mockk()
        val sprintInfo = Sprint.Information(UsersFixture.defaultUser, "Sprint 1", Date(), Date())
        sprint.startSprint()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            sprint.sprintInfo = sprintInfo
        }
        assertNotEquals(sprintInfo, sprint.sprintInfo)
    }

    @Test
    fun `FR-2_6) When adding backlog items to finished sprints should throw an IllegalStateException`() {
        // Arrange
        val sprint = Sprint()
        sprint.sprintInfo = mockk()
        val task = Task()
        task.title = "Add backlog"
        sprint.startSprint()
        sprint.markFinished()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            sprint.addTask(task)
        }
    }

    @Test
    fun `FR-2_6) When adding sprint information to finished sprints should throw an IllegalStateException`() {
        // Arrange
        val sprint = Sprint()
        sprint.sprintInfo = mockk()
        val sprintInfo = Sprint.Information(UsersFixture.defaultUser, "Sprint 1", Date(), Date())
        sprint.startSprint()
        sprint.markFinished()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            sprint.sprintInfo = sprintInfo
        }
        assertNotEquals(sprintInfo, sprint.sprintInfo)
    }

    @Test
    fun `FR-2_6) A sprint should be able to start and finish`() {
        // Arrange
        val sprint = Sprint()
        sprint.sprintInfo = mockk()

        // Act
        sprint.startSprint()
        sprint.markFinished()

        // Assert
        assertIs<FinishedSprintState>(sprint.state)
    }

    @Test
    fun `FR-2_7) Closing a planned sprint should throw an IllegalStateException`() {
        // Arrange
        val sprint = Sprint()
        sprint.sprintInfo = mockk()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            sprint.markFinished()
        }
    }

    @Test
    fun `FR-2_7) Opening a closed sprint should throw an IllegalStateException`() {
        // Arrange
        val sprint = Sprint()
        sprint.sprintInfo = mockk()
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
        sprint.sprintInfo = mockk()
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
        sprint.sprintInfo = mockk()
        sprint.startSprint()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            sprint.startSprint()
        }
    }

    @Test
    fun `FR-2_5) When adding sprint information in the sprint builder, the info should be readable when a sprint is still planned`() {
        // Arrange
        val scrumMaster = UsersFixture.defaultUser
        val sprintName = "Sprint 1"
        val sprint = Sprint.Builder()
            .setScrumMaster(scrumMaster)
            .setSprintName(sprintName)
            .setBeginDate(Date())
            .setEndDate(Date())
            .build()

        // Act
        val info = sprint.sprintInfo

        // Assert
        assertNotNull(info)
        assertEquals(info.scrumMaster, scrumMaster)
        assertEquals(info.name, sprintName)
    }

    @Test
    fun `FR-2_5) Missing the scrum master in the builder causes a NullPointerException`() {
        // Arrange
        val sprintBuilder = Sprint.Builder()
            .setSprintName("Sprint 2")

        // Act & Assert
        assertFailsWith<NullPointerException> {
            sprintBuilder.build()
        }
    }

    @Test
    fun `FR-2_5) Missing the sprint name in the builder causes a NullPointerException`() {
        // Arrange
        val sprintBuilder = Sprint.Builder()
            .setScrumMaster(UsersFixture.defaultUser)

        // Act & Assert
        assertFailsWith<NullPointerException> {
            sprintBuilder.build()
        }
    }
}
