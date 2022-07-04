package nl.marc.devops.board.task_states

import nl.marc.devops.board.BacklogItem
import nl.marc.devops.projects.Role

class CompletedBacklogItemState(private val backlogItem: BacklogItem) : BacklogItemState() {
    override val associatedRole = Role.PRODUCT_OWNER

    override val swimlane = "Done"
}
