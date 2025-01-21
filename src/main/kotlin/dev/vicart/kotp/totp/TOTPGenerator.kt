package dev.vicart.kotp.totp

import dev.vicart.kotp.common.HashAlgorithm
import dev.vicart.kotp.hotp.HOTPGenerator
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.crypto.SecretKey

class TOTPGenerator(val codeLength: Int = 6, val algorithm: HashAlgorithm = HashAlgorithm.SHA1,
    val periodSecond: Long = 30) {

    private val hotpGenerator = HOTPGenerator(codeLength, algorithm)
    private val period = Duration.of(periodSecond, ChronoUnit.SECONDS)

    fun generateCode(key: SecretKey, instant: Instant = Instant.now()) : String {
        val counter = getCounterFrom(instant)
        return hotpGenerator.generateCode(key, counter)
    }

    fun generateCode(key: ByteArray, instant: Instant = Instant.now()) : String {
        val counter = getCounterFrom(instant)
        return hotpGenerator.generateCode(key, counter)
    }

    fun generateCode(key: String, instant: Instant = Instant.now()) : String {
        val counter = getCounterFrom(instant)
        return hotpGenerator.generateCode(key, counter)
    }

    private fun getCounterFrom(instant: Instant) : Long {
        return (instant.epochSecond/period.seconds)
    }
}