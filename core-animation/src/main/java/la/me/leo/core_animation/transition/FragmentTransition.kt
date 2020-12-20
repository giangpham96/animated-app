package la.me.leo.core_animation.transition

import android.animation.Animator
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.transition.Transition
import androidx.transition.TransitionValues

abstract class FragmentTransition : Transition() {

    protected lateinit var fragment: Fragment

    override fun captureEndValues(transitionValues: TransitionValues) {}
    override fun captureStartValues(transitionValues: TransitionValues) {}

    override fun isTransitionRequired(startValues: TransitionValues?, endValues: TransitionValues?) = true

    @CallSuper override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?,
        endValues: TransitionValues?): Animator? {
        val fragmentRoot = fragment.view
        return fragmentRoot?.let { createFragmentAnimator(it) } ?: return null
    }

    @CallSuper
    open fun integrateWithFragment(fragment: Fragment) {
        this.fragment = fragment
    }

    abstract fun createFragmentAnimator(fragmentRoot: View): Animator

}
