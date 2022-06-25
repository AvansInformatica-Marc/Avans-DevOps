package nl.marc.devops.pipeline.task_runners.analysis

import nl.marc.devops.pipeline.TaskRunner
import nl.marc.devops.pipeline.task_runners.CliTaskRunner

class GradleAnalysisTask : TaskRunner {
    override fun start() {
        val cliTask = CliTaskRunner()
        cliTask.cliAction = "./gradlew check"
        cliTask.start()
    }
}
