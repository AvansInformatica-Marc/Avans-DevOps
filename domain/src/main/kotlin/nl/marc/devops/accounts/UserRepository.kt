package nl.marc.devops.accounts

interface UserRepository {
    fun findUserByEmail(email: String): User?

    fun addUser(user: User): User
}
