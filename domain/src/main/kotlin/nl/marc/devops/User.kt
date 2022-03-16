package nl.marc.devops

data class User(
    val id: String,
    val name: String,
    val email: String,
    val passwordHash: String
)
