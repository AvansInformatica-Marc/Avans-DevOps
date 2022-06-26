package nl.marc.devops.board.sprint_states

import nl.marc.devops.board.Sprint
import nl.marc.devops.board.Task

class RunningSprintState(
    private val sprint: Sprint,
    override val tasks: Set<Task>,
    private val _sprintInfo: Sprint.Information
) : SprintState {
    override var sprintInfo: Sprint.Information?
        get() = _sprintInfo
        set(_) {
            throw IllegalStateException("Can't change sprint info when sprint is in progress")
        }

    override fun addTask(task: Task) {
        throw IllegalStateException("Can't add tasks to sprints that are in progress")
    }

    override fun startSprint() {
        throw IllegalStateException("Sprint was already started")
    }

    override fun markFinished() {
        sprint.state = FinishedSprintState(sprint, tasks, sprintInfo!!)
    }

    override fun onPipelineCompleted() {
        throw IllegalStateException("Can't complete sprint that has not finished yet")
    }

    override fun onDocumentAttached() {
        throw IllegalStateException("Can't complete sprint that has not finished yet")
    }
}
