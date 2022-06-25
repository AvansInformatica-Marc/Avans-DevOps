package nl.marc.devops.pipeline.task_runners.build_tasks

import nl.marc.devops.pipeline.TaskRunner
import nl.marc.devops.pipeline.task_runners.CliTaskRunner

class NpmBuildTask : TaskRunner {
    override fun start() {
        val cliTask = CliTaskRunner()
        cliTask.cliAction = "npm run build"
        cliTask.start()
    }
}
