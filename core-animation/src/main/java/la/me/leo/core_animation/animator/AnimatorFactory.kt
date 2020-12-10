package la.me.leo.core_animation.animator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.animation.Interpolator
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

fun constructLifecycleAwareAnimator(
    duration: Int, interpolator: Interpolator? = null, startDelay: Long = 0,
    onUpdate: ((Float) -> (Unit)), onStart: (() -> (Unit))? = null, onEnd: (() -> (Unit))? = null,
    lifecycle: Lifecycle
): ValueAnimator {
    val animator = constructAnimator(duration, interpolator, startDelay, onUpdate, onStart, onEnd)
    cancelAnimatorOnDestroy(animator, lifecycle)
    return animator
}

fun constructAnimator(
    duration: Int, interpolator: Interpolator? = null, startDelay: Long = 0,
    onUpdate: ((Float) -> (Unit)), onStart: (() -> (Unit))? = null, onEnd: (() -> (Unit))? = null
): ValueAnimator {
    val animator = ValueAnimator.ofFloat(0f, 1f)
        .setDuration(duration.toLong())
    interpolator?.let { animator.interpolator = it }
    animator.addUpdateListener { onUpdate(it.animatedFraction) }
    if (onStart != null || onEnd != null) {
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                onStart?.invoke()
            }

            override fun onAnimationEnd(animation: Animator?) {
                onEnd?.invoke()
            }
        })
    }
    animator.startDelay = startDelay
    return animator
}

fun cancelAnimatorOnDestroy(animator: Animator, lifecycle: Lifecycle) {
    lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroyed() {
            lifecycle.removeObserver(this)
            animator.cancel()
        }
    })
}
