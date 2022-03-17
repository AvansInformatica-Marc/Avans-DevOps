package nl.marc.devops

class AccountService(private val userRepository: UserRepository) {
    fun login(email: String, password: String): User {
        val user = userRepository.findUserByEmail(email)
        val passwordHash = password.hashCode().toString(16)

        when {
            user == null -> throw IllegalArgumentException("E-mail is invalid")
            user.passwordHash != passwordHash -> throw IllegalArgumentException("Password is invalid")
            else -> return user
        }
    }

    fun register(name: String, email: String, password: String): User {
        return userRepository.addUser(User(name, email, password.hashCode().toString(16)))
    }
}
