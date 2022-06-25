package nl.marc.devops.pipeline.task_runners

import nl.marc.devops.pipeline.TaskRunner

class RetrieveSourceCodeTask : TaskRunner {
    override fun start() {
        val cliTask = CliTaskRunner()
        cliTask.cliAction = "git checkout ."
        cliTask.start()
    }
}
