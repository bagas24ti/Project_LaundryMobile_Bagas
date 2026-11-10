package com.example.project_laundry_bagas.riwayat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.project_laundry_bagas.databinding.FragmentRiwayatBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class RiwayatFragment : Fragment() {

    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!
    private var orders: List<RiwayatModel> = emptyList()

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
        val preferences = requireContext().getSharedPreferences("order_pref", Context.MODE_PRIVATE)
        val storedOrders = preferences.getString("order_list", null)
        if (storedOrders == null) {
            // Data contoh hanya dibuat sekali, lalu pesanan nyata dapat ditambahkan ke daftar.
            orders = listOf(
                RiwayatModel("#KL-0921", "Cuci Kiloan", "3 kg", "21 Sep 2026, 10:00", "Rp21.000", "Diproses"),
                RiwayatModel("#KL-0874", "Cuci Satuan", "2 item", "18 Sep 2026, 09:00", "Rp50.000", "Selesai"),
                RiwayatModel("#KL-0810", "Cuci Express", "5 kg", "12 Sep 2026, 16:00", "Rp60.000", "Selesai")
            )
            preferences.edit().putString("order_list", ordersToJson(orders).toString()).apply()
        } else {
            orders = try {
                val jsonOrders = JSONArray(storedOrders)
                (0 until jsonOrders.length()).map { index ->
                    val order = jsonOrders.getJSONObject(index)
                    RiwayatModel(
                        id = order.optString("id"),
                        layanan = order.optString("layanan"),
                        jumlah = order.optString("jumlah"),
                        jadwal = order.optString("jadwal"),
                        total = order.optString("total"),
                        status = order.optString("status")
                    )
                }
            } catch (exception: JSONException) {
                Toast.makeText(
                    requireContext(),
                    "Data riwayat pesanan tidak dapat dibaca",
                    Toast.LENGTH_LONG
                ).show()
                emptyList()
            }
        }
        binding.lvRiwayat.adapter = RiwayatAdapter(requireContext(), orders)
    }

    private fun ordersToJson(items: List<RiwayatModel>): JSONArray = JSONArray().apply {
        items.forEach { item ->
            put(JSONObject().apply {
                put("id", item.id)
                put("layanan", item.layanan)
                put("jumlah", item.jumlah)
                put("jadwal", item.jadwal)
                put("total", item.total)
                put("status", item.status)
            })
        }
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
        super.onDestroyView()
        _binding = null
    }
}
