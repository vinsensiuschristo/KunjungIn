package com.bangkit.kunjungin.ui.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bangkit.kunjungin.R
import com.bangkit.kunjungin.databinding.FragmentProfileBinding
import com.bangkit.kunjungin.ui.MainViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)

        mainViewModel.getSession().observe(viewLifecycleOwner) { user ->
            val userName = user.name
            binding.username.text = userName
        }

        binding.logout.setOnClickListener {
            it.setBackgroundResource(R.drawable.clicked_backround)
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Konfirmasi Keluar")
        alertDialogBuilder.setMessage("Apakah Anda yakin ingin keluar?")
        alertDialogBuilder.setPositiveButton("Ya") { _, _ ->
            logoutUser()
        }
        alertDialogBuilder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun logoutUser() {
        mainViewModel.logout()
        requireActivity().finish()
    }
}