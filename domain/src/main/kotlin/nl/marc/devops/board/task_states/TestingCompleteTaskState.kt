package nl.marc.devops.board.task_states

import nl.marc.devops.board.Task
import nl.marc.devops.board.TaskStateChange
import nl.marc.devops.projects.Role

class TestingCompleteTaskState(private val task: Task) : TaskState() {
    override val associatedRole = Role.DEVELOPERS

    override val swimlane = "Tested"

    override fun passesDefinitionOfDone() {
        task.state = task.taskStateFactory.completedTask
        task.notify(TaskStateChange(task, associatedRole, Role.PRODUCT_OWNER, false))
    }

    override fun failedDefinitionOfDone() {
        task.state = task.taskStateFactory.taskReadyForTesting
        task.notify(TaskStateChange(task, associatedRole, Role.TESTER, true))
    }
}
