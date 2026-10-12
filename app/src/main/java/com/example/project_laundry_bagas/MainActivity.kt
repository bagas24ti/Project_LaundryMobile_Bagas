package com.example.project_laundry_bagas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_laundry_bagas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. SharedPreferences & Username Display
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val username = sharedPref.getString("username", "Pengguna") ?: "Pengguna"

        // 2. Toolbar Setup
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.title_beranda)
            subtitle = getString(R.string.greeting_user, username)
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }

        // Service Click Listeners
        binding.itemKiloan.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java).apply {
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Kiloan")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 7000)
                putExtra(OrderActivity.EXTRA_ESTIMATE_DAYS, "2-3 hari kerja")
            }
            startActivity(intent)
        }
        binding.itemSatuan.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java).apply {
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Satuan")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 15000)
                putExtra(OrderActivity.EXTRA_ESTIMATE_DAYS, "1-2 hari kerja")
            }
            startActivity(intent)
        }
        binding.itemExpress.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java).apply {
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Express")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 12000)
                putExtra(OrderActivity.EXTRA_ESTIMATE_DAYS, "1 hari kerja")
            }
            startActivity(intent)
        }
        binding.itemSepatu.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java).apply {
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Sepatu")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 25000)
                putExtra(OrderActivity.EXTRA_ESTIMATE_DAYS, "2 hari kerja")
            }
            startActivity(intent)
        }

        // Action Buttons
        binding.btnBuatPesanan.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }

        binding.btnTipsPakaian.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }

        // Logout Handler
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_logout_title))
                .setMessage(getString(R.string.dialog_logout_message))
                .setPositiveButton(getString(R.string.dialog_yes)) { dialog, _ ->
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()
                    dialog.dismiss()

                    val intent = Intent(this, AuthActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton(getString(R.string.dialog_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                val intent = Intent(this, HalamanProfil::class.java)
                startActivity(intent)
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