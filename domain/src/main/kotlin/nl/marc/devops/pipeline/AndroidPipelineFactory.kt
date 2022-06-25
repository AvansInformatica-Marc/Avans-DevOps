package nl.marc.devops.pipeline

import nl.marc.devops.pipeline.task_runners.RetrieveSourceCodeTask
import nl.marc.devops.pipeline.task_runners.analysis.GradleAnalysisTask
import nl.marc.devops.pipeline.task_runners.build.GradleBuildTask
import nl.marc.devops.pipeline.task_runners.test.GradleTestTask

class AndroidPipelineFactory : ProjectPipelineFactory {
    override fun createDeployPipelineWithAzure(): Pipeline {
        throw IllegalStateException("Android projects can't be deployed to Azure")
    }

    override fun createDeployPipelineWithHeroku(): Pipeline {
        throw IllegalStateException("Android projects can't be deployed to Azure")
    }

    override fun createTestPipeline(): Pipeline {
        return Pipeline(
            "Android testing",
            mutableSetOf(
                RetrieveSourceCodeTask(),
                GradleBuildTask(),
                GradleTestTask()
            )
        )
    }

    override fun createAnalysisPipeline(): Pipeline {
        return Pipeline(
            "Android analysis",
            mutableSetOf(
                RetrieveSourceCodeTask(),
                GradleBuildTask(),
                GradleAnalysisTask()
            )
        )
    }
}
