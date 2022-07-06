package nl.marc.devops.projects

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import nl.marc.devops.fixtures.UsersFixture
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ProjectsServiceTests {
    @Test
    fun `FR-1_3) Creating a project assigns the current user as product owner`() {
        // Arrange
        val user = UsersFixture.defaultUser
        val project = Project("MyTestProject")
        val projectRepository = mockk<ProjectRepository>()
        val capturedProject = slot<Project>()
        every { projectRepository.addProject(capture(capturedProject)) } answers { capturedProject.captured }
        val projectsService = ProjectsService(projectRepository)

        // Act
        val createdProject = projectsService.createProject(user, project)

        // Assert
        assertEquals(mapOf(user to Role.PRODUCT_OWNER), createdProject.users)
    }

    @Test
    fun `FR-1_5, 1) When a project has a product owner assigned, addUserToProject with product owner role should throw error`() {
        // Arrange
        val users = UsersFixture.generateUsers(2)
        val project = Project("MyTestProject", mutableMapOf(users[0] to Role.PRODUCT_OWNER))
        val projectRepository = mockk<ProjectRepository>()
        val projectsService = ProjectsService(projectRepository)

        // Act & assert
        assertFailsWith(IllegalStateException::class) {
            projectsService.addUserToProject(users[1], Role.PRODUCT_OWNER, project)
        }
    }

    @Test
    fun `FR-1_5, 4) When a project has no lead developer assigned, addUserToProject should update repository`() {
        // Arrange
        val users = UsersFixture.generateUsers(2)
        val project = Project("MyTestProject", mutableMapOf(users[0] to Role.PRODUCT_OWNER))
        val projectRepository = mockk<ProjectRepository>()
        val capturedProject = slot<Project>()
        every { projectRepository.updateProject(capture(capturedProject)) } answers { capturedProject.captured }
        val projectsService = ProjectsService(projectRepository)

        // Act
        val actualProject = projectsService.addUserToProject(users[1], Role.LEAD_DEVELOPER, project)

        // Act & assert
        verify(exactly = INVOCATION_KIND_ONCE) { projectRepository.updateProject(any()) }
        assertContains(actualProject.users, users[1])
        assertEquals(actualProject.users[users[1]], Role.LEAD_DEVELOPER)
    }

    @Test
    fun `FR-1_5, 3) When a project has a lead developer assigned, addUserToProject with lead developer role should throw error`() {
        // Arrange
        val users = UsersFixture.generateUsers(2)
        val project = Project("MyTestProject", mutableMapOf(users[0] to Role.LEAD_DEVELOPER))
        val projectRepository = mockk<ProjectRepository>()
        val projectsService = ProjectsService(projectRepository)

        // Act & assert
        assertFailsWith(IllegalStateException::class) {
            projectsService.addUserToProject(users[1], Role.LEAD_DEVELOPER, project)
        }
    }

    @Test
    fun `FR-1_5) A combination of all roles can be added in a project`() {
        // Arrange
        val users = UsersFixture.generateUsers(4)
        val project = Project("MyTestProject")
        val projectRepository = mockk<ProjectRepository>()
        val capturedProject = slot<Project>()
        every { projectRepository.addProject(capture(capturedProject)) } answers { capturedProject.captured }
        every { projectRepository.updateProject(capture(capturedProject)) } answers { capturedProject.captured }
        val projectsService = ProjectsService(projectRepository)

        // Act
        var actualProject = projectsService.createProject(users[0], project)
        actualProject = projectsService.addUserToProject(users[1], Role.LEAD_DEVELOPER, actualProject)
        actualProject = projectsService.addUserToProject(users[2], Role.DEVELOPERS, actualProject)
        actualProject = projectsService.addUserToProject(users[3], Role.TESTER, actualProject)

        // Assert
        assertEquals(actualProject.users.size, 4)
    }

    @Test
    fun `FR-1_5, 2) Multiple developers and testers can be added to a project`() {
        // Arrange
        val users = UsersFixture.generateUsers(5)
        val project = Project("MyTestProject")
        val projectRepository = mockk<ProjectRepository>()
        val capturedProject = slot<Project>()
        every { projectRepository.addProject(capture(capturedProject)) } answers { capturedProject.captured }
        every { projectRepository.updateProject(capture(capturedProject)) } answers { capturedProject.captured }
        val projectsService = ProjectsService(projectRepository)

        // Act
        var actualProject = projectsService.createProject(users[0], project)
        actualProject = projectsService.addUserToProject(users[1], Role.DEVELOPERS, actualProject)
        actualProject = projectsService.addUserToProject(users[2], Role.DEVELOPERS, actualProject)
        actualProject = projectsService.addUserToProject(users[3], Role.TESTER, actualProject)
        actualProject = projectsService.addUserToProject(users[4], Role.TESTER, actualProject)

        // Assert
        assertEquals(actualProject.users.size, 5)
    }

    companion object {
        const val INVOCATION_KIND_ONCE = 1
    }
}
