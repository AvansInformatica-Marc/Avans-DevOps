package nl.marc.devops

interface UserRepository {
    fun findUserByEmail(email: String): User?

    fun addUser(user: User): User
}
