package nl.marc.devops

class Project(
    val name: String,
    val users: MutableMap<User, Role> = HashMap()
)
