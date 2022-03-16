package nl.marc.devops

fun main() {
    printHeader()
    val email = requestEmail()
    val password = requestPassword()

    val repository = FakeUserRepository()
    val loginUseCase = LoginUseCase(repository)
    try {
        loginUseCase.login(email, password)
        println("Login successful")
    } catch (error: IllegalArgumentException) {
        println("Error: ${error.message}")
    }
}

fun printHeader() {
    println("Welkom bij Avans DevOps!")
    println("---")
}

fun requestEmail(): String {
    println("Enter email: ")
    val email = "dev@avans.nl"
    println("> $email")
    return email
}

fun requestPassword(): String {
    println("Enter password: ")
    val password = "Secret123"
    println("> ${password.map { "*" }.joinToString(separator = "")}")
    return password
}
