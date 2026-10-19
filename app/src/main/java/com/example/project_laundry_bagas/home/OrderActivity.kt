package com.example.project_laundry_bagas.home

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_laundry_bagas.R
import com.example.project_laundry_bagas.databinding.ActivityOrderBinding
import com.google.android.material.chip.Chip
import java.text.NumberFormat
import java.util.Locale

class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding

    private var weight: Int = DEFAULT_WEIGHT
    private var pricePerKg: Int = DEFAULT_PRICE_PER_KG
    private var serviceName: String = DEFAULT_SERVICE_NAME
    private var estimateDays: String = DEFAULT_ESTIMATE_DAYS

    companion object {
        const val EXTRA_SERVICE_NAME = "extra_service_name"
        const val EXTRA_PRICE_PER_KG = "extra_price_per_kg"
        const val EXTRA_ESTIMATE_DAYS = "extra_estimate_days"

        private const val KEY_WEIGHT = "key_weight"
        private const val DEFAULT_WEIGHT = 3
        private const val DEFAULT_PRICE_PER_KG = 7000
        private const val DEFAULT_SERVICE_NAME = "Cuci + Setrika"
        private const val DEFAULT_ESTIMATE_DAYS = "2-3 hari kerja"
        private const val MIN_WEIGHT = 1
        private const val MAX_WEIGHT = 30
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        if (savedInstanceState != null) {
            weight = savedInstanceState.getInt(KEY_WEIGHT, DEFAULT_WEIGHT)
        }

        serviceName = intent.getStringExtra(EXTRA_SERVICE_NAME) ?: DEFAULT_SERVICE_NAME
        pricePerKg = intent.getIntExtra(EXTRA_PRICE_PER_KG, DEFAULT_PRICE_PER_KG)
        estimateDays = intent.getStringExtra(EXTRA_ESTIMATE_DAYS) ?: DEFAULT_ESTIMATE_DAYS

        // 1. Toolbar Setup
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.title_buat_pesanan)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        // 2. Setup Service Info
        binding.tvServiceName.text = serviceName
        binding.tvServiceDetail.text = getString(R.string.service_detail_format, formatRupiah(pricePerKg), estimateDays)

        // 3. Setup Address Card Click
        binding.cardAlamat.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_select_address), Toast.LENGTH_SHORT).show()
        }

        // 4. Weight Stepper Setup
        updateWeightUI()

        binding.btnMinus.setOnClickListener {
            if (weight > MIN_WEIGHT) {
                weight--
                updateWeightUI()
            }
        }

        binding.btnPlus.setOnClickListener {
            if (weight < MAX_WEIGHT) {
                weight++
                updateWeightUI()
            }
        }

        // 5. Order Button
        binding.btnOrder.setOnClickListener {
            handleOrderSubmit()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun updateWeightUI() {
        binding.tvWeightValue.text = getString(R.string.weight_format, weight)

        binding.btnMinus.isEnabled = weight > MIN_WEIGHT
        binding.btnMinus.alpha = if (weight > MIN_WEIGHT) 1.0f else 0.4f

        binding.btnPlus.isEnabled = weight < MAX_WEIGHT
        binding.btnPlus.alpha = if (weight < MAX_WEIGHT) 1.0f else 0.4f

        val total = weight * pricePerKg
        binding.tvTotalPrice.text = formatRupiah(total)
    }

    private fun handleOrderSubmit() {
        val selectedDateChipId = binding.cgDate.checkedChipId
        val dateText = if (selectedDateChipId != View.NO_ID) {
            binding.cgDate.findViewById<Chip>(selectedDateChipId)?.text?.toString() ?: getString(R.string.chip_today)
        } else {
            getString(R.string.chip_today)
        }

        val selectedTimeSlotChipId = binding.cgTimeSlot.checkedChipId
        if (selectedTimeSlotChipId == View.NO_ID) {
            Toast.makeText(this, getString(R.string.toast_select_time_first), Toast.LENGTH_SHORT).show()
            return
        }

        val timeText = binding.cgTimeSlot.findViewById<Chip>(selectedTimeSlotChipId)?.text?.toString() ?: ""

        val summaryMessage = "Pesanan $weight kg ($serviceName) dijadwalkan $dateText, $timeText"
        Toast.makeText(this, summaryMessage, Toast.LENGTH_LONG).show()

        finish()
    }

    private fun formatRupiah(amount: Int): String {
        val localeID = Locale.Builder().setLanguage("id").setRegion("ID").build()
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.maximumFractionDigits = 0
        return numberFormat.format(amount).replace("Rp", "Rp").replace(",00", "")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_WEIGHT, weight)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}