package nl.marc.devops.board

import nl.marc.devops.accounts.User
import nl.marc.devops.board.sprint_states.PlannedSprintState
import nl.marc.devops.board.sprint_states.SprintState

class Sprint {
    internal var state: SprintState = PlannedSprintState(this)

    var scrumMaster: User?
        get() = state.scrumMaster
        set(value) { state.scrumMaster = value }

    var name: String?
        get() = state.name
        set(value) { state.name = value }

    var dateRange: DateRange?
        get() = state.dateRange
        set(value) { state.dateRange = value }

    val backlogItems: Set<BacklogItem> by state::backlogItems

    fun addTask(backlogItem: BacklogItem) = state.addTask(backlogItem)

    fun startSprint() = state.startSprint()

    fun markFinished() = state.markFinished()

    fun onPipelineCompleted() = state.onPipelineCompleted()

    fun onDocumentAttached() = state.onDocumentAttached()
}
