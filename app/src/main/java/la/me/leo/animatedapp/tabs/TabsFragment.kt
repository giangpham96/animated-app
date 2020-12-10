package la.me.leo.animatedapp.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import la.me.leo.animatedapp.R
import la.me.leo.animatedapp.databinding.FragmentTabsBinding
import la.me.leo.animatedapp.tabs.discovery.DiscoveryFragment
import la.me.leo.animatedapp.tabs.profile.ProfileFragment
import la.me.leo.animatedapp.tabs.transition.MainTabsPopTransition
import la.me.leo.animatedapp.tabs.transition.MainTabsPushTransition
import la.me.leo.core.base.ui.BaseFragment
import la.me.leo.core_animation.fragment.navigateToTab

internal class TabsFragment : BaseFragment<FragmentTabsBinding>() {

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTabsBinding {
        return FragmentTabsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabs()
        if (savedInstanceState == null) {
            showFragment(TAG_DISCOVERY, 0) { DiscoveryFragment() }
        }
    }

    private fun setUpTabs() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener f@{
            when (it.itemId) {
                R.id.item_discovery -> {
                    showFragment(TAG_DISCOVERY, 0) { DiscoveryFragment() }
                    return@f true
                }
                R.id.item_profile -> {
                    showFragment(TAG_PROFILE, 1) { ProfileFragment() }
                    return@f true
                }
            }
            return@f false
        }
    }

    private fun showFragment(tag: String, tabIndex: Int, fragmentFactory: () -> Fragment) {
        val bottomNavigationView = binding.bottomNavigationView
        val pushAnimationX = ((tabIndex + 0.5f) / 2 * bottomNavigationView.width).toInt()
        val pushAnimationY = (bottomNavigationView.bottom + bottomNavigationView.top) / 2
        childFragmentManager.navigateToTab(
            tag,
            R.id.fragmentRoot,
            fragmentFactory,
            MainTabsPushTransition(
                pushAnimationX,
                pushAnimationY
            ),
            MainTabsPopTransition()
        )
    }

    companion object {
        private const val TAG_DISCOVERY = "tag discovery"
        private const val TAG_PROFILE = "tag profile"
    }
}
