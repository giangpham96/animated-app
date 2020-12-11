package la.me.leo.core_animation.transition

import android.animation.Animator
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.animation.addListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.transition.TransitionValues
import androidx.transition.Visibility
import la.me.leo.core_animation.fragment.remove

abstract class FragmentTransition() : Visibility() {

    lateinit var fragment: Fragment

    @CallSuper
    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        val fragmentRoot = fragment.view
        return fragmentRoot?.let { createFragmentAnimator(it) } ?: return null
    }

    abstract fun integrateWithFragment(fragment: Fragment)

    private val isEnterTransition: Boolean
        get() = this is FragmentEnterTransition

    abstract fun createFragmentAnimator(fragmentRoot: View): Animator

}
