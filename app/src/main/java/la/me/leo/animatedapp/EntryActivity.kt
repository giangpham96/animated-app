package la.me.leo.animatedapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import la.me.leo.animatedapp.databinding.ActivityEntryBinding
import la.me.leo.core.base.navigation.NavigationEvent
import la.me.leo.core.base.navigation.Navigator
import la.me.leo.core.base.navigation.ToTabs
import la.me.leo.core_animation.fragment.replace
import la.me.leo.onboarding.OnboardingFragment
import la.me.leo.tabs.TabsFragment

internal class EntryActivity : AppCompatActivity(),
    Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragmentRoot, OnboardingFragment())
            }
        }
    }

    override fun navigateTo(event: NavigationEvent) {
        when(event) {
            ToTabs -> navigateToTabs()
        }
    }

    private fun navigateToTabs() {
        supportFragmentManager.replace(R.id.fragmentRoot, TabsFragment())
    }
}
