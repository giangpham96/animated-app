package la.me.leo.data

import android.content.Context

internal fun Context.readJsonFromAsset(file: String): String {
    return assets.open(file).bufferedReader().use { it.readText() }
}
