package nl.marc.devops.projects

import nl.marc.devops.accounts.User

interface UserRoleService {
    fun getUsersByRole(role: Role): Set<User>
}
