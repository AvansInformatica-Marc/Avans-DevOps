package nl.marc.devops.board.task_states

import nl.marc.devops.board.BacklogItem
import nl.marc.devops.board.BacklogItemStateChange
import nl.marc.devops.projects.Role

class TestingInProgressBacklogItemState(private val backlogItem: BacklogItem) : BacklogItemState() {
    override val associatedRole = Role.TESTER

    override val swimlane = "Testing"

    override fun testingSucceeded() {
        backlogItem.state = backlogItem.taskStateFactory.testedTask
        backlogItem.notify(BacklogItemStateChange(backlogItem, associatedRole, Role.DEVELOPERS, false))
    }

    override fun testingFailed() {
        backlogItem.state = backlogItem.taskStateFactory.plannedTask
        backlogItem.notify(BacklogItemStateChange(backlogItem, associatedRole, Role.DEVELOPERS, true))
    }
}
