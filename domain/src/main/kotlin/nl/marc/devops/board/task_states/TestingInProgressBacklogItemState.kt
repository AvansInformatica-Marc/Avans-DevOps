package nl.marc.devops.board.task_states

import nl.marc.devops.board.BacklogItem
import nl.marc.devops.board.BacklogItemStateChange
import nl.marc.devops.projects.Role

class TestingInProgressBacklogItemState(private val backlogItemStateFactory: BacklogItemStateFactory) : BacklogItemState() {
    override val associatedRole = Role.TESTER

    override val swimlane = "Testing"

    override fun testingSucceeded(context: BacklogItem) {
        context.state = backlogItemStateFactory.testedTask
        context.notify(BacklogItemStateChange(context, associatedRole, Role.DEVELOPERS, false))
    }

    override fun testingFailed(context: BacklogItem) {
        context.state = backlogItemStateFactory.plannedTask
        context.notify(BacklogItemStateChange(context, associatedRole, Role.DEVELOPERS, true))
    }
}
