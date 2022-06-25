package nl.marc.devops.pipeline

class PipelineFactory {
    fun createAndroidPipelineFactory(): ProjectPipelineFactory {
        return AndroidPipelineFactory()
    }

    fun createAngularPipelineFactory(): ProjectPipelineFactory {
        return AngularPipelineFactory()
    }
}
