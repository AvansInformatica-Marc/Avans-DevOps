package nl.marc.devops.pipeline.task_runners.release

import nl.marc.devops.pipeline.TaskRunner
import nl.marc.devops.pipeline.task_runners.CliTaskRunner

class HerokuReleaseTask : TaskRunner {
    override fun start() {
        val cliTask = CliTaskRunner()
        cliTask.cliAction = "git push heroku main"
        cliTask.start()
    }
}
