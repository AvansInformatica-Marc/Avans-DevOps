package nl.marc.devops.pipeline.task_runners.test

import nl.marc.devops.pipeline.TaskRunner
import nl.marc.devops.pipeline.task_runners.CliTaskRunner

class GradleTestTask : TaskRunner {
    override fun start() {
        val cliTask = CliTaskRunner()
        cliTask.cliAction = "./gradlew test"
        cliTask.start()
    }
}
