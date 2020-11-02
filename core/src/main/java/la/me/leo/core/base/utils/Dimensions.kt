package la.me.leo.core.base.utils

import android.content.Context
import android.util.TypedValue

fun Context.dp(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
}

fun Context.dp(dp: Int) = dp(dp.toFloat())
