package la.me.leo.animatedapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import la.me.leo.animatedapp.databinding.ActivityMainBinding
import la.me.leo.animatedapp.tabs.TabsFragment

internal class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragmentRoot, TabsFragment())
            }
        }
    }
}
