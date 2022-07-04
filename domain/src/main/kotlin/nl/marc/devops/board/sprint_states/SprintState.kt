package nl.marc.devops.board.sprint_states

import nl.marc.devops.board.Sprint
import nl.marc.devops.board.BacklogItem

abstract class SprintState {
    abstract var sprintInfo: Sprint.Information?

    abstract val backlogItems: Set<BacklogItem>

    open fun addTask(backlogItem: BacklogItem) {
        throw IllegalStateException("Can't add tasks to running sprints")
    }

    open fun startSprint() {
        throw IllegalStateException("Can't start a sprint that was already started")
    }

    open fun markFinished() {
        throw IllegalStateException("Sprint should be running to cancel it")
    }

    open fun onPipelineCompleted() {
        throw IllegalStateException("Can't complete pipeline in this state")
    }

    open fun onDocumentAttached() {
        throw IllegalStateException("Can't add documents in this state")
    }
}
