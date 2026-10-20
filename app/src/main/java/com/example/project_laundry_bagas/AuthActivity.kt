package com.example.project_laundry_bagas

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_laundry_bagas.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty()) {
                binding.etUsername.error = "Username wajib diisi"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.etPassword.error = "Kata sandi wajib diisi"
                return@setOnClickListener
            }

            val regPref = getSharedPreferences("register_pref", MODE_PRIVATE)
            val regUsername = regPref.getString("username", null)
            val regPassword = regPref.getString("password", null)

            val isRegisteredUser = regUsername != null && username == regUsername && password == regPassword
            val isDemoUser = username == password

            if (isRegisteredUser || isDemoUser) {
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", if (isRegisteredUser) regUsername else username)
                editor.apply()

                startActivity(Intent(this, BaseActivity::class.java))
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username atau kata sandi tidak cocok. Silakan coba lagi.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}