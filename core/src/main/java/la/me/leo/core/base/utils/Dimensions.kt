package la.me.leo.core.base.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.DimenRes
import la.me.leo.core.R

fun Context.dp(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
        .toInt()
}

fun Context.dp(dp: Int) = dp(dp.toFloat())

fun Context.getDimen(@DimenRes id: Int) = resources.getDimensionPixelSize(id)

val Context.statusBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) getDimen(resourceId) else getDimen(R.dimen.u3)
    }

val Context.toolbarHeight: Int
    get() {
        val ta = obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        val height = ta.getDimensionPixelSize(0, getDimen(R.dimen.u7))
        ta.recycle()
        return height
    }
