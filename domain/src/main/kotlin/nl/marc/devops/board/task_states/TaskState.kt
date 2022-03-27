package nl.marc.devops.board.task_states

import nl.marc.devops.projects.Role

abstract class TaskState {
    abstract val associatedRole: Role

    abstract val swimlane: String

    open fun startDevelopment() {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    open fun setDevelopmentCompleted() {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    open fun setPlannedForTesting() {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    open fun setTestingInProgress() {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    open fun testingSucceeded() {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    open fun testingFailed() {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    open fun passesDefinitionOfDone() {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    open fun failedDefinitionOfDone() {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    companion object {
        const val DEFAULT_ERROR_MESSAGE = "Not allowed in this state"
    }
}
