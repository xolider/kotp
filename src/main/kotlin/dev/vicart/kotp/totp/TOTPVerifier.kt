package dev.vicart.kotp.totp

import dev.vicart.kotp.common.HashAlgorithm
import org.apache.commons.codec.binary.Base32
import java.time.Instant
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class TOTPVerifier(val algorithm: HashAlgorithm = HashAlgorithm.SHA1, val period: Long = 30) {

    fun verify(key: SecretKey, code: String, instant: Instant = Instant.now()) : Boolean {
        val totpGenerator = TOTPGenerator(code.length, algorithm, period)
        return (instant.minusSeconds(period).epochSecond..instant.plusSeconds(period).epochSecond step period).map {
            totpGenerator.generateCode(key, Instant.ofEpochSecond(it)) == code
        }.contains(true)
    }

    fun verify(key: ByteArray, code: String, instant: Instant = Instant.now()) : Boolean {
        val secretKey = SecretKeySpec(key, algorithm.name)
        return verify(secretKey, code, instant)
    }

    fun verify(key: String, code: String, instant: Instant = Instant.now()): Boolean {
        val secretKey = Base32().decode(key)
        return verify(secretKey, code, instant)
    }
}