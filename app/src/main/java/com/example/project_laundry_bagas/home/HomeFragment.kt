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
                // Hide profile menu item on home fragment if present
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

        // Service Items Click Listeners
        binding.itemKiloan.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Kiloan")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 7000)
                putExtra(OrderActivity.EXTRA_ESTIMATE_DAYS, "2-3 hari kerja")
            }
            startActivity(intent)
        }

        binding.itemSatuan.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Satuan")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 15000)
                putExtra(OrderActivity.EXTRA_ESTIMATE_DAYS, "1-2 hari kerja")
            }
            startActivity(intent)
        }

        binding.itemExpress.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Express")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 12000)
                putExtra(OrderActivity.EXTRA_ESTIMATE_DAYS, "1 hari kerja")
            }
            startActivity(intent)
        }

        binding.itemSepatu.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Sepatu")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 25000)
                putExtra(OrderActivity.EXTRA_ESTIMATE_DAYS, "2 hari kerja")
            }
            startActivity(intent)
        }

        // Action Buttons
        binding.btnBuatPesanan.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java)
            startActivity(intent)
        }

        binding.btnLihatLayanan.setOnClickListener {
            val intent = Intent(requireContext(), LayananActivity::class.java)
            startActivity(intent)
        }

        binding.btnTipsPakaian.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}