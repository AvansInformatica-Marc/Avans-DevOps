package nl.marc.devops.projects

import nl.marc.devops.fixtures.UsersFixture
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProjectTests {
    private fun testRoleAvailability(inputRole: Role, testRole: Role): Boolean {
        // Arrange
        val user = UsersFixture.defaultUser
        val project = Project("MyTestProject", mutableMapOf(user to inputRole))

        // Act
        return project.isRoleUsed(testRole)
    }

    @Test
    fun `FR-1_3) When a project has a product owner assigned, isRoleUsed should return true`() {
        // Arrange Act
        val isRoleOccupied = testRoleAvailability(Role.PRODUCT_OWNER, Role.PRODUCT_OWNER)

        // Assert
        assertTrue(isRoleOccupied)
    }

    @Test
    fun `FR-1_5) When a project has no lead developer assigned, isRoleUsed should return false`() {
        // Arrange & Act
        val isRoleOccupied = testRoleAvailability(Role.PRODUCT_OWNER, Role.LEAD_DEVELOPER)

        // Assert
        assertFalse(isRoleOccupied)
    }

    @Test
    fun `FR-1_5) When a project has a lead developer assigned, isRoleUsed should return true`() {
        // Arrange & Act
        val isRoleOccupied = testRoleAvailability(Role.LEAD_DEVELOPER, Role.LEAD_DEVELOPER)

        // Assert
        assertTrue(isRoleOccupied)
    }
}
