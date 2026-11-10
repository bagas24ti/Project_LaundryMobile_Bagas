package com.example.project_laundry_bagas.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project_laundry_bagas.databinding.FragmentOnboard2Binding

class Onboard2Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentOnboard2Binding.inflate(inflater, container, false).root
}
