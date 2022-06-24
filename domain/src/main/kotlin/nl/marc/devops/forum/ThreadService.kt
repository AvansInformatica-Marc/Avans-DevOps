package nl.marc.devops.forum

import java.util.*

class ThreadService(
    private val threadRepository: ThreadRepository
) {
    fun getThreadByBacklogId(backlogId: UUID): Thread {
        return threadRepository.getThreadByBacklogId(backlogId)
    }

    fun markInactive(backlogId: UUID) {
        val thread = getThreadByBacklogId(backlogId)
        thread.isActive = false
        threadRepository.updateThread(thread)
    }
}
