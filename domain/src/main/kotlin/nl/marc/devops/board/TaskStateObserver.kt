package nl.marc.devops.board

import nl.marc.devops.projects.Role

interface TaskStateObserver {
    fun onTaskMovedBack(task: Task, oldRole: Role, newRole: Role)

    fun onTaskChangedAssignment(task: Task, newAssignedRole: Role)
}
