package nl.marc.devops.board.task_states

import nl.marc.devops.board.BacklogItem
import nl.marc.devops.projects.Role

class ReadyForTestingBacklogItemState(private val backlogItem: BacklogItem) : BacklogItemState() {
    override val associatedRole = Role.TESTER

    override val swimlane = "Ready for testing"

    override fun setTestingInProgress() {
        backlogItem.state = backlogItem.taskStateFactory.testingInProgressTask
    }
}
