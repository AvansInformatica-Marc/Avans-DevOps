package nl.marc.devops.board.sprint_states

import nl.marc.devops.board.Sprint
import nl.marc.devops.board.Task

class PlannedSprintState(private val sprint: Sprint) : SprintState {
    override var sprintInfo: Sprint.Information? = null

    override val tasks = mutableSetOf<Task>()

    override fun addTask(task: Task) {
        tasks += task
    }

    override fun startSprint() {
        sprint.state = RunningSprintState(sprint, tasks, sprintInfo!!)
    }

    override fun markFinished() {
        throw IllegalStateException("Can't complete sprint that has not started yet")
    }

    override fun onPipelineCompleted() {
        throw IllegalStateException("Can't complete sprint that has not started yet")
    }

    override fun onDocumentAttached() {
        throw IllegalStateException("Can't complete sprint that has not started yet")
    }
}
