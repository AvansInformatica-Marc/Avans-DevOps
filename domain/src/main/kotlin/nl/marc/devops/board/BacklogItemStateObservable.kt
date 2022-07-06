package nl.marc.devops.board

abstract class BacklogItemStateObservable {
    private val observers = mutableSetOf<BacklogItemStateObserver>()

    fun addObserver(observer: BacklogItemStateObserver) {
        observers += observer
    }

    fun notify(backlogItemStateChange: BacklogItemStateChange) {
        for (observer in observers) {
            observer.notify(backlogItemStateChange)
        }
    }
}
