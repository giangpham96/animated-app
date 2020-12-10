package la.me.leo.core_animation.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import la.me.leo.core_animation.transition.FragmentTransition

fun FragmentManager.navigateToTab(
    tag: String,
    @IdRes rootId: Int,
    enterFragmentFactory: () -> Fragment,
    enterTransition: FragmentTransition,
    exitTransition: FragmentTransition
) {
    val exitFragment = fragments.firstOrNull { it.isVisible }
    if (exitFragment != null) exitTransition.integrateWithFragment(exitFragment)
    val (enterFragment, newlyCreated) = findFragmentByTag(tag)?.let { it to false }
        ?: enterFragmentFactory() to true
    enterTransition.integrateWithFragment(enterFragment)
    commit {
        exitFragment?.let { hide(it) }
        enterTransition.integrateWithFragment(enterFragment)
        if (newlyCreated) { add(rootId, enterFragment, tag) } else { show(enterFragment) }
    }
}
