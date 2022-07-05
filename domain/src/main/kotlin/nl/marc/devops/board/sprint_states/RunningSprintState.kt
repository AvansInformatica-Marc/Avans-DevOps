package nl.marc.devops.board.sprint_states

import nl.marc.devops.board.BacklogItem
import nl.marc.devops.board.Sprint

class RunningSprintState(
    sprint: Sprint,
    override val backlogItems: Set<BacklogItem>,
    private val _sprintInfo: Sprint.Information
) : SprintState(sprint) {
    override var sprintInfo: Sprint.Information?
        get() = _sprintInfo
        set(_) {
            throw IllegalStateException("Can't change sprint info when sprint is in progress")
        }

    override fun markFinished() {
        sprint.state = FinishedSprintState(sprint, backlogItems, sprintInfo!!)
    }
}
