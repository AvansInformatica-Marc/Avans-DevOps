package nl.marc.devops.board.sprint_states

import nl.marc.devops.board.Sprint
import nl.marc.devops.board.BacklogItem

class FinishedSprintState(
    private val sprint: Sprint,
    val backlogItems: Set<BacklogItem>,
    private val _sprintInfo: Sprint.Information
) : SprintState() {
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
