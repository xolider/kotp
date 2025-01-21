package dev.vicart.kotp.hotp

import dev.vicart.kotp.common.HashAlgorithm
import org.apache.commons.codec.binary.Base32
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class HOTPVerifier(val algorithm: HashAlgorithm = HashAlgorithm.SHA1) {

    fun verify(key: SecretKey, code: String, counter: Long) : Boolean {
        val hotpGenerator = HOTPGenerator(code.length, algorithm)
        return hotpGenerator.generateCode(key, counter) == code
    }

    fun verify(key: ByteArray, code: String, counter: Long) : Boolean {
        val secretKey = SecretKeySpec(key, algorithm.name)
        return verify(secretKey, code, counter)
    }

    fun verify(key: String, code: String, counter: Long) : Boolean {
        val secretKey = Base32().decode(key)
        return verify(secretKey, code, counter)
    }
}