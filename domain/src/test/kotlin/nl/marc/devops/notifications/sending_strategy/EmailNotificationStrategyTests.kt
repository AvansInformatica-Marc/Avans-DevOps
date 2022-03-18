package nl.marc.devops.notifications.sending_strategy

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import nl.marc.devops.notifications.sending_apis.EmailSender
import kotlin.test.Test

class EmailNotificationStrategyTests {
    @Test
    fun `AD-30, AD-103) Email strategy will send notifications with email service to recipient`() {
        // Arrange
        val recipient = "notifications@avans-devops.nl"
        val emailService = mockk<EmailSender>()
        justRun { emailService.sendEmail(any(), any(), any(), any()) }
        val strategy = EmailNotificationStrategy(emailService)

        // Act
        strategy.sendNotification("Build failed", "Pipeline", recipient)

        // Assert
        verify(exactly = INVOCATION_KIND_ONCE) { emailService.sendEmail(any(), recipient, any(), any()) }
    }

    companion object {
        const val INVOCATION_KIND_ONCE = 1
    }
}
