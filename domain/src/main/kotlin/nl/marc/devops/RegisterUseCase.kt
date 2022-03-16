package nl.marc.devops

class RegisterUseCase(private val userRepository: UserRepository) {
    fun register(name: String, email: String, password: String): User {
        return userRepository.addUser(name, email, password.hashCode().toString(16))
    }
}
