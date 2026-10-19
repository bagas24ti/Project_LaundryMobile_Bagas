package com.example.project_laundry_bagas.riwayat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.project_laundry_bagas.databinding.FragmentRiwayatBinding
import com.example.project_laundry_bagas.home.OrderActivity

class RiwayatFragment : Fragment() {

    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!

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
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Riwayat Pesanan"
        }

        binding.btnRepeatOrder1.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Kiloan")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 7000)
            }
            startActivity(intent)
        }

        binding.btnRepeatOrder2.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Satuan")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 15000)
            }
            startActivity(intent)
        }

        binding.btnRepeatOrder3.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra(OrderActivity.EXTRA_SERVICE_NAME, "Cuci Express")
                putExtra(OrderActivity.EXTRA_PRICE_PER_KG, 12000)
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}