package nl.marc.devops.pipeline.task_runners.release

import nl.marc.devops.pipeline.TaskRunner
import nl.marc.devops.pipeline.task_runners.CliTaskRunner

class AzureReleaseTask : TaskRunner {
    var directory = "./"

    var projectId: String? = null

    override fun start() {
        val cliTask = CliTaskRunner()
        cliTask.cliAction = "azure deploy --dir $directory --project $projectId"
        cliTask.start()
    }
}
