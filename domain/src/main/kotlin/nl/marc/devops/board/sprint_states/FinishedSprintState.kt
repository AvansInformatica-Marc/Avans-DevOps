package nl.marc.devops.board.sprint_states

import nl.marc.devops.board.BacklogItem
import nl.marc.devops.board.Sprint

class FinishedSprintState(
    sprint: Sprint,
    override val backlogItems: Set<BacklogItem>,
    private val _sprintInfo: Sprint.Information
) : SprintState(sprint) {
    override var sprintInfo: Sprint.Information?
        get() = _sprintInfo
        set(_) {
            throw IllegalStateException("Can't change sprint info when sprint is completed")
        }

    override fun onPipelineCompleted() {
        sprint.state = CompletedSprintState(sprint, backlogItems, sprintInfo!!)
    }

    override fun onDocumentAttached() {
        sprint.state = CompletedSprintState(sprint, backlogItems, sprintInfo!!)
    }
}
