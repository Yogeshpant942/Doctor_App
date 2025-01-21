package com.example.doctorapp.InApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentDifferentDoctorBinding

class DifferentDoctorFragment : Fragment() {
    private lateinit var binding: FragmentDifferentDoctorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDifferentDoctorBinding.inflate(inflater, container, false)

        // Set up click listeners for each category
        binding.All.setOnClickListener { navigateToAllDoctorFragment("All") }
        binding.heart.setOnClickListener { navigateToAllDoctorFragment("heart") }
        binding.cardio.setOnClickListener { navigateToAllDoctorFragment("cardio") }
        binding.gand.setOnClickListener { navigateToAllDoctorFragment("gand") }
        binding.PEDIATRIC.setOnClickListener { navigateToAllDoctorFragment("PEDIATRIC") }

        return binding.root
    }

    /**
     * Navigates to the AllDoctorFragment with the specified category.
     */
    private fun navigateToAllDoctorFragment(category: String) {
        val fragment = AllDoctorFragment().apply {
            arguments = Bundle().apply {
                putString("Category", category)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
