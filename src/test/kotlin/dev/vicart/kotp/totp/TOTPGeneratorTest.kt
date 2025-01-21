package dev.vicart.kotp.totp

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Instant

class TOTPGeneratorTest {

    @Test
    fun testTotp() {
        val expected = "236918"

        val generator = TOTPGenerator()

        val result = generator.generateCode("JBSWY3DPEHPK3PXP", Instant.ofEpochMilli(1737360000))
        Assertions.assertEquals(expected, result)
    }
}