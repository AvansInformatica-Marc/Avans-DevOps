package nl.marc.devops.board

import nl.marc.devops.accounts.User
import nl.marc.devops.board.task_states.CompletedTaskState
import nl.marc.devops.board.task_states.TaskState
import nl.marc.devops.board.task_states.TaskStateFactory
import nl.marc.devops.projects.Role
import java.util.*

class Task : TaskStateObservable() {
    internal val taskStateFactory = TaskStateFactory(this)

    var state: TaskState = taskStateFactory.plannedTask

    var title: String? = null

    var developer: User? = null

    var id: UUID = UUID.randomUUID()

    val associatedRole: Role by state::associatedRole

    val swimlane: String by state::swimlane

    val isComplete: Boolean
        get() = state is CompletedTaskState

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
