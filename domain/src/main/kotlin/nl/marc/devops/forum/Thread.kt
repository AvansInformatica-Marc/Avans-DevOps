package nl.marc.devops.forum

import nl.marc.devops.accounts.User
import nl.marc.devops.board.Task
import java.util.*

data class Thread(
    val backlogItem: Task,
    val messages: MutableSet<Message> = mutableSetOf(),
    var isActive: Boolean = true,
    val id: UUID = UUID.randomUUID()
) {
    data class Message(
        val user: User,
        val date: Date,
        val message: String
    )
}
