package nl.marc.devops.projects

import nl.marc.devops.accounts.User

class ProjectsService(private val projectRepository: ProjectRepository) {
    fun createProject(productOwner: User, project: Project): Project {
        project.users += productOwner to Role.PRODUCT_OWNER
        return projectRepository.addProject(project)
    }

    fun addUserToProject(user: User, role: Role, project: Project): Project {
        if (role == Role.PRODUCT_OWNER) {
            throw IllegalStateException("Project already has product owner")
        } else if (role == Role.LEAD_DEVELOPER && project.isRoleUsed(role)) {
            throw IllegalStateException("Project already has lead developer")
        }

        project.users += user to role
        return projectRepository.updateProject(project)
    }
}
