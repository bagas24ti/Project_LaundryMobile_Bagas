package com.example.project_laundry_bagas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_laundry_bagas.databinding.ActivityHalamanSecondBinding

class HalamanSecond : AppCompatActivity() {

    private lateinit var binding: ActivityHalamanSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHalamanSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Toolbar Setup
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Beranda"
            subtitle = "Selamat datang di Kilau"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        // Service Item Click Listeners
        binding.itemKiloan.setOnClickListener {
            Toast.makeText(this, "Layanan Kiloan dipilih", Toast.LENGTH_SHORT).show()
        }
        binding.itemSatuan.setOnClickListener {
            Toast.makeText(this, "Layanan Satuan dipilih", Toast.LENGTH_SHORT).show()
        }
        binding.itemExpress.setOnClickListener {
            Toast.makeText(this, "Layanan Express dipilih", Toast.LENGTH_SHORT).show()
        }
        binding.itemSepatu.setOnClickListener {
            Toast.makeText(this, "Layanan Sepatu dipilih", Toast.LENGTH_SHORT).show()
        }

        // Button Click Listeners
        binding.btnBuatPesanan.setOnClickListener {
            Toast.makeText(this, "Membuat Pesanan Baru...", Toast.LENGTH_SHORT).show()
        }

        binding.btnTipsPakaian.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }
    }

    // 2. Option Menu Setup
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.action_search -> {
                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_settings -> {
                Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}