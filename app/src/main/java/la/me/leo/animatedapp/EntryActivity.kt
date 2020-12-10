package la.me.leo.animatedapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import la.me.leo.animatedapp.databinding.ActivityEntryBinding
import la.me.leo.tabs.TabsFragment

internal class EntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragmentRoot, TabsFragment())
            }
        }
    }
}
