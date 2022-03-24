package nl.marc.devops.projects

import nl.marc.devops.accounts.User

interface GetUserByRoleService {
    fun getUsersByRole(role: Role): Set<User>
}
