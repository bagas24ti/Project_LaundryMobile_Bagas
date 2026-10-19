package com.example.project_laundry_bagas.profil

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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.project_laundry_bagas.AuthActivity
import com.example.project_laundry_bagas.R
import com.example.project_laundry_bagas.databinding.FragmentProfilBinding
import com.example.project_laundry_bagas.home.WebViewActivity

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.title_profil_saya)
        }

        val sharedPref = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Pengguna") ?: "Pengguna"

        binding.tvProfileName.text = username
        binding.tvInfoUsername.text = username

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_profil, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_edit -> {
                        Toast.makeText(requireContext(), getString(R.string.action_edit_profile), Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.action_settings -> {
                        Toast.makeText(requireContext(), getString(R.string.action_settings), Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Menu Items
        binding.btnMenuAlamat.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.menu_alamat_tersimpan), Toast.LENGTH_SHORT).show()
        }
        binding.btnMenuPembayaran.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.menu_metode_pembayaran), Toast.LENGTH_SHORT).show()
        }
        binding.btnMenuVoucher.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.menu_voucher_poin), Toast.LENGTH_SHORT).show()
        }
        binding.btnMenuNotifikasi.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.menu_notifikasi), Toast.LENGTH_SHORT).show()
        }
        binding.btnMenuTips.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            startActivity(intent)
        }
        binding.btnMenuBantuan.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.menu_bantuan), Toast.LENGTH_SHORT).show()
        }

        // Logout
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.dialog_logout_title))
                .setMessage(getString(R.string.dialog_logout_message))
                .setPositiveButton(getString(R.string.dialog_yes)) { dialog, _ ->
                    val pref = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                    pref.edit().clear().apply()
                    dialog.dismiss()

                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton(getString(R.string.dialog_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}