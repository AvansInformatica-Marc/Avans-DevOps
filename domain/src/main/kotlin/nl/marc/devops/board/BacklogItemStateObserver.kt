package nl.marc.devops.board

interface BacklogItemStateObserver {
    fun notify(backlogItemStateChange: BacklogItemStateChange)
}
