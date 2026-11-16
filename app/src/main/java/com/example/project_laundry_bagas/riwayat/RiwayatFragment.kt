package com.example.project_laundry_bagas.riwayat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.project_laundry_bagas.databinding.FragmentRiwayatBinding
import com.google.android.material.tabs.TabLayoutMediator

class RiwayatFragment : Fragment() {

    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!
    private val repository = RiwayatRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Riwayat Pesanan"

        binding.viewPager.adapter = RiwayatTabsAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Semua"
                1 -> "Aktif"
                2 -> "Selesai"
                else -> throw IllegalArgumentException("Tab riwayat tidak dikenal: $position")
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        if (_binding != null) {
            repository.getAll(requireContext())
            updateActiveBadge(repository.getAktif().size)
        }
    }

    private fun updateActiveBadge(activeCount: Int) {
        val activeTab = binding.tabLayout.getTabAt(1) ?: return
        val badge = activeTab.getOrCreateBadge()
        badge.isVisible = activeCount > 0
        if (activeCount > 0) badge.number = activeCount
    }

    override fun onDestroyView() {
        binding.viewPager.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
