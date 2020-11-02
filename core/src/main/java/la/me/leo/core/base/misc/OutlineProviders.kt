package la.me.leo.core.base.misc

import android.annotation.SuppressLint
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

class RoundRectOutlineProvider(
    val radius: Float
) : ViewOutlineProvider() {

    @SuppressLint("NewApi")
    override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(0, 0, view.width, view.height, radius)
    }

}
