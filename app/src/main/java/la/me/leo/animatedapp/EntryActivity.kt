package la.me.leo.animatedapp

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import la.me.leo.animatedapp.databinding.ActivityEntryBinding
import la.me.leo.core.base.navigation.NavigationEvent
import la.me.leo.core.base.navigation.Navigator
import la.me.leo.core.base.navigation.ToTabs
import la.me.leo.core.base.navigation.ToVenue
import la.me.leo.core_animation.fragment.replace
import la.me.leo.onboarding.OnboardingFragment
import la.me.leo.tabs.TabsFragment
import la.me.leo.venue_detail_page.VenueDetailPageFragment

internal class EntryActivity : AppCompatActivity(),
    Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWindow()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragmentRoot, OnboardingFragment())
            }
        }
    }

    private fun setupWindow() {
        var flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.light_100p)
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        window.decorView.systemUiVisibility = flags
    }

    override fun navigateTo(event: NavigationEvent) {
        when (event) {
            ToTabs -> navigateToTabs()
            ToVenue -> navigateToVenue()
        }
    }

    private fun navigateToVenue() {
        supportFragmentManager.commit {
            add(R.id.fragmentRoot, VenueDetailPageFragment())
            addToBackStack(null)
        }
    }

    private fun navigateToTabs() {
        supportFragmentManager.replace(R.id.fragmentRoot, TabsFragment())
    }
}
