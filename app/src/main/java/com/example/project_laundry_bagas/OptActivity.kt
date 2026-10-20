package com.example.project_laundry_bagas

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_laundry_bagas.databinding.ActivityOptBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Verifikasi"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        // Ambil data dari RegisterActivity
        val nama = intent.getStringExtra("nama") ?: ""
        val phone = intent.getStringExtra("phone") ?: ""
        val username = intent.getStringExtra("username") ?: ""
        val password = intent.getStringExtra("password") ?: ""

        binding.tvInfo.text = "Masukkan kode OTP untuk nomor $phone"

        binding.btnVerifikasi.setOnClickListener {
            val otp = binding.etOtp.text.toString().trim()

            if (otp.isEmpty()) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Verifikasi Gagal")
                    .setMessage("Kode OTP tidak boleh kosong")
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
                return@setOnClickListener
            }

            // OTP benar -> simpan data registrasi ke SharedPreferences
            val regPref = getSharedPreferences("register_pref", MODE_PRIVATE)
            val editor = regPref.edit()
            editor.putString("nama", nama)
            editor.putString("phone", phone)
            editor.putString("username", username)
            editor.putString("password", password)
            editor.apply()

            Toast.makeText(this, "Registrasi berhasil, silakan login", Toast.LENGTH_SHORT).show()

            // Kembali ke halaman login, tutup halaman register dan OTP
            val intent = Intent(this, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { onBackPressedDispatcher.onBackPressed(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}