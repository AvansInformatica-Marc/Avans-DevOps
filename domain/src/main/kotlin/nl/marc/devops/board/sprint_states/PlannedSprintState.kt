package nl.marc.devops.board.sprint_states

import nl.marc.devops.accounts.User
import nl.marc.devops.board.BacklogItem
import nl.marc.devops.board.DateRange
import nl.marc.devops.board.Sprint

class PlannedSprintState(sprint: Sprint) : SprintState(sprint) {
    override var scrumMaster: User? = null

    override var name: String? = null

    override var dateRange: DateRange? = null

    override val backlogItems = mutableSetOf<BacklogItem>()

    override fun addTask(backlogItem: BacklogItem) {
        backlogItems += backlogItem
    }

    override fun startSprint() {
        sprint.state = RunningSprintState(sprint, backlogItems, scrumMaster, name, dateRange)
    }
}
