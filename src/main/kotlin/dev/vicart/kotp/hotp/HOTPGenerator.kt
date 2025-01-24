package dev.vicart.kotp.hotp

import dev.vicart.kotp.common.HashAlgorithm
import dev.vicart.kotp.extension.toByteArray
import org.apache.commons.codec.binary.Base32
import java.nio.ByteBuffer
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and
import kotlin.math.pow

class HOTPGenerator(val codeLength: Int = 6, val algorithm: HashAlgorithm = HashAlgorithm.SHA1) {

    private val hmac = Mac.getInstance("Hmac${algorithm.name}")

    fun generateCode(key: SecretKey, counter: Long) : String {
        hmac.reset()
        hmac.init(key)
        val hmacResult = hmac.doFinal(counter.toByteArray())
        return getCodeFromHash(hmacResult)
    }

    fun generateCode(key: ByteArray, counter: Long) : String {
        val secretKey = SecretKeySpec(key, algorithm.name)
        return generateCode(secretKey, counter)
    }

    fun generateCode(key: String, counter: Long) : String {
        val baseKey = Base32().decode(key)
        return generateCode(baseKey, counter)
    }

    private fun getCodeFromHash(hash: ByteArray) : String {
        val mask: Byte = 0xF
        val lastByte = hash.last() and mask
        val offset = lastByte.toInt()

        val byteBuffer = ByteBuffer.allocate(4)
        byteBuffer.put(hash, offset, 4)
        var truncated = byteBuffer.position(0).getInt() and 0x7FFFFFFF
        truncated = truncated.mod(10f.pow(codeLength).toInt())
        return "$truncated".padStart(codeLength, '0')
    }
}