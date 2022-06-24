package nl.marc.devops.accounts

import java.util.*

class User(
    val name: String,
    val email: String,
    val passwordHash: String,
    val id: UUID = UUID.randomUUID()
) {
    override fun hashCode() = email.hashCode()

    override fun equals(other: Any?) = email == (other as? User)?.email
}
