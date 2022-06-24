package nl.marc.devops.forum

import nl.marc.devops.board.TaskStateChange
import nl.marc.devops.board.TaskStateObserver

class ThreadAssociatedTaskCloseObserver(
    private val threadService: ThreadService
) : TaskStateObserver {
    override fun notify(taskStateChange: TaskStateChange) {
        if (taskStateChange.task.isComplete) {
            threadService.markInactive(taskStateChange.task.id)
        }
    }
}
