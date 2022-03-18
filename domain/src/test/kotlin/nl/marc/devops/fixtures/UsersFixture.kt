package nl.marc.devops.fixtures

import nl.marc.devops.accounts.User

object UsersFixture {
    val defaultUser = User("Test", "test@avans-devops.nl", "ABCD")

    fun generateUsers(amount: Int): Array<User> {
        return Array(amount) {
            User("Test$it", "test-$it@avans-devops.nl", "ABC$it")
        }
    }
}
