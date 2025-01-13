package ai.corca.corcaads_android_plugins

import ai.corca.adcio_android_plugins.databinding.ActivityMainBinding
import ai.corca.corcaads_android_plugins.corca_ads_demo.presentation.SuggestionActivity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlacement.setOnClickListener {
            val intent = Intent(this, SuggestionActivity::class.java)
            startActivity(intent)
        }
    }
}
