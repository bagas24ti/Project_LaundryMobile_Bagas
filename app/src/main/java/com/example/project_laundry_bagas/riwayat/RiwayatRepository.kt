package com.example.project_laundry_bagas.riwayat

import android.content.Context
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONException

class RiwayatRepository {

    private var orders: List<RiwayatModel> = emptyList()

    fun getAll(context: Context): List<RiwayatModel> {
        val storedOrders = context
            .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .getString(ORDERS_KEY, null)

        orders = if (storedOrders.isNullOrBlank()) {
            sampleOrders
        } else {
            try {
                val jsonOrders = JSONArray(storedOrders)
                if (jsonOrders.length() == 0) {
                    sampleOrders
                } else {
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
                }
            } catch (exception: JSONException) {
                Toast.makeText(
                    context,
                    "Data riwayat pesanan tidak dapat dibaca",
                    Toast.LENGTH_LONG
                ).show()
                emptyList()
            }
        }
        return orders
    }

    fun getAktif(): List<RiwayatModel> = orders.filter {
        !it.status.equals(STATUS_FINISHED, ignoreCase = true) &&
            !it.status.equals(STATUS_CANCELLED, ignoreCase = true)
    }

    fun getSelesai(): List<RiwayatModel> = orders.filter {
        it.status.equals(STATUS_FINISHED, ignoreCase = true)
    }

    private companion object {
        const val PREFERENCES_NAME = "order_pref"
        const val ORDERS_KEY = "order_list"
        const val STATUS_FINISHED = "Selesai"
        const val STATUS_CANCELLED = "Dibatalkan"

        val sampleOrders = listOf(
            RiwayatModel("#KL-0921", "Cuci Kiloan", "3 kg", "21 Sep 2026, 10:00", "Rp21.000", "Diproses"),
            RiwayatModel("#KL-0874", "Cuci Satuan", "2 item", "18 Sep 2026, 09:00", "Rp50.000", "Selesai"),
            RiwayatModel("#KL-0810", "Cuci Express", "5 kg", "12 Sep 2026, 16:00", "Rp60.000", "Selesai")
        )
    }
}
