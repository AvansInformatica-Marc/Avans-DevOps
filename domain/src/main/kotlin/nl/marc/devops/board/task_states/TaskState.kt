package nl.marc.devops.board.task_states

import nl.marc.devops.projects.Role

abstract class TaskState {
    abstract val associatedRole: Role

    abstract val swimlane: String

    open fun markAsPlanned() {
        throw IllegalStateException("Not allowed")
    }

    open fun startDevelopment() {
        throw IllegalStateException("Not allowed")
    }

    open fun setDevelopmentCompleted() {
        throw IllegalStateException("Not allowed")
    }

    open fun setPlannedForTesting() {
        throw IllegalStateException("Not allowed")
    }

    open fun setTestingInProgress() {
        throw IllegalStateException("Not allowed")
    }

    open fun testingSucceeded() {
        throw IllegalStateException("Not allowed")
    }

    open fun testingFailed() {
        throw IllegalStateException("Not allowed")
    }

    open fun passesDefinitionOfDone() {
        throw IllegalStateException("Not allowed")
    }

    open fun failedDefinitionOfDone() {
        throw IllegalStateException("Not allowed")
    }
}
