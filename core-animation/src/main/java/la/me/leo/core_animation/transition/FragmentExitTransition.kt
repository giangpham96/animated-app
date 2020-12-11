package la.me.leo.core_animation.transition

import android.animation.Animator
import android.view.ViewGroup
import androidx.core.animation.addListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.transition.TransitionValues
import la.me.leo.core_animation.fragment.remove

abstract class FragmentExitTransition : FragmentTransition() {

    internal var destroyFragmentAfterTransition: Boolean = false

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        val animator = super.createAnimator(sceneRoot, startValues, endValues) ?: return null
        animator.addListener(
            onStart = { fragment.view?.isVisible = true },
            onEnd = {
                fragment.exitTransition = null
                if (destroyFragmentAfterTransition) fragment.remove()
            }
        )
        return animator
    }

    override fun integrateWithFragment(fragment: Fragment) {
        this.fragment = fragment
        fragment.exitTransition = this
    }
}
