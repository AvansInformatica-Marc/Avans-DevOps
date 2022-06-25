package nl.marc.devops.pipeline

import kotlin.test.Test
import kotlin.test.assertFailsWith

class AndroidPipelineFactoryTests {
    @Test
    fun `FR-5_4) Create deploy pipeline with Azure should fail when Android factory is used`() {
        // Arrange
        val pipelineFactory = AndroidPipelineFactory()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            pipelineFactory.createDeployPipelineWithAzure()
        }
    }

    @Test
    fun `FR-5_4) Create deploy pipeline with Heroku should fail when Android factory is used`() {
        // Arrange
        val pipelineFactory = AndroidPipelineFactory()

        // Act & Assert
        assertFailsWith<IllegalStateException> {
            pipelineFactory.createDeployPipelineWithHeroku()
        }
    }
}
