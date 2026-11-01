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

        // Klik 4 Kotak Layanan Utama -> Buka OrderActivity dengan extra_layanan
        binding.itemKiloan.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra("extra_layanan", "Kiloan")
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Kiloan")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 7000)
                putExtra(OrderActivity.EXTRA_ESTIMATE_DAYS, "2-3 hari kerja")
            }
            startActivity(intent)
        }

        binding.itemSatuan.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra("extra_layanan", "Satuan")
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Satuan")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 15000)
                putExtra(OrderActivity.EXTRA_ESTIMATE_DAYS, "1-2 hari kerja")
            }
            startActivity(intent)
        }

        binding.itemExpress.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra("extra_layanan", "Express")
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Express")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 12000)
                putExtra(OrderActivity.EXTRA_ESTIMATE_DAYS, "1 hari kerja")
            }
            startActivity(intent)
        }

        binding.itemSepatu.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra("extra_layanan", "Sepatu")
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Sepatu")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 25000)
                putExtra(OrderActivity.EXTRA_ESTIMATE_DAYS, "2 hari kerja")
            }
            startActivity(intent)
        }

        // Tombol Buat Pesanan Baru (default extra_layanan "Kiloan")
        binding.btnBuatPesanan.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra("extra_layanan", "Kiloan")
            }
            startActivity(intent)
        }

        // Tombol Tips Merawat Pakaian
        binding.btnTipsPakaian.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            startActivity(intent)
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