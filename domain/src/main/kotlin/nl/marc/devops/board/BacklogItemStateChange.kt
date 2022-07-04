package nl.marc.devops.board

import nl.marc.devops.projects.Role

data class BacklogItemStateChange(val backlogItem: BacklogItem, val oldRole: Role?, val newRole: Role, val wasMovedBack: Boolean)
