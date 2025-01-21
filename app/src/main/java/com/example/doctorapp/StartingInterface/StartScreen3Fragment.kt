package com.example.doctorapp.StartingInterface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentStartScreen2Binding
import com.example.doctorapp.databinding.FragmentStartScreen3Binding


class StartScreen3Fragment : Fragment() {
    lateinit var binding: FragmentStartScreen3Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartScreen3Binding.inflate(layoutInflater,container,false)

        binding.NextButton.setOnClickListener {
            findNavController().navigate(R.id.action_startScreen3Fragment_to_homeFragment)
        }
        return binding.root
    }


}