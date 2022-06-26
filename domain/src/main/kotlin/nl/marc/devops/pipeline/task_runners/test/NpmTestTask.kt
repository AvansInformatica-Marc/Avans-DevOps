package nl.marc.devops.pipeline.task_runners.test

import nl.marc.devops.pipeline.TaskRunner
import nl.marc.devops.pipeline.task_runners.CliTaskRunner

class NpmTestTask : TaskRunner {
    override fun start() {
        val cliTask = CliTaskRunner()
        cliTask.cliAction = "npm run test"
        cliTask.start()
    }
}
