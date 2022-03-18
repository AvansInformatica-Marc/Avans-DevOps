package nl.marc.devops.projects

import nl.marc.devops.accounts.User

class Project(
    val name: String,
    val users: MutableMap<User, Role> = HashMap()
)
