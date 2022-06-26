package nl.marc.devops.pipeline

import java.util.*

interface TaskRunnerRepository {
    fun getTaskRunnerById(id: UUID): TaskRunner

    fun saveTaskRunner(taskRunner: TaskRunner)
}
