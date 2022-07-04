package nl.marc.devops.board.sprint_states

import nl.marc.devops.board.Sprint
import nl.marc.devops.board.BacklogItem

class PlannedSprintState(private val sprint: Sprint) : SprintState() {
    override var sprintInfo: Sprint.Information? = null

    override val backlogItems = mutableSetOf<BacklogItem>()

    override fun addTask(backlogItem: BacklogItem) {
        backlogItems += backlogItem
    }

    override fun startSprint() {
        sprint.state = RunningSprintState(sprint, backlogItems, sprintInfo!!)
    }
}
