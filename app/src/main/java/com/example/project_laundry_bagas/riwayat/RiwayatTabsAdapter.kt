package com.example.project_laundry_bagas.riwayat

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RiwayatTabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = FILTERS.size

    override fun createFragment(position: Int): Fragment =
        RiwayatListFragment.newInstance(FILTERS[position])

    companion object {
        private val FILTERS = listOf("semua", "aktif", "selesai")
    }
}
