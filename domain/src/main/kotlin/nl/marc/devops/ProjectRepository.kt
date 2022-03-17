package nl.marc.devops

interface ProjectRepository {
    fun addProject(project: Project): Project

    fun updateProject(project: Project): Project
}
