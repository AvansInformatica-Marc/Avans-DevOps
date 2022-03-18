package nl.marc.devops.projects

interface ProjectRepository {
    fun addProject(project: Project): Project

    fun updateProject(project: Project): Project
}
