package com.example.project_laundry_bagas.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project_laundry_bagas.R
import com.example.project_laundry_bagas.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Pengguna") ?: "Pengguna"

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.title_beranda)
            subtitle = getString(R.string.greeting_user, username)
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
                menu.findItem(R.id.action_profile)?.isVisible = false
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_search -> {
                        Toast.makeText(requireContext(), "Search Clicked", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.action_settings -> {
                        Toast.makeText(requireContext(), "Settings Clicked", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val layanan = listOf(
            LayananModel("kiloan", "Kiloan", "Cuci pakaian harian", "Rp7.000/kg", R.drawable.ic_baju_cowok),
            LayananModel("satuan", "Satuan", "Cuci pakaian khusus", "Rp25.000/item", R.drawable.ic_baju_cewek),
            LayananModel("express", "Express", "Selesai lebih cepat", "Rp12.000/kg", R.drawable.ic_kilat),
            LayananModel("sepatu", "Sepatu", "Perawatan sepatu", "Rp30.000/pasang", R.drawable.ic_sepatu)
        )
        binding.rvLayanan.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvLayanan.isNestedScrollingEnabled = false
        binding.rvLayanan.adapter = LayananAdapter(layanan) { item ->
            val intent = Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra("extra_layanan", item.nama)
                putExtra(
                    OrderActivity.EXTRA_PRICE_PER_KG,
                    when (item.kode) {
                        "satuan" -> 25000
                        "express" -> 12000
                        "sepatu" -> 30000
                        else -> 7000
                    }
                )
                putExtra(
                    OrderActivity.EXTRA_ESTIMATE_DAYS,
                    when (item.kode) {
                        "satuan" -> "1-2 hari kerja"
                        "express" -> "1 hari kerja"
                        "sepatu" -> "2 hari kerja"
                        else -> "2-3 hari kerja"
                    }
                )
            }
            startActivity(intent)
        }

        binding.btnBuatPesanan.setOnClickListener {
            startActivity(Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra("extra_layanan", "Kiloan")
            })
        }

        binding.btnTipsPakaian.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        updateActiveOrderCard()
    }

    private fun updateActiveOrderCard() {
        val orderPrefs = requireContext().getSharedPreferences("order_pref", Context.MODE_PRIVATE)
        val lastOrderId = orderPrefs.getString("last_order_id", null)
        val lastOrderJadwal = orderPrefs.getString("last_order_jadwal", null)
        val lastOrderStatus = orderPrefs.getString("last_order_status", null)
        val lastOrderService = orderPrefs.getString("last_order_service", "Cuci Regular")

        if (lastOrderId != null && lastOrderJadwal != null && lastOrderStatus != null) {
            binding.tvOrderId.text = "No. Pesanan: $lastOrderId"
            binding.tvOrderStatus.text = lastOrderStatus
            binding.tvOrderService.text = "Layanan: $lastOrderService"
            binding.tvOrderDate.text = "Jadwal: $lastOrderJadwal"
        } else {
            binding.tvOrderId.text = "Belum Ada Pesanan"
            binding.tvOrderStatus.text = "Kosong"
            binding.tvOrderService.text = "Belum ada pesanan aktif"
            binding.tvOrderDate.text = "Silakan buat pesanan baru"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
