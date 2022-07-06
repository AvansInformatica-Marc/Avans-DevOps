package nl.marc.devops.board.task_states

import nl.marc.devops.board.BacklogItem
import nl.marc.devops.projects.Role

class PlannedBacklogItemState(private val backlogItemStateFactory: BacklogItemStateFactory) : BacklogItemState() {
    override val associatedRole = Role.DEVELOPERS

    override val swimlane = "To Do"

    override fun startDevelopment(context: BacklogItem) {
        context.state = backlogItemStateFactory.taskInDevelopment
    }
}
