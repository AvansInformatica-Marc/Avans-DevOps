package nl.marc.devops.board.sprint_states

import nl.marc.devops.board.Sprint
import nl.marc.devops.board.Task

class PlannedSprintState(private val sprint: Sprint) : SprintState() {
    override var sprintInfo: Sprint.Information? = null

    override val tasks = mutableSetOf<Task>()

    override fun addTask(task: Task) {
        tasks += task
    }

    override fun startSprint() {
        sprint.state = RunningSprintState(sprint, tasks, sprintInfo!!)
    }
}
