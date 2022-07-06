package nl.marc.devops.board.task_states

import nl.marc.devops.board.BacklogItem
import nl.marc.devops.board.BacklogItemStateChange
import nl.marc.devops.projects.Role

class InDevelopmentBacklogItemState(private val backlogItemStateFactory: BacklogItemStateFactory) : BacklogItemState() {
    override val associatedRole = Role.DEVELOPERS

    override val swimlane = "Doing"

    override fun setDevelopmentCompleted(context: BacklogItem) = setPlannedForTesting(context)

    override fun setPlannedForTesting(context: BacklogItem) {
        context.state = backlogItemStateFactory.taskReadyForTesting
        context.notify(BacklogItemStateChange(context, associatedRole, Role.TESTER, false))
    }
}
