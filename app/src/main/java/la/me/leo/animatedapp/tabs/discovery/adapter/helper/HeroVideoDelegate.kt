package la.me.leo.animatedapp.tabs.discovery.adapter.helper

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import la.me.leo.animatedapp.tabs.discovery.adapter.view_holder.HeroViewHolder
import la.me.leo.core.base.view.MediaPlayerWidget

internal class HeroVideoDelegate(
    private val holder: HeroViewHolder,
    private val playerWidget: MediaPlayerWidget,
    private val recyclerView: RecyclerView,
    private val lifecycle: Lifecycle
) : LifecycleObserver {

    private val layoutManager = recyclerView.layoutManager as LinearLayoutManager
    private var foreground = false
    private var attached = false
    private var playing = false
    private val video: String? get() = holder.item.video

    init {
        setup()
    }

    fun render() {
        playerWidget.isVisible = video != null
        refreshPlaybackState()
    }

    private fun setup() {
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                refreshPlaybackState()
            }
        }
        holder.itemView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {

            override fun onViewAttachedToWindow(v: View) {
                attached = true
                recyclerView.addOnScrollListener(scrollListener)
                lifecycle.addObserver(this@HeroVideoDelegate)
                refreshPlaybackState()
            }

            override fun onViewDetachedFromWindow(v: View) {
                attached = false
                recyclerView.removeOnScrollListener(scrollListener)
                lifecycle.removeObserver(this@HeroVideoDelegate)
                refreshPlaybackState()
            }

        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        foreground = true
        refreshPlaybackState()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        foreground = false
        refreshPlaybackState()
    }

    private fun refreshPlaybackState() {
        if (!playing) {
            if (video != null && attached && foreground && isVisible()) {
                playing = true
                playerWidget.play(video!!)
            }
        } else {
            if (video == null || !attached || !foreground || !isVisible()) {
                playing = false
                playerWidget.stop()
            }
        }
    }

    private fun isVisible() =
        layoutManager.findFirstCompletelyVisibleItemPosition() == holder.adapterPosition

}
