package nl.marc.devops.board

import nl.marc.devops.accounts.User
import nl.marc.devops.board.task_states.CompletedBacklogItemState
import nl.marc.devops.board.task_states.BacklogItemState
import nl.marc.devops.board.task_states.TaskStateFactory
import nl.marc.devops.projects.Role
import java.util.*

class BacklogItem : BacklogItemStateObservable() {
    internal val taskStateFactory = TaskStateFactory(this)

    var state: BacklogItemState = taskStateFactory.plannedTask

    var title: String? = null

    var developer: User? = null

    var id: UUID = UUID.randomUUID()

    val associatedRole: Role by state::associatedRole

    val swimlane: String by state::swimlane

    val isComplete: Boolean
        get() = state is CompletedBacklogItemState

    fun startDevelopment() {
        state.startDevelopment()
    }

    fun setDevelopmentCompleted() {
        state.setDevelopmentCompleted()
    }

    fun setPlannedForTesting() {
        state.setPlannedForTesting()
    }

    fun setTestingInProgress() {
        state.setTestingInProgress()
    }

    fun testingSucceeded() {
        state.testingSucceeded()
    }

    fun testingFailed() {
        state.testingFailed()
    }

    fun passesDefinitionOfDone() {
        state.passesDefinitionOfDone()
    }

    fun failedDefinitionOfDone() {
        state.failedDefinitionOfDone()
    }
}
