package nl.marc.devops.board.task_states

import nl.marc.devops.board.BacklogItem
import nl.marc.devops.board.BacklogItemStateChange
import nl.marc.devops.projects.Role

class InDevelopmentBacklogItemState(private val backlogItem: BacklogItem) : BacklogItemState() {
    override val associatedRole = Role.DEVELOPERS

    override val swimlane = "Doing"

    override fun setDevelopmentCompleted() = setPlannedForTesting()

    override fun setPlannedForTesting() {
        backlogItem.state = backlogItem.taskStateFactory.taskReadyForTesting
        backlogItem.notify(BacklogItemStateChange(backlogItem, associatedRole, Role.TESTER, false))
    }
}
