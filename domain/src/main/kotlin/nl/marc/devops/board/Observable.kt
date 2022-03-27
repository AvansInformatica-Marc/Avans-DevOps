package nl.marc.devops.board

abstract class Observable<T> {
    protected val observers = mutableSetOf<T>()

    fun addObserver(observer: T) {
        observers += observer
    }
}
