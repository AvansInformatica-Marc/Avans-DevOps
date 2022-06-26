package nl.marc.devops.pipeline

import java.util.*

class TaskRunnerService(
    private val taskRunnerRepository: TaskRunnerRepository
) {
    fun getTaskRunnerById(id: UUID): TaskRunner {
        return taskRunnerRepository.getTaskRunnerById(id)
    }

    fun saveTaskRunner(taskRunner: TaskRunner) {
        taskRunnerRepository.saveTaskRunner(taskRunner)
    }
}
