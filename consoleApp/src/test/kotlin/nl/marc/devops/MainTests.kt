package nl.marc.devops

import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

class MainTests {
    @Test
    fun `main should not crash`() {
        assertDoesNotThrow {
            main()
        }
    }
}
