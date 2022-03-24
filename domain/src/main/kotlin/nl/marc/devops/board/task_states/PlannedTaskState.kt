package nl.marc.devops.board.task_states

import nl.marc.devops.board.Task
import nl.marc.devops.projects.Role

class PlannedTaskState(private val task: Task) : TaskState() {
    override val associatedRole = Role.DEVELOPERS

    override val swimlane = "To Do"

    override fun startDevelopment() {
        task.state = task.taskStateFactory.taskInDevelopment
    }
}
