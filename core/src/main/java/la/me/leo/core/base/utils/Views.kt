package la.me.leo.core.base.utils

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.core.view.isVisible

fun View.doOnPreDraw(block: () -> Unit) {
    val observer = viewTreeObserver
    observer.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            if (observer.isAlive) {
                observer.removeOnPreDrawListener(this)
            } else {
                viewTreeObserver.removeOnPreDrawListener(this)
            }
            block()
            return true
        }
    })
}

fun View.startMargin() = (layoutParams as ViewGroup.MarginLayoutParams).marginStart

fun View.endMargin() = (layoutParams as ViewGroup.MarginLayoutParams).marginEnd

fun TextView.showTextOrSetGone(text: String?) {
    if (text.isNullOrEmpty()) {
        isVisible = false
    } else {
        isVisible = true
        setText(text)
    }
}
