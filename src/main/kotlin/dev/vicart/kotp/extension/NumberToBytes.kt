package dev.vicart.kotp.extension

import java.nio.ByteBuffer

internal fun Long.toByteArray() : ByteArray {
    return ByteBuffer.allocate(Long.SIZE_BYTES).putLong(this).array()
}