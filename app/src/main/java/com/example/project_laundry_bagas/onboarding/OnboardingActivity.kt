package com.example.project_laundry_bagas.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.project_laundry_bagas.AuthActivity
import com.example.project_laundry_bagas.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private val pages = listOf(Onboard1Fragment(), Onboard2Fragment(), Onboard3Fragment())
    private val openedFromProfile: Boolean
        get() = intent.getBooleanExtra(EXTRA_FROM_PROFILE, false)

    companion object {
        const val EXTRA_FROM_PROFILE = "from_profile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.onboardViewPager.adapter = OnboardFragmentAdapter(this, pages)
        binding.dotIndicator.attachTo(binding.onboardViewPager)
        binding.onboardViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.btnLanjut.text =
                    if (position == pages.lastIndex) "Mulai" else "Lanjut"
            }
        })

        binding.btnLewati.setOnClickListener { finishOnboarding() }
        binding.btnLanjut.setOnClickListener {
            if (binding.onboardViewPager.currentItem < pages.lastIndex) {
                binding.onboardViewPager.currentItem += 1
            } else {
                finishOnboarding()
            }
        }
    }

    private fun finishOnboarding() {
        // Tandai onboarding selesai agar tidak muncul lagi setelah aplikasi dibuka.
        getSharedPreferences("app_pref", MODE_PRIVATE)
            .edit()
            .putBoolean("onboard_done", true)
            .apply()

        if (openedFromProfile) {
            finish()
        } else {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }
}
