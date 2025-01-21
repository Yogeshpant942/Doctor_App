package com.example.doctorapp.StartingInterface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentStartScreen1Binding
import com.example.doctorapp.databinding.FragmentStartScreen2Binding

class StartScreen2Fragment : Fragment() {
    private lateinit var binding: FragmentStartScreen2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStartScreen2Binding.inflate(inflater, container, false)
        binding.NextButton.setOnClickListener {
            findNavController().navigate(R.id.action_startScreen2Fragment_to_startScreen3Fragment)
        }
        return binding.root
    }


}
