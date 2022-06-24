package nl.marc.devops.board

interface TaskStateObserver {
    fun notify(taskStateChange: TaskStateChange)
}
