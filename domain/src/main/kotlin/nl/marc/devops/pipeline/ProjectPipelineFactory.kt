package nl.marc.devops.pipeline

interface ProjectPipelineFactory {
    fun createDeployPipelineWithAzure(): Pipeline

    fun createDeployPipelineWithHeroku(): Pipeline

    fun createTestPipeline(): Pipeline

    fun createAnalysisPipeline(): Pipeline
}
