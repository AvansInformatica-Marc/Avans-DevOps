package nl.marc.devops

class FakeUserRepository : UserRepository {
    override fun findUserByEmail(email: String): User? {
        return null
    }
}
