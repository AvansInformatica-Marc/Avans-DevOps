package nl.marc.devops.forum

import java.util.*

interface ThreadRepository {
    fun getThreadByBacklogId(backlogId: UUID): Thread

    fun updateThread(thread: Thread)
}
