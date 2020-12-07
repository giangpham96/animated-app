package la.me.leo.animatedapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import la.me.leo.animatedapp.databinding.ActivityMainBinding
import la.me.leo.animatedapp.tabs.MainTabsActivity

internal class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnTabsTransition.setOnClickListener {
            startActivity(Intent(this, MainTabsActivity::class.java))
        }
    }
}
