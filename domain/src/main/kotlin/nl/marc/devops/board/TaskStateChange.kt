package nl.marc.devops.board

import nl.marc.devops.projects.Role

data class TaskStateChange(val task: Task, val oldRole: Role?, val newRole: Role, val wasMovedBack: Boolean)
