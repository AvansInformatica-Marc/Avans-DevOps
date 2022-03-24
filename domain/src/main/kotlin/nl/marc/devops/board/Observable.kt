package nl.marc.devops.board

interface Observable<T> {
    fun addObserver(observer: T)
}
