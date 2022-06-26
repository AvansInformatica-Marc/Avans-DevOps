package nl.marc.devops.pipeline

data class Pipeline(
    val name: String,
    val tasks: MutableSet<TaskRunner> = mutableSetOf()
): TaskRunner {
    override fun start() {
        for (task in tasks) {
            task.start()
        }
    }
}
