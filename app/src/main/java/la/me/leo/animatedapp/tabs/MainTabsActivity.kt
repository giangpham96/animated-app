package la.me.leo.animatedapp.tabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import la.me.leo.animatedapp.*
import la.me.leo.animatedapp.tabs.transition.MainTabsPopTransition
import la.me.leo.animatedapp.tabs.transition.MainTabsPushTransition
import la.me.leo.animatedapp.databinding.ActivityMainTabsBinding
import la.me.leo.animatedapp.tabs.discovery.DiscoveryFragment
import la.me.leo.animatedapp.tabs.profile.ProfileFragment
import la.me.leo.core_animation.fragment.navigateToTab

internal class MainTabsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainTabsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTabsBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        supportFragmentManager.navigateToTab(
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
