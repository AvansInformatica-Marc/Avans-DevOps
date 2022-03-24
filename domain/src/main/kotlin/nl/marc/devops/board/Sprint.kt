package nl.marc.devops.board

import nl.marc.devops.accounts.User
import nl.marc.devops.board.sprint_states.PlannedSprintState
import nl.marc.devops.board.sprint_states.SprintState
import java.util.*

class Sprint {
    internal var state: SprintState = PlannedSprintState(this)

    var sprintInfo: Information?
        get() = state.sprintInfo
        set(value) {
            state.sprintInfo = value
        }

    val tasks: Set<Task> by state::tasks

    fun addTask(task: Task) = state.addTask(task)

    fun startSprint() = state.startSprint()

    fun markFinished() = state.markFinished()

    data class Information(
        val scrumMaster: User,
        val name: String,
        val beginDate: Date,
        val endDate: Date
    )
}
