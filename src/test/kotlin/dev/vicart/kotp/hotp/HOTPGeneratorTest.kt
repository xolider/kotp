package dev.vicart.kotp.hotp

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.Base64

class HOTPGeneratorTest {

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testHotp() {
        val expected = "808363"
        val generator = HOTPGenerator()

        val key = "7B33A5979DD68A3F51101C9551E0E9D076A50B72".hexToByteArray()
        val result = generator.generateCode(key, 0)

        Assertions.assertEquals(expected, result)
    }
}