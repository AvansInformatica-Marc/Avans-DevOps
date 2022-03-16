package nl.marc.devops

interface UserRepository {
    fun findUserByEmail(email: String): User?

    fun addUser(name: String, email: String, password: String): User
}
