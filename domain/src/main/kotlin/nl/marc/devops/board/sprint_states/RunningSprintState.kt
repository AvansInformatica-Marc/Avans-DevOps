package nl.marc.devops.board.sprint_states

import nl.marc.devops.accounts.User
import nl.marc.devops.board.BacklogItem
import nl.marc.devops.board.DateRange
import nl.marc.devops.board.Sprint

class RunningSprintState(
    sprint: Sprint,
    override val backlogItems: Set<BacklogItem>,
    scrumMaster: User?,
    name: String?,
    dateRange: DateRange?
) : SprintState(sprint) {
    override var scrumMaster: User? = scrumMaster
        set(_) = throw IllegalStateException("Can't change scrum master when sprint is in progress")

    override var name: String? = name
        set(_) = throw IllegalStateException("Can't change sprint name when sprint is in progress")

    override var dateRange: DateRange? = dateRange
        set(_) = throw IllegalStateException("Can't change sprint start and end when sprint is in progress")

    override fun markFinished() {
        sprint.state = FinishedSprintState(sprint, backlogItems, scrumMaster, name, dateRange)
    }
}
