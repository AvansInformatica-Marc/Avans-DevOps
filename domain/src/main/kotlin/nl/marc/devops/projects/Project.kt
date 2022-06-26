package nl.marc.devops.projects

import nl.marc.devops.accounts.User
import java.util.*

class Project(
    val name: String,
    val users: MutableMap<User, Role> = HashMap(),
    val id: UUID = UUID.randomUUID()
)
