package la.me.leo.core.base.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import la.me.leo.core.base.transition.FragmentTransition

fun FragmentManager.navigateToTab(
    tag: String,
    @IdRes rootId: Int,
    fragmentFactory: () -> Fragment,
    enterTransition: FragmentTransition,
    exitTransition: FragmentTransition
) {
    val existingFragment = fragments.firstOrNull { it.isVisible }
    if (existingFragment != null) {
        exitTransition.fragment = existingFragment
        existingFragment.exitTransition = exitTransition
    }
    commit {
        var fragment = findFragmentByTag(tag)
        if (existingFragment != null) hide(existingFragment)
        if (fragment != null) {
            enterTransition.fragment = fragment
            fragment.enterTransition = enterTransition
            show(fragment)
        } else {
            fragment = fragmentFactory()
            enterTransition.fragment = fragment
            fragment.enterTransition = enterTransition
            add(rootId, fragment, tag)
        }
    }
}
