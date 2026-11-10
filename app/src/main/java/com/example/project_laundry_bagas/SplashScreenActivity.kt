package com.example.project_laundry_bagas

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.project_laundry_bagas.databinding.ActivitySplashScreenBinding
import com.example.project_laundry_bagas.home.NotificationHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi NotificationChannel sekali saja saat aplikasi dibuka
        NotificationHelper.createChannel(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            delay(2000) // simulasi pengambilan data 2 detik
            val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)
            val onboardDone = getSharedPreferences("app_pref", MODE_PRIVATE)
                .getBoolean("onboard_done", false)
            val target = when {
                isLogin -> BaseActivity::class.java
                !onboardDone -> com.example.project_laundry_bagas.onboarding.OnboardingActivity::class.java
                else -> AuthActivity::class.java
            }
            startActivity(Intent(this@SplashScreenActivity, target))
            finish()
        }
    }
}