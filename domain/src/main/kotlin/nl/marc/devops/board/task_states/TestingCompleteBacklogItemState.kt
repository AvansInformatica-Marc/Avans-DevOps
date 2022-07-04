package nl.marc.devops.board.task_states

import nl.marc.devops.board.BacklogItem
import nl.marc.devops.board.BacklogItemStateChange
import nl.marc.devops.projects.Role

class TestingCompleteBacklogItemState(private val backlogItem: BacklogItem) : BacklogItemState() {
    override val associatedRole = Role.DEVELOPERS

    override val swimlane = "Tested"

    override fun passesDefinitionOfDone() {
        backlogItem.state = backlogItem.taskStateFactory.completedTask
        backlogItem.notify(BacklogItemStateChange(backlogItem, associatedRole, Role.PRODUCT_OWNER, false))
    }

    override fun failedDefinitionOfDone() {
        backlogItem.state = backlogItem.taskStateFactory.taskReadyForTesting
        backlogItem.notify(BacklogItemStateChange(backlogItem, associatedRole, Role.TESTER, true))
    }
}
