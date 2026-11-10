package com.example.project_laundry_bagas.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.project_laundry_bagas.R
import com.example.project_laundry_bagas.databinding.ActivityOrderBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding

    private var weight: Int = DEFAULT_WEIGHT
    private var pricePerKg: Int = DEFAULT_PRICE_PER_KG
    private var serviceName: String = DEFAULT_SERVICE_NAME
    private var estimateDays: String = DEFAULT_ESTIMATE_DAYS

    // Variable untuk menyimpan nilai dari MaterialDatePicker dan MaterialTimePicker
    private var selectedTimestampUtc: Long? = null
    private var selectedHour: Int? = null
    private var selectedMinute: Int? = null

    companion object {
        const val EXTRA_SERVICE_NAME = "extra_service_name"
        const val EXTRA_PRICE_PER_KG = "extra_price_per_kg"
        const val EXTRA_ESTIMATE_DAYS = "extra_estimate_days"

        private const val KEY_WEIGHT = "key_weight"
        private const val DEFAULT_WEIGHT = 3
        private const val DEFAULT_PRICE_PER_KG = 7000
        private const val DEFAULT_SERVICE_NAME = "Cuci Kiloan"
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

        // Baca extra_layanan dari Intent (default "Kiloan")
        val extraLayanan = intent.getStringExtra("extra_layanan") ?: intent.getStringExtra(EXTRA_SERVICE_NAME) ?: "Kiloan"
        serviceName = when (extraLayanan) {
            "Kiloan", "Cuci Kiloan" -> "Cuci Kiloan"
            "Satuan", "Cuci Satuan" -> "Cuci Satuan"
            "Express", "Cuci Express" -> "Cuci Express"
            "Sepatu", "Cuci Sepatu" -> "Cuci Sepatu"
            else -> "Cuci $extraLayanan"
        }

        pricePerKg = when (serviceName) {
            "Cuci Satuan" -> intent.getIntExtra(EXTRA_PRICE_PER_KG, 25000)
            "Cuci Express" -> intent.getIntExtra(EXTRA_PRICE_PER_KG, 12000)
            "Cuci Sepatu" -> intent.getIntExtra(EXTRA_PRICE_PER_KG, 30000)
            else -> intent.getIntExtra(EXTRA_PRICE_PER_KG, 7000)
        }

        estimateDays = when (serviceName) {
            "Cuci Satuan" -> "1-2 hari kerja"
            "Cuci Express" -> "1 hari kerja"
            "Cuci Sepatu" -> "2 hari kerja"
            else -> intent.getStringExtra(EXTRA_ESTIMATE_DAYS) ?: "2-3 hari kerja"
        }

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
        binding.tvServiceDetail.text = getString(
            R.string.service_detail_format,
            formatRupiah(pricePerKg),
            quantityUnit(),
            estimateDays
        )
        binding.lblEstimasiBerat.text = when (serviceName) {
            "Cuci Satuan" -> "JUMLAH ITEM"
            "Cuci Sepatu" -> "JUMLAH PASANG"
            else -> getString(R.string.label_estimasi_berat)
        }
        binding.tvWeightTitle.text = when (serviceName) {
            "Cuci Satuan" -> "Jumlah pakaian"
            "Cuci Sepatu" -> "Jumlah sepatu"
            else -> getString(R.string.weight_title)
        }
        binding.tvWeightSubtext.text = when (serviceName) {
            "Cuci Satuan" -> "Ditagih sesuai jumlah item"
            "Cuci Sepatu" -> "Ditagih sesuai jumlah pasang"
            else -> getString(R.string.weight_subtext)
        }

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

        // 5. MaterialDatePicker untuk Tanggal Jemput
        binding.etTanggal.setOnClickListener {
            showDatePicker()
        }

        // 6. MaterialTimePicker untuk Jam Jemput
        binding.etJam.setOnClickListener {
            showTimePicker()
        }

        // 7. Order Button Click
        binding.btnOrder.setOnClickListener {
            handleOrderSubmit()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun showDatePicker() {
        val constraints = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .build()

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih tanggal jemput")
            .setCalendarConstraints(constraints)
            .setSelection(selectedTimestampUtc ?: MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            selectedTimestampUtc = selection
            val formatter = SimpleDateFormat("dd MMM yyyy",
                Locale.Builder().setLanguage("id").setRegion("ID").build()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            binding.etTanggal.setText(formatter.format(Date(selection)))
            binding.etTanggal.error = null
        }

        datePicker.show(supportFragmentManager, "DATE_PICKER")
    }

    private fun showTimePicker() {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(selectedHour ?: 12)
            .setMinute(selectedMinute ?: 0)
            .setTitleText("Pilih jam jemput")
            .build()

        timePicker.addOnPositiveButtonClickListener {
            selectedHour = timePicker.hour
            selectedMinute = timePicker.minute
            val formattedTime = String.format(Locale.getDefault(),
                "%02d:%02d", timePicker.hour, timePicker.minute)
            binding.etJam.setText(formattedTime)
            binding.etJam.error = null
        }

        timePicker.show(supportFragmentManager, "TIME_PICKER")
    }

    private fun updateWeightUI() {
        binding.tvWeightValue.text = "$weight ${quantityUnit()}"

        binding.btnMinus.isEnabled = weight > MIN_WEIGHT
        binding.btnMinus.alpha = if (weight > MIN_WEIGHT) 1.0f else 0.4f

        binding.btnPlus.isEnabled = weight < MAX_WEIGHT
        binding.btnPlus.alpha = if (weight < MAX_WEIGHT) 1.0f else 0.4f

        val total = weight * pricePerKg
        binding.tvTotalPrice.text = formatRupiah(total)
    }

    private fun quantityUnit(): String = when (serviceName) {
        "Cuci Satuan" -> "item"
        "Cuci Sepatu" -> "pasang"
        else -> "kg"
    }

    private fun handleOrderSubmit() {
        val dateStr = binding.etTanggal.text?.toString()?.trim() ?: ""
        val timeStr = binding.etJam.text?.toString()?.trim() ?: ""

        if (dateStr.isEmpty() || timeStr.isEmpty()) {
            if (dateStr.isEmpty()) binding.etTanggal.error = "Pilih tanggal jemput"
            if (timeStr.isEmpty()) binding.etJam.error = "Pilih jam jemput"
            Toast.makeText(this, "Pilih tanggal dan jam jemput terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedTimestampUtc != null && selectedHour != null && selectedMinute != null) {
            val nowCalendar = Calendar.getInstance()
            val selectedCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                timeInMillis = selectedTimestampUtc!!
            }

            val isToday = nowCalendar.get(Calendar.YEAR) == selectedCalendar.get(Calendar.YEAR) &&
                    nowCalendar.get(Calendar.DAY_OF_YEAR) == selectedCalendar.get(Calendar.DAY_OF_YEAR)

            if (isToday) {
                val currentHour = nowCalendar.get(Calendar.HOUR_OF_DAY)
                val currentMinute = nowCalendar.get(Calendar.MINUTE)

                if (selectedHour!! < currentHour || (selectedHour!! == currentHour && selectedMinute!! <= currentMinute)) {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Peringatan")
                        .setMessage("Jam jemput sudah terlewat")
                        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        .show()
                    return
                }
            }
        }

        val orderNumber = "#KL-${System.currentTimeMillis().toString().takeLast(4)}"
        val orderSchedule = "$dateStr, $timeStr"

        val orderPrefs = getSharedPreferences("order_pref", MODE_PRIVATE)
        orderPrefs.edit().apply {
            putString("last_order_id", orderNumber)
            putString("last_order_jadwal", orderSchedule)
            putString("last_order_service", serviceName)
            putString("last_order_status", "Menunggu dijemput")
            apply()
        }
        saveOrderToHistory(orderNumber, orderSchedule)

        sendOrderNotifications(orderNumber, orderSchedule)
    }

    // Simpan setiap pesanan sebagai JSON agar dapat ditampilkan kembali di Riwayat.
    private fun saveOrderToHistory(orderNumber: String, orderSchedule: String) {
        val orderPrefs = getSharedPreferences("order_pref", MODE_PRIVATE)
        val orders = try {
            JSONArray(orderPrefs.getString("order_list", "[]"))
        } catch (exception: org.json.JSONException) {
            throw IllegalStateException("Data riwayat pesanan tidak valid", exception)
        }
        val total = weight * pricePerKg
        val order = JSONObject().apply {
            put("id", orderNumber)
            put("layanan", serviceName)
            put("jumlah", "${weight} ${quantityUnit()}")
            put("jadwal", orderSchedule)
            put("total", formatRupiah(total))
            put("status", "Menunggu dijemput")
        }
        orders.put(order)
        orderPrefs.edit().putString("order_list", orders.toString()).apply()
    }

    private fun sendOrderNotifications(orderNumber: String, orderSchedule: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Izin Notifikasi")
                    .setMessage("Aplikasi memerlukan izin notifikasi untuk memberikan pembaruan status pesanan Kilau Laundry.")
                    .setPositiveButton("Izinkan") { _, _ ->
                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                    .setNegativeButton("Nanti") { _, _ ->
                        finishOrderProcess(orderNumber)
                    }
                    .show()
                return
            }
        }

        triggerNotifications(orderNumber, orderSchedule)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        val orderPrefs = getSharedPreferences("order_pref", Context.MODE_PRIVATE)
        val orderNumber = orderPrefs.getString("last_order_id", "") ?: ""
        val orderSchedule = orderPrefs.getString("last_order_jadwal", "") ?: ""

        if (isGranted) {
            triggerNotifications(orderNumber, orderSchedule)
        } else {
            finishOrderProcess(orderNumber)
        }
    }

    private fun triggerNotifications(orderNumber: String, orderSchedule: String) {
        NotificationHelper.showOrderNotification(
            this,
            "Pesanan Diterima",
            "Pesanan $orderNumber berhasil dibuat. Jadwal penjemputan: $orderSchedule",
            notificationId = 1001
        )

        lifecycleScope.launch {
            delay(10000)
            val orderPrefs = getSharedPreferences("order_pref", Context.MODE_PRIVATE)
            orderPrefs.edit().putString("last_order_status", "Sedang diproses").apply()

            NotificationHelper.showOrderNotification(
                this@OrderActivity,
                "Pesanan Sedang Diproses",
                "Pesanan $orderNumber sedang dalam proses pencucian.",
                notificationId = 1002
            )
        }

        finishOrderProcess(orderNumber)
    }

    private fun finishOrderProcess(orderNumber: String) {
        Toast.makeText(this, "Pesanan $orderNumber berhasil dibuat!", Toast.LENGTH_LONG).show()
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