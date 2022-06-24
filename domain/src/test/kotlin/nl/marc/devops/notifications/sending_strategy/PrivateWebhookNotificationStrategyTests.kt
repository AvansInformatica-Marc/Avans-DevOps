package nl.marc.devops.notifications.sending_strategy

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import nl.marc.devops.notifications.sending_apis.WebhookSender
import org.junit.jupiter.api.Test

class PrivateWebhookNotificationStrategyTests {
    @Test
    fun `FR-1_4) Private webhook strategy will send notifications with webhook service to recipient`() {
        // Arrange
        val recipient = "https://webhook.avans-devops.nl/"
        val webhookService = mockk<WebhookSender>()
        justRun { webhookService.send(any(), any()) }
        val strategy = PrivateWebhookNotificationStrategy(webhookService)

        // Act
        strategy.sendNotification("Build failed", "Pipeline", recipient)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { webhookService.send(recipient, any()) }
    }

    companion object {
        const val INVOCATION_KIND_ONCE = 1
    }
}
