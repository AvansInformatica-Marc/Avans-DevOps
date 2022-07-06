package nl.marc.devops.board.task_states

import nl.marc.devops.board.BacklogItem
import nl.marc.devops.projects.Role

abstract class BacklogItemState {
    abstract val associatedRole: Role

    abstract val swimlane: String

    open fun startDevelopment(context: BacklogItem) {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    open fun setDevelopmentCompleted(context: BacklogItem) {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    open fun setPlannedForTesting(context: BacklogItem) {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    open fun setTestingInProgress(context: BacklogItem) {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    open fun testingSucceeded(context: BacklogItem) {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    open fun testingFailed(context: BacklogItem) {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    open fun passesDefinitionOfDone(context: BacklogItem) {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    open fun failedDefinitionOfDone(context: BacklogItem) {
        throw IllegalStateException(DEFAULT_ERROR_MESSAGE)
    }

    companion object {
        const val DEFAULT_ERROR_MESSAGE = "Not allowed in this state"
    }
}
