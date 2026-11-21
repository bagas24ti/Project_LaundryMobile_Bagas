package com.example.project_laundry_bagas.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_laundry_bagas.databinding.ItemLayananBinding

class LayananAdapter(
    private val list: List<LayananModel>,
    private val onItemClick: (LayananModel) -> Unit
) : RecyclerView.Adapter<LayananAdapter.LayananViewHolder>() {

    inner class LayananViewHolder(
        private val binding: ItemLayananBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LayananModel) {
            binding.ivEmoji.setImageResource(item.icon)
            binding.tvNamaLayanan.text = item.nama
            binding.tvDeskripsiLayanan.text = item.deskripsi
            binding.tvHargaLayanan.text = item.harga
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayananViewHolder {
        val binding = ItemLayananBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LayananViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LayananViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
