package la.me.leo.core_animation.transition

import android.animation.Animator
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import androidx.transition.TransitionValues
import androidx.transition.Visibility

sealed class FragmentTransition() : Visibility() {

    lateinit var fragment: Fragment

    @CallSuper
    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        val fragmentRoot = fragment.view
        val animator = fragmentRoot?.let {
            createFragmentAnimator(it)
        } ?: return null
        animator.addListener(
            onStart = {
                if (isEnterTransition) fragmentRoot.bringToFront()
            },
            onEnd = {
                fragment.enterTransition = null
                fragment.exitTransition = null
            }
        )
        return animator
    }

    fun integrateWithFragment(fragment: Fragment) {
        this.fragment = fragment
        if (isEnterTransition) {
            fragment.enterTransition = this
        } else {
            fragment.exitTransition = this
        }
    }

    private val isEnterTransition: Boolean
        get() = this is FragmentEnterTransition

    abstract fun createFragmentAnimator(fragmentRoot: View): Animator

    abstract class FragmentEnterTransition : FragmentTransition()
    abstract class FragmentExitTransition : FragmentTransition()

}
