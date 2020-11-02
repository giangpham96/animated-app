package la.me.leo.animatedapp.tabs.discovery.adapter.helper

import android.os.Handler
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import la.me.leo.animatedapp.tabs.discovery.adapter.view_holder.HeroViewHolder
import la.me.leo.core.base.view.MediaPlayerWidget

internal class HeroRollingDelegate(
    private val holder: HeroViewHolder,
    private val playerWidget: MediaPlayerWidget,
    private val recyclerView: RecyclerView,
    private val lifecycle: Lifecycle,
    private val rollCallback: () -> Unit
) : LifecycleObserver {

    private val handler = Handler()
    private val layoutManager = recyclerView.layoutManager as LinearLayoutManager
    private var foreground = false
    private var attached = false
    private var scrollIdle = false
    private var rollScheduled = false

    init {
        setup()
    }

    fun render() {
        refreshRollingState()
    }

    private fun setup() {
        val scrollListener = object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                refreshRollingState()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                scrollIdle = newState == RecyclerView.SCROLL_STATE_IDLE
                refreshRollingState()
            }

        }

        holder.itemView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {

            override fun onViewAttachedToWindow(v: View) {
                attached = true
                recyclerView.addOnScrollListener(scrollListener)
                lifecycle.addObserver(this@HeroRollingDelegate)
                scrollIdle = recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE
                refreshRollingState()
            }

            override fun onViewDetachedFromWindow(v: View) {
                attached = false
                recyclerView.removeOnScrollListener(scrollListener)
                lifecycle.removeObserver(this@HeroRollingDelegate)
                refreshRollingState()
            }

        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onForeground() {
        foreground = true
        refreshRollingState()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onBackground() {
        foreground = false
        refreshRollingState()
    }

    private fun refreshRollingState() {
        if (!rollScheduled) {
            if (attached && foreground && scrollIdle && isVisible()) {
                rollScheduled = true
                scheduleRoll()
            }
        } else {
            if (!attached || !foreground || !scrollIdle || !isVisible()) {
                rollScheduled = false
                cancelRoll()
            }
        }
    }

    private fun isVisible() =
        layoutManager.findFirstCompletelyVisibleItemPosition() == holder.adapterPosition

    private fun scheduleRoll() {
        if (holder.item.video != null) {
            playerWidget.playbackEndedCallback = { rollCallback() }
        } else {
            handler.postDelayed({ rollCallback() },
                INTERVAL
            )
        }
    }

    private fun cancelRoll() {
        playerWidget.playbackEndedCallback = null
        handler.removeCallbacksAndMessages(null)
    }

}

private const val INTERVAL = 5000L
