package nl.marc.devops.board.sprint_states

import nl.marc.devops.board.Sprint
import nl.marc.devops.board.BacklogItem

class CompletedSprintState(
    sprint: Sprint,
    override val backlogItems: Set<BacklogItem>,
    private val _sprintInfo: Sprint.Information
) : SprintState(sprint) {
    override var sprintInfo: Sprint.Information?
        get() = _sprintInfo
        set(_) {
            throw IllegalStateException("Can't change sprint info when sprint is completed")
        }
}
