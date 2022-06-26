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

    fun onPipelineCompleted() = state.onPipelineCompleted()

    fun onDocumentAttached() = state.onDocumentAttached()

    data class Information(
        val scrumMaster: User,
        val name: String,
        val beginDate: Date,
        val endDate: Date
    )

    class Builder {
        var scrumMaster: User? = null
            private set

        var sprintName: String? = null
            private set

        var beginDate: Date? = null
            private set

        var endDate: Date? = null
            private set

        fun setScrumMaster(scrumMaster: User): Builder {
            this.scrumMaster = scrumMaster
            return this
        }

        fun setSprintName(sprintName: String): Builder {
            this.sprintName = sprintName
            return this
        }

        fun setBeginDate(beginDate: Date): Builder {
            this.beginDate = beginDate
            return this
        }

        fun setEndDate(endDate: Date): Builder {
            this.endDate = endDate
            return this
        }

        fun build(): Sprint {
            return Sprint().apply {
                sprintInfo = Information(scrumMaster!!, sprintName!!, beginDate!!, endDate!!)
            }
        }
    }
}
