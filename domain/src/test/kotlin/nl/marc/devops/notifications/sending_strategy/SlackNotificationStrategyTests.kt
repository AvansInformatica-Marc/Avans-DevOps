package nl.marc.devops.notifications.sending_strategy

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import nl.marc.devops.notifications.sending_apis.WebhookSender
import org.junit.jupiter.api.Test

class SlackNotificationStrategyTests {
    @Test
    fun `AD-30, AD-104) Slack strategy will send notifications with webhook service to recipient`() {
        // Arrange
        val recipient = "avans-devops#notifications"
        val destinationUrl = "${SlackNotificationStrategy.BASE_URL}$recipient"
        val webhookService = mockk<WebhookSender>()
        justRun { webhookService.send(any(), any()) }
        val strategy = SlackNotificationStrategy(webhookService)

        // Act
        strategy.sendNotification("Build failed", "Pipeline", recipient)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { webhookService.send(destinationUrl, any()) }
    }

    companion object {
        const val INVOCATION_KIND_ONCE = 1
    }
}
