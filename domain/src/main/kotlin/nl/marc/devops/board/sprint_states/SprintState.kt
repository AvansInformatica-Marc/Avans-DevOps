package nl.marc.devops.board.sprint_states

import nl.marc.devops.board.Sprint
import nl.marc.devops.board.Task

abstract class SprintState {
    abstract var sprintInfo: Sprint.Information?

    abstract val tasks: Set<Task>

    abstract fun addTask(task: Task)

    abstract fun startSprint()

    abstract fun markFinished()
}
