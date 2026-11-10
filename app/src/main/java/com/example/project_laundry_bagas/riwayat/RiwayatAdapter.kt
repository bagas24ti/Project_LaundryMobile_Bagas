package com.example.project_laundry_bagas.riwayat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.project_laundry_bagas.R
import com.example.project_laundry_bagas.databinding.ItemRiwayatBinding

class RiwayatAdapter(
    context: Context,
    private val list: List<RiwayatModel>
) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    private class ViewHolder(val binding: ItemRiwayatBinding)

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): RiwayatModel = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder = if (convertView == null) {
            ViewHolder(ItemRiwayatBinding.inflate(inflater, parent, false))
                .also { it.binding.root.tag = it }
        } else {
            convertView.tag as ViewHolder
        }

        val item = getItem(position)
        with(holder.binding) {
            tvNomorPesanan.text = item.id
            tvStatusPesanan.text = item.status
            tvStatusPesanan.setBackgroundResource(
                when (item.status.lowercase()) {
                    "selesai" -> R.drawable.bg_status_selesai
                    "diproses", "sedang diproses" -> R.drawable.bg_status_diproses
                    else -> R.drawable.bg_status_menunggu
                }
            )
            tvLayananJumlah.text = "${item.layanan} · ${item.jumlah}"
            tvJadwalPesanan.text = item.jadwal
            tvTotalPesanan.text = item.total
        }
        return holder.binding.root
    }
}
