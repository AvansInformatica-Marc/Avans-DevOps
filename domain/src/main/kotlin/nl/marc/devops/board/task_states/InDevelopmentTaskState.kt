package nl.marc.devops.board.task_states

import nl.marc.devops.board.Task
import nl.marc.devops.board.TaskStateChange
import nl.marc.devops.projects.Role

class InDevelopmentTaskState(private val task: Task) : TaskState() {
    override val associatedRole = Role.DEVELOPERS

    override val swimlane = "Doing"

    override fun setDevelopmentCompleted() = setPlannedForTesting()

    override fun setPlannedForTesting() {
        task.state = task.taskStateFactory.taskReadyForTesting
        task.notify(TaskStateChange(task, associatedRole, Role.TESTER, false))
    }
}
