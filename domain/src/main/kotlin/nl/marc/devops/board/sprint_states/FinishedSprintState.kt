package nl.marc.devops.board.sprint_states

import nl.marc.devops.board.Sprint
import nl.marc.devops.board.Task

class FinishedSprintState(
    private val sprint: Sprint,
    override val tasks: Set<Task>,
    private val _sprintInfo: Sprint.Information
) : SprintState {
    override var sprintInfo: Sprint.Information?
        get() = _sprintInfo
        set(_) {
            throw IllegalStateException("Can't change sprint info when sprint is completed")
        }

    override fun addTask(task: Task) {
        throw IllegalStateException("Can't add tasks to sprints that are in progress")
    }

    override fun startSprint() {
        throw IllegalStateException("Can't start sprint that was finished")
    }

    override fun markFinished() {
        throw IllegalStateException("Sprint has already finished")
    }
}
