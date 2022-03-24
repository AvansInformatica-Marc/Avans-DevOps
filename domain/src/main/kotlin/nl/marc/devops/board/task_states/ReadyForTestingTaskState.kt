package nl.marc.devops.board.task_states

import nl.marc.devops.board.Task
import nl.marc.devops.projects.Role

class ReadyForTestingTaskState(private val task: Task) : TaskState() {
    override val associatedRole = Role.TESTER

    override val swimlane = "Ready for testing"

    override fun setTestingInProgress() {
        task.state = task.taskStateFactory.testingInProgressTask
    }
}
