package nl.marc.devops.pipeline

import nl.marc.devops.pipeline.task_runners.RetrieveSourceCodeTask
import nl.marc.devops.pipeline.task_runners.analysis.SonarqubeAnalysisTask
import nl.marc.devops.pipeline.task_runners.build.NpmBuildTask
import nl.marc.devops.pipeline.task_runners.release.AzureReleaseTask
import nl.marc.devops.pipeline.task_runners.release.HerokuReleaseTask
import nl.marc.devops.pipeline.task_runners.test.NpmTestTask

class AngularPipelineFactory : ProjectPipelineFactory {
    override fun createDeployPipelineWithAzure(): Pipeline {
        return Pipeline(
            "Angular release",
            mutableSetOf(
                RetrieveSourceCodeTask(),
                NpmBuildTask(),
                AzureReleaseTask()
            )
        )
    }

    override fun createDeployPipelineWithHeroku(): Pipeline {
        return Pipeline(
            "Angular release",
            mutableSetOf(
                RetrieveSourceCodeTask(),
                NpmBuildTask(),
                HerokuReleaseTask()
            )
        )
    }

    override fun createTestPipeline(): Pipeline {
        return Pipeline(
            "Angular testing",
            mutableSetOf(
                RetrieveSourceCodeTask(),
                NpmBuildTask(),
                NpmTestTask()
            )
        )
    }

    override fun createAnalysisPipeline(): Pipeline {
        return Pipeline(
            "Angular analysis",
            mutableSetOf(
                RetrieveSourceCodeTask(),
                NpmBuildTask(),
                SonarqubeAnalysisTask()
            )
        )
    }
}
