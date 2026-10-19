package com.example.project_laundry_bagas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.project_laundry_bagas.databinding.ActivityBaseBinding
import com.example.project_laundry_bagas.home.HomeFragment
import com.example.project_laundry_bagas.profil.ProfilFragment
import com.example.project_laundry_bagas.riwayat.RiwayatFragment

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.riwayat -> {
                    replaceFragment(RiwayatFragment())
                    true
                }
                R.id.profil -> {
                    replaceFragment(ProfilFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}