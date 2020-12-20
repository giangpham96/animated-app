package la.me.leo.core_animation.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import la.me.leo.core_animation.transition.FragmentEnterTransition
import la.me.leo.core_animation.transition.FragmentExitTransition
import la.me.leo.core_animation.transition.common.RegularPopTransition
import la.me.leo.core_animation.transition.common.RegularPushTransition

internal fun Fragment.remove() {
    if (!isRemoving) {
        (parentFragment?.childFragmentManager ?: activity?.supportFragmentManager)
            ?.commit { remove(this@remove) }
    }
}

fun FragmentManager.navigateToTab(
    tag: String,
    @IdRes rootId: Int,
    enterFragmentFactory: () -> Fragment,
    enterTransition: FragmentEnterTransition,
    exitTransition: FragmentExitTransition
) {
    val exitFragment = fragments.firstOrNull { it.isVisible }
    if (exitFragment != null) exitTransition.integrateWithFragment(exitFragment)
    val (enterFragment, newlyCreated) = findFragmentByTag(tag)?.let { it to false }
        ?: enterFragmentFactory() to true
    enterTransition.integrateWithFragment(enterFragment)
    commit {
        exitFragment?.let { hide(it) }
        if (newlyCreated) add(rootId, enterFragment, tag) else show(enterFragment)
    }
}

fun FragmentManager.replace(
    @IdRes rootId: Int,
    fragment: Fragment,
    tag: String? = null,
    enterTransition: FragmentEnterTransition = RegularPushTransition(),
    exitTransition: FragmentExitTransition = RegularPopTransition()
) {
    val exitFragment = fragments.firstOrNull { it.isVisible }
    if (exitFragment != null) {
        exitTransition.destroyFragmentAfterTransition = true
        exitTransition.integrateWithFragment(exitFragment)
    }
    enterTransition.integrateWithFragment(fragment)
    commit {
        exitFragment?.let { hide(it) }
        add(rootId, fragment, tag)
    }
}
