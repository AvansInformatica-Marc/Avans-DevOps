package nl.marc.devops.pipeline

import nl.marc.devops.pipeline.task_runners.release.AzureReleaseTask
import nl.marc.devops.pipeline.task_runners.release.HerokuReleaseTask
import kotlin.test.Test
import kotlin.test.assertTrue

class AngularPipelineFactoryTests {
    @Test
    fun `FR-5_4) Create deploy pipeline with Azure should succeed when Angular factory is used`() {
        // Arrange
        val pipelineFactory = AngularPipelineFactory()

        // Act
        val pipeline = pipelineFactory.createDeployPipelineWithAzure()

        // Assert
        assertTrue(pipeline.tasks.any { it is AzureReleaseTask })
    }

    @Test
    fun `FR-5_4) Create deploy pipeline with Heroku should succeed when Angular factory is used`() {
        // Arrange
        val pipelineFactory = AngularPipelineFactory()

        // Act
        val pipeline = pipelineFactory.createDeployPipelineWithHeroku()

        // Assert
        assertTrue(pipeline.tasks.any { it is HerokuReleaseTask })
    }
}
