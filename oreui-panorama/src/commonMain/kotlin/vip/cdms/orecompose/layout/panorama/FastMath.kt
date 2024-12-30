package vip.cdms.orecompose.layout.panorama

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun fastTan(x: Float): Float {
    val x2 = x * x
    val x3 = x * x2
    val x5 = x3 * x2

    return x + x3 / 3 + (2 * x5 / 15)
}

fun fastAtan2(y: Float, x: Float): Float {
    val absX = abs(x)
    val absY = abs(y)
    val a = min(absX, absY) / max(absX, absY)
    val s = a * a
    val angle = (((-0.046496473f * s + 0.15931422f) * s - 0.32762277f) * s * a + a)

    return if (absY > absX) {
        (PI / 2).toFloat() - angle
    } else {
        angle
    } * if (x < 0) -1 else 1 * if (y < 0) -1 else 1
}

fun fastAtan(x: Float, terms: Int = 5): Float {
    var result = 0f
    var power = x
    var sign = 1
    for (i in 1..terms step 2) {
        result += sign * power / i
        power *= x * x
        sign = -sign
    }
    return result
}
