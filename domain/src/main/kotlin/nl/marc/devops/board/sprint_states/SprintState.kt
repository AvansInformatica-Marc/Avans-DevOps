package nl.marc.devops.board.sprint_states

import nl.marc.devops.board.Sprint
import nl.marc.devops.board.Task

interface SprintState {
    var sprintInfo: Sprint.Information?

    val tasks: Set<Task>

    fun addTask(task: Task)

    fun startSprint()

    fun markFinished()

    fun onPipelineCompleted()

    fun onDocumentAttached()
}
