package nl.marc.devops.board

import nl.marc.devops.accounts.User
import nl.marc.devops.board.task_states.TaskState
import nl.marc.devops.board.task_states.TaskStateFactory
import nl.marc.devops.projects.Role

class Task : Observable<TaskStateObserver>() {
    internal val taskStateFactory = TaskStateFactory(this)

    var state: TaskState = taskStateFactory.plannedTask

    var title: String? = null

    var developer: User? = null

    val associatedRole: Role by state::associatedRole

    val swimlane: String by state::swimlane

    internal fun notifyTaskMovedBack(oldRole: Role, newRole: Role) {
        for (observer in observers) {
            observer.onTaskMovedBack(this, oldRole, newRole)
        }
    }

    internal fun notifyAssignmentChanged(newAssignedRole: Role) {
        for (observer in observers) {
            observer.onTaskChangedAssignment(this, newAssignedRole)
        }
    }

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
