package nl.marc.devops.pipeline

import nl.marc.devops.pipeline.task_runners.RetrieveSourceCodeTask
import nl.marc.devops.pipeline.task_runners.analysis.GradleAnalysisTask
import nl.marc.devops.pipeline.task_runners.analysis.SonarqubeAnalysisTask
import nl.marc.devops.pipeline.task_runners.build.GradleBuildTask
import nl.marc.devops.pipeline.task_runners.build.NpmBuildTask
import nl.marc.devops.pipeline.task_runners.release.AzureReleaseTask
import nl.marc.devops.pipeline.task_runners.release.HerokuReleaseTask
import nl.marc.devops.pipeline.task_runners.test.GradleTestTask
import nl.marc.devops.pipeline.task_runners.test.NpmTestTask

class PipelineBuilder {
    private val name: String? = null

    private val tasks = mutableSetOf<TaskRunner>()

    fun setupGradleProject() {
        tasks += RetrieveSourceCodeTask()
        tasks += GradleBuildTask()
        tasks += GradleTestTask()
        tasks += GradleAnalysisTask()
    }

    fun setupNpmProject() {
        tasks += RetrieveSourceCodeTask()
        tasks += NpmBuildTask()
        tasks += NpmTestTask()
        tasks += SonarqubeAnalysisTask()
    }

    fun deployToHeroku() {
        tasks += HerokuReleaseTask()
    }

    fun deployToAzure() {
        tasks += AzureReleaseTask()
    }

    fun build(): Pipeline {
        return Pipeline(name!!, tasks)
    }
}
