package nl.marc.devops.board.task_states

import nl.marc.devops.board.BacklogItem
import nl.marc.devops.projects.Role

class ReadyForTestingBacklogItemState(private val backlogItemStateFactory: BacklogItemStateFactory) : BacklogItemState() {
    override val associatedRole = Role.TESTER

    override val swimlane = "Ready for testing"

    override fun setTestingInProgress(context: BacklogItem) {
        context.state = backlogItemStateFactory.testingInProgressTask
    }
}
