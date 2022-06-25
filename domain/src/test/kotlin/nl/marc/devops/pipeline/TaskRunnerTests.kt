package nl.marc.devops.pipeline

import io.mockk.spyk
import io.mockk.verify
import kotlin.test.Test

class TaskRunnerTests {
    @Test
    fun `FR-5_1) All nested tasks should run when run is called on the root task`() {
        // Arrange
        val pipelineFactory = PipelineFactory()
        val testPipelineFactory1 = pipelineFactory.createAndroidPipelineFactory()
        val testPipelineFactory2 = pipelineFactory.createAngularPipelineFactory()
        val rootTask = Pipeline(
            "AvansDevOps test pipeline",
            mutableSetOf(
                testPipelineFactory1.createTestPipeline(),
                testPipelineFactory1.createAnalysisPipeline(),
                testPipelineFactory2.createTestPipeline(),
                testPipelineFactory2.createAnalysisPipeline()
            )
        )
        val rootTaskWithSpy = convertTaskRunnerToSpiedObject(rootTask)

        // Act
        rootTaskWithSpy.start()

        // Assert
        verifyTaskRanNested(rootTaskWithSpy)
    }

    private fun convertTaskRunnerToSpiedObject(taskRunner: TaskRunner): TaskRunner {
        if (taskRunner is Pipeline) {
            return spyk(
                Pipeline(
                    taskRunner.name,
                    taskRunner.tasks.map { convertTaskRunnerToSpiedObject(it) }.toMutableSet()
                )
            )
        }
        return spyk(taskRunner)
    }

    private fun verifyTaskRanNested(taskRunner: TaskRunner) {
        verify(exactly = INVOCATION_KIND_ONCE) { taskRunner.start() }

        if (taskRunner is Pipeline) {
            for (nestedTaskRunner in taskRunner.tasks) {
                verifyTaskRanNested(nestedTaskRunner)
            }
        }
    }

    companion object {
        const val INVOCATION_KIND_ONCE = 1
    }
}
