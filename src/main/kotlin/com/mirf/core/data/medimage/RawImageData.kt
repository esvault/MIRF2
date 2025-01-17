package com.mirf.core.data.medimage

/**
 * Abstract class to store image pixels data.
 * It is made to assist cross-platform integration for mobile platforms
 * RawImageData is used in MedImage and all algorithms that use it
 */
// TODO(musatian): migrate all calls to image to rawImage and according addons
open class RawImageData protected constructor() : Cloneable {
    open fun getHeight(): Int {
        return 0
    }

    open fun getWidth(): Int {
        return 0
    }

    open fun getImage(): ByteArray? {
        return null
    }

    public open override fun clone(): RawImageData {
        return this
    }
}

