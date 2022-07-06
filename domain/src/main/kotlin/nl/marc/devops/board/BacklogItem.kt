package nl.marc.devops.board

import nl.marc.devops.accounts.User
import nl.marc.devops.board.task_states.CompletedBacklogItemState
import nl.marc.devops.board.task_states.BacklogItemStateFactory
import nl.marc.devops.projects.Role
import java.util.*

class BacklogItem : BacklogItemStateObservable() {
    var state = BacklogItemStateFactory().plannedTask

    var title: String? = null

    var developer: User? = null

    var id: UUID = UUID.randomUUID()

    val associatedRole: Role by state::associatedRole

    val swimlane: String by state::swimlane

    val isComplete: Boolean
        get() = state is CompletedBacklogItemState

    fun startDevelopment() {
        state.startDevelopment(this)
    }

    fun setDevelopmentCompleted() {
        state.setDevelopmentCompleted(this)
    }

    fun setPlannedForTesting() {
        state.setPlannedForTesting(this)
    }

    fun setTestingInProgress() {
        state.setTestingInProgress(this)
    }

    fun testingSucceeded() {
        state.testingSucceeded(this)
    }

    fun testingFailed() {
        state.testingFailed(this)
    }

    fun passesDefinitionOfDone() {
        state.passesDefinitionOfDone(this)
    }

    fun failedDefinitionOfDone() {
        state.failedDefinitionOfDone(this)
    }
}
