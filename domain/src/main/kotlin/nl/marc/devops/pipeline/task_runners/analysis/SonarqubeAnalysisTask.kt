package nl.marc.devops.pipeline.task_runners.analysis

import nl.marc.devops.pipeline.TaskRunner
import nl.marc.devops.pipeline.task_runners.CliTaskRunner

class SonarqubeAnalysisTask : TaskRunner {
    var projectKey: String? = null

    var sonarHost: String? = null

    var sonarLoginToken: String? = null

    override fun start() {
        val cliTask = CliTaskRunner()
        cliTask.cliAction = "./sonar-scanner -Dsonar.projectKey=$projectKey -sonar.host.url=$sonarHost -Dsonar.login=$sonarLoginToken"
        cliTask.start()
    }
}
