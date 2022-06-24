package nl.marc.devops.board.task_states

import nl.marc.devops.board.Task
import nl.marc.devops.board.TaskStateChange
import nl.marc.devops.projects.Role

class TestingInProgressTaskState(private val task: Task) : TaskState() {
    override val associatedRole = Role.TESTER

    override val swimlane = "Testing"

    override fun testingSucceeded() {
        task.state = task.taskStateFactory.testedTask
        task.notify(TaskStateChange(task, associatedRole, Role.DEVELOPERS, false))
    }

    override fun testingFailed() {
        task.state = task.taskStateFactory.plannedTask
        task.notify(TaskStateChange(task, associatedRole, Role.DEVELOPERS, true))
    }
}
