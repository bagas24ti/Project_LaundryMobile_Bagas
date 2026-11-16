package com.example.project_laundry_bagas.riwayat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project_laundry_bagas.databinding.FragmentRiwayatListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RiwayatListFragment : Fragment() {

    private var _binding: FragmentRiwayatListBinding? = null
    private val binding get() = _binding!!
    private val repository = RiwayatRepository()
    private var orders: List<RiwayatModel> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRiwayatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lvRiwayat.emptyView = binding.tvKosong
        binding.lvRiwayat.setOnItemClickListener { _, _, position, _ ->
            showOrderDetail(orders[position])
        }
    }

    override fun onResume() {
        super.onResume()
        if (_binding != null) loadOrders()
    }

    private fun loadOrders() {
        val allOrders = repository.getAll(requireContext())
        orders = when (requireArguments().getString(ARG_FILTER)) {
            FILTER_ALL -> allOrders
            FILTER_ACTIVE -> repository.getAktif()
            FILTER_FINISHED -> repository.getSelesai()
            else -> throw IllegalArgumentException("Filter riwayat tidak dikenal")
        }
        binding.lvRiwayat.adapter = RiwayatAdapter(requireContext(), orders)
    }

    private fun showOrderDetail(order: RiwayatModel) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Detail Pesanan ${order.id}")
            .setMessage(
                "Layanan: ${order.layanan}\n" +
                    "Jumlah: ${order.jumlah}\n" +
                    "Jadwal: ${order.jadwal}\n" +
                    "Total: ${order.total}\n" +
                    "Status: ${order.status}"
            )
            .setPositiveButton("Tutup", null)
            .show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val ARG_FILTER = "filter"
        private const val FILTER_ALL = "semua"
        private const val FILTER_ACTIVE = "aktif"
        private const val FILTER_FINISHED = "selesai"

        fun newInstance(filter: String) = RiwayatListFragment().apply {
            arguments = Bundle().apply { putString(ARG_FILTER, filter) }
        }
    }
}
