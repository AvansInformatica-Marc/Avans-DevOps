package nl.marc.devops.pipeline.task_runners

import nl.marc.devops.pipeline.TaskRunner

class CliTaskRunner : TaskRunner {
    var cliAction: String? = null

    override fun start() {
        println(cliAction)
    }
}
