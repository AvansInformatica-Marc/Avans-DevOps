package nl.marc.devops.board.task_states

import nl.marc.devops.board.Task
import nl.marc.devops.projects.Role

class TestingCompleteTaskState(private val task: Task) : TaskState() {
    override val associatedRole = Role.DEVELOPERS

    override val swimlane = "Tested"

    override fun passesDefinitionOfDone() {
        task.state = task.taskStateFactory.completedTask
        task.notifyAssignmentChanged(Role.PRODUCT_OWNER)
    }

    override fun failedDefinitionOfDone() {
        task.state = task.taskStateFactory.taskReadyForTesting
        task.notifyTaskMovedBack(associatedRole, Role.TESTER)
    }
}
