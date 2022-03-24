package nl.marc.devops.board.task_states

import nl.marc.devops.board.Task
import nl.marc.devops.projects.Role

class CompletedTaskState(private val task: Task) : TaskState() {
    override val associatedRole = Role.PRODUCT_OWNER

    override val swimlane = "Done"
}
