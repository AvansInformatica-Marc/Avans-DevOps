package nl.marc.devops

class User(
    val name: String,
    val email: String,
    val passwordHash: String
) {
    override fun hashCode() = email.hashCode()

    override fun equals(other: Any?) = email == (other as? User)?.email
}
