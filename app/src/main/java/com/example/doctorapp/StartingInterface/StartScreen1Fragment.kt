package com.example.doctorapp.StartingInterface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentStartScreen1Binding


class StartScreen1Fragment : Fragment() {
    lateinit var binding:FragmentStartScreen1Binding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStartScreen1Binding.inflate(layoutInflater,container,false)
        binding.NextButton.setOnClickListener {
            findNavController().navigate(R.id.action_startScreen1Fragment_to_startScreen2Fragment)
        }
        return binding.root

    }


}