package la.me.leo.core.base.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.TextureView
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class MediaPlayerWidget(
        context: Context,
        attrs: AttributeSet
) : TextureView(context, attrs) {

    private lateinit var player: SimpleExoPlayer
    private val dataSourceFactory = DefaultDataSourceFactory(context, "animated-app; giang.pham")
    var playbackEndedCallback: (() -> Unit)? = null

    init {
        isOpaque = false
    }

    fun play(video: String) {
        player = ExoPlayerFactory.newSimpleInstance(context)
        player.setVideoTextureView(this)
        player.volume = 0f
        val source = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(video))
        player.prepare(source)
        player.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    playbackEndedCallback?.invoke()
                }
            }
        })
        player.playWhenReady = true
    }

    fun stop() {
        player.release()
    }

}
