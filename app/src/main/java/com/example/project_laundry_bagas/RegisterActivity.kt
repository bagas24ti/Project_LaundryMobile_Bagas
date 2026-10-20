package com.example.project_laundry_bagas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_laundry_bagas.databinding.ActivityRegisterBinding
import kotlin.jvm.java

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Registrasi"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.btnDaftar.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (nama.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua kolom wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Kirim data ke halaman verifikasi OTP
            val intent = Intent(this, OtpActivity::class.java).apply {
                putExtra("nama", nama)
                putExtra("phone", phone)
                putExtra("username", username)
                putExtra("password", password)
            }
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}