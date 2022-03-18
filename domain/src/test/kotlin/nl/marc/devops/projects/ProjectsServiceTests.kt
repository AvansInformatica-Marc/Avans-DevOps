package nl.marc.devops.projects

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import nl.marc.devops.accounts.User
import kotlin.test.*

class ProjectsServiceTests {
    @Test
    fun `AD-89, AD-95) Creating a project assigns the current user as product owner`() {
        // Arrange
        val user = User("Test123", "test@avans.nl", "1234")
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

    private fun testRoleAvailability(inputRole: Role, testRole: Role): Boolean {
        // Arrange
        val user = User("Test123", "test@avans.nl", "1234")
        val project = Project("MyTestProject", mutableMapOf(user to inputRole))
        val projectRepository = mockk<ProjectRepository>()
        val projectsService = ProjectsService(projectRepository)

        // Act
        return projectsService.isRoleUsed(testRole, project)
    }

    @Test
    fun `AD-89, AD-96) When a project has a product owner assigned, isRoleUsed should return true`() {
        // Arrange Act
        val isRoleOccupied = testRoleAvailability(Role.PRODUCT_OWNER, Role.PRODUCT_OWNER)

        // Assert
        assertTrue(isRoleOccupied)
    }

    @Test
    fun `AD-89, AD-96) When a project has a product owner assigned, addUserToProject with product owner role should throw error`() {
        // Arrange
        val user = User("Test123", "test@avans.nl", "1234")
        val project = Project("MyTestProject", mutableMapOf(user to Role.PRODUCT_OWNER))
        val user2 = User("TestABC", "test.abc@avans.nl",  "ABCD")
        val projectRepository = mockk<ProjectRepository>()
        val projectsService = ProjectsService(projectRepository)

        // Act & assert
        assertFailsWith(IllegalStateException::class) {
            projectsService.addUserToProject(user2, Role.PRODUCT_OWNER, project)
        }
    }

    @Test
    fun `AD-28, AD-97) When a project has no lead developer assigned, isRoleUsed should return false`() {
        // Arrange & Act
        val isRoleOccupied = testRoleAvailability(Role.PRODUCT_OWNER, Role.LEAD_DEVELOPER)

        // Assert
        assertFalse(isRoleOccupied)
    }

    @Test
    fun `AD-28, AD-97) When a project has a lead developer assigned, isRoleUsed should return true`() {
        // Arrange & Act
        val isRoleOccupied = testRoleAvailability(Role.LEAD_DEVELOPER, Role.LEAD_DEVELOPER)

        // Assert
        assertTrue(isRoleOccupied)
    }

    @Test
    fun `AD-28, AD-97) When a project has no lead developer assigned, addUserToProject should update repository`() {
        // Arrange
        val user = User("Test123", "test@avans.nl", "1234")
        val project = Project("MyTestProject", mutableMapOf(user to Role.PRODUCT_OWNER))
        val user2 = User("TestABC", "test.abc@avans.nl",  "ABCD")
        val projectRepository = mockk<ProjectRepository>()
        val capturedProject = slot<Project>()
        every { projectRepository.updateProject(capture(capturedProject)) } answers { capturedProject.captured }
        val projectsService = ProjectsService(projectRepository)

        // Act
        val actualProject = projectsService.addUserToProject(user2, Role.LEAD_DEVELOPER, project)

        // Act & assert
        verify(exactly = INVOCATION_KIND_ONCE) { projectRepository.updateProject(any()) }
        assertContains(actualProject.users, user2)
        assertEquals(actualProject.users[user2], Role.LEAD_DEVELOPER)
    }

    @Test
    fun `AD-28, AD-97) When a project has a lead developer assigned, addUserToProject with lead developer role should throw error`() {
        // Arrange
        val user = User("Test123", "test@avans.nl", "1234")
        val project = Project("MyTestProject", mutableMapOf(user to Role.LEAD_DEVELOPER))
        val user2 = User("TestABC", "test.abc@avans.nl",  "ABCD")
        val projectRepository = mockk<ProjectRepository>()
        val projectsService = ProjectsService(projectRepository)

        // Act & assert
        assertFailsWith(IllegalStateException::class) {
            projectsService.addUserToProject(user2, Role.LEAD_DEVELOPER, project)
        }
    }

    @Test
    fun `AD-28, AD-98) A combination of all roles can be added in a project`() {
        // Arrange
        val users = Array(size = 4) {
            User("Test$it", "test-$it@avans.nl", "ABCD$it")
        }
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
    fun `AD-28, AD-99) Multiple developers and testers can be added to a project`() {
        // Arrange
        val users = Array(size = 5) {
            User("Test$it", "test-$it@avans.nl", "ABCD$it")
        }
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
