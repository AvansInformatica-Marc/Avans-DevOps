package nl.marc.devops.board

abstract class TaskStateObservable {
    private val observers = mutableSetOf<TaskStateObserver>()

    fun addObserver(observer: TaskStateObserver) {
        observers += observer
    }

    fun notify(taskStateChange: TaskStateChange) {
        for (observer in observers) {
            observer.notify(taskStateChange)
        }
    }
}
