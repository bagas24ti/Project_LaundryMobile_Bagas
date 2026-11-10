package com.example.project_laundry_bagas.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project_laundry_bagas.databinding.FragmentOnboard1Binding

class Onboard1Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentOnboard1Binding.inflate(inflater, container, false).root
}
