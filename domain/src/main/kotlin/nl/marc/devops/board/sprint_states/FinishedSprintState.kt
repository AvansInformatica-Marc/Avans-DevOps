package nl.marc.devops.board.sprint_states

import nl.marc.devops.board.Sprint
import nl.marc.devops.board.Task

class FinishedSprintState(
    private val sprint: Sprint,
    override val tasks: Set<Task>,
    private val _sprintInfo: Sprint.Information
) : SprintState() {
    override var sprintInfo: Sprint.Information?
        get() = _sprintInfo
        set(_) {
            throw IllegalStateException("Can't change sprint info when sprint is completed")
        }

    override fun onPipelineCompleted() {
        sprint.state = CompletedSprintState(sprint, tasks, sprintInfo!!)
    }

    override fun onDocumentAttached() {
        sprint.state = CompletedSprintState(sprint, tasks, sprintInfo!!)
    }
}
