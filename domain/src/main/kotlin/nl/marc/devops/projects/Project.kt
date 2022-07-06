package nl.marc.devops.projects

import nl.marc.devops.accounts.User
import nl.marc.devops.board.Sprint
import java.util.*

class Project(
    val name: String,
    val users: MutableMap<User, Role> = HashMap(),
    val sprints: MutableSet<Sprint> = mutableSetOf(),
    val id: UUID = UUID.randomUUID()
) {
    fun isRoleUsed(role: Role): Boolean {
        return role in users.values
    }

    fun runGitCommand(command: String): String {
        println("git $command")
        return "command executed successfully."
    }
}
