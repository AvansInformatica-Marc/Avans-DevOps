package nl.marc.devops.board.task_states

import nl.marc.devops.board.BacklogItem
import nl.marc.devops.board.BacklogItemStateChange
import nl.marc.devops.projects.Role

class TestingInProgressBacklogItemState(private val taskStateFactory: TaskStateFactory) : BacklogItemState() {
    override val associatedRole = Role.TESTER

    override val swimlane = "Testing"

    override fun testingSucceeded(context: BacklogItem) {
        context.state = taskStateFactory.testedTask
        context.notify(BacklogItemStateChange(context, associatedRole, Role.DEVELOPERS, false))
    }

    override fun testingFailed(context: BacklogItem) {
        context.state = taskStateFactory.plannedTask
        context.notify(BacklogItemStateChange(context, associatedRole, Role.DEVELOPERS, true))
    }
}
