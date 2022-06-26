package nl.marc.devops.board.sprint_states

import nl.marc.devops.board.Sprint
import nl.marc.devops.board.Task

class CompletedSprintState(
    private val sprint: Sprint,
    override val tasks: Set<Task>,
    private val _sprintInfo: Sprint.Information
) : SprintState() {
    override var sprintInfo: Sprint.Information?
        get() = _sprintInfo
        set(_) {
            throw IllegalStateException("Can't change sprint info when sprint is completed")
        }
}
