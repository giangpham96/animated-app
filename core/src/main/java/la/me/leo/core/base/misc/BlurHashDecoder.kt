package la.me.leo.core.base.misc

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.withSign

object BlurHashDecoder {

    fun createPlaceholderRatio1F(context: Context, blurHash: String?): BitmapDrawable? =
        createPlaceholder(context, blurHash, 1.0)

    fun createPlaceholderRatio2F(context: Context, blurHash: String?): BitmapDrawable? =
        createPlaceholder(context, blurHash, 2.0)

    fun createPlaceholderRatio3Div2(context: Context, blurHash: String?): BitmapDrawable? =
        createPlaceholder(context, blurHash, 3.0 / 2.0)

    fun createPlaceholderRatio17777(context: Context, blurHash: String?): BitmapDrawable? =
        createPlaceholder(context, blurHash, 1.777778)

    fun createPlaceholder(context: Context, blurHash: String?, ratio: Double): BitmapDrawable? {
        val size = 20
        var bitmap = decode(blurHash, size, size) ?: return null
        val width = if (ratio > 1) size else (size * ratio).toInt()
        val height = if (ratio > 1) (size / ratio).toInt() else size
        if (width <= 0 || height <= 0) {
            return null
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
        return BitmapDrawable(context.resources, bitmap)
    }

    private fun decode(blurHash: String?, width: Int, height: Int, punch: Float = 1f): Bitmap? {
        if (blurHash == null || blurHash.length < 6) {
            return null
        }
        val numCompEnc = decode64(blurHash, 0, 1)
        val numCompX = (numCompEnc and 7) + 1
        val numCompY = (numCompEnc shr 3) + 1

        if (blurHash.length != 4 + 2 * numCompX * numCompY) {
            return null
        }
        val maxAcEnc = decode64(blurHash, 1, 2)
        val maxAc = (maxAcEnc + 1) / 128f
        val colors = Array(numCompX * numCompY) { i ->
            if (i == 0) {
                val colorEnc = decode64(blurHash, 2, 6)
                decodeDc(colorEnc)
            } else {
                val from = 4 + i * 2
                val colorEnc = decode64(blurHash, from, from + 2)
                decodeAc(colorEnc, maxAc * punch)
            }
        }
        return composeBitmap(width, height, numCompX, numCompY, colors)
    }

    private fun decode64(str: String, from: Int = 0, to: Int = str.length): Int {
        var result = 0
        for (i in from until to) {
            val index = charMap[str[i]] ?: -1
            if (index != -1) {
                result = result * 64 + index
            }
        }
        return result
    }

    private fun decodeDc(colorEnc: Int): FloatArray {
        val r = colorEnc shr 16
        val g = (colorEnc shr 8) and 255
        val b = colorEnc and 255
        return floatArrayOf(srgbToLinear(r), srgbToLinear(g), srgbToLinear(b))
    }

    private fun srgbToLinear(colorEnc: Int): Float {
        val v = colorEnc / 255f
        return if (v <= 0.04045f) {
            (v / 12.92f)
        } else {
            ((v + 0.055f) / 1.055f).pow(2.4f)
        }
    }

    private fun decodeAc(value: Int, maxAc: Float): FloatArray {
        val r = value shr 8
        val g = (value shr 4) and 15
        val b = value and 15
        return floatArrayOf(
                signedPow3((r - 8) / 8.0f) * maxAc * 2,
                signedPow3((g - 8) / 8.0f) * maxAc * 2,
                signedPow3((b - 8) / 8.0f) * maxAc * 2
        )
    }

    private fun signedPow3(value: Float) = value.pow(3f).withSign(value)

    private fun composeBitmap(
            width: Int, height: Int,
            numCompX: Int, numCompY: Int,
            colors: Array<FloatArray>
    ): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        for (y in 0 until height) {
            for (x in 0 until width) {
                var r = 0f
                var g = 0f
                var b = 0f
                for (j in 0 until numCompY) {
                    for (i in 0 until numCompX) {
                        val basis = (cos(PI * x * i / width) * cos(PI * y * j / height)).toFloat()
                        val color = colors[j * numCompX + i]
                        r += color[0] * basis
                        g += color[1] * basis
                        b += color[2] * basis
                    }
                }
                bitmap.setPixel(x, y, Color.rgb(linearToSrgb(r), linearToSrgb(g), linearToSrgb(b)))
            }
        }
        return bitmap
    }

    private fun linearToSrgb(value: Float): Int {
        val v = value.coerceIn(0f, 1f)
        return if (v <= 0.0031308f) {
            (v * 12.92f * 255f + 0.5f).toInt()
        } else {
            ((1.055f * v.pow(1 / 2.4f) - 0.055f) * 255 + 0.5f).toInt()
        }
    }

    private val charMap = listOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ':', ';'
    )
            .mapIndexed { i, c -> c to i }
            .toMap()

}
