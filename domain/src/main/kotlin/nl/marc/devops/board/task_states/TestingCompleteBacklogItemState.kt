package nl.marc.devops.board.task_states

import nl.marc.devops.board.BacklogItem
import nl.marc.devops.board.BacklogItemStateChange
import nl.marc.devops.projects.Role

class TestingCompleteBacklogItemState(private val backlogItemStateFactory: BacklogItemStateFactory) : BacklogItemState() {
    override val associatedRole = Role.DEVELOPERS

    override val swimlane = "Tested"

    override fun passesDefinitionOfDone(context: BacklogItem) {
        context.state = backlogItemStateFactory.completedTask
        context.notify(BacklogItemStateChange(context, associatedRole, Role.PRODUCT_OWNER, false))
    }

    override fun failedDefinitionOfDone(context: BacklogItem) {
        context.state = backlogItemStateFactory.taskReadyForTesting
        context.notify(BacklogItemStateChange(context, associatedRole, Role.TESTER, true))
    }
}
