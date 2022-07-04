package nl.marc.devops.forum

import nl.marc.devops.board.BacklogItemStateChange
import nl.marc.devops.board.BacklogItemStateObserver

class ThreadAssociatedBacklogItemCloseObserver(
    private val threadService: ThreadService
) : BacklogItemStateObserver {
    override fun notify(backlogItemStateChange: BacklogItemStateChange) {
        if (backlogItemStateChange.backlogItem.isComplete) {
            threadService.markInactive(backlogItemStateChange.backlogItem.id)
        }
    }
}
