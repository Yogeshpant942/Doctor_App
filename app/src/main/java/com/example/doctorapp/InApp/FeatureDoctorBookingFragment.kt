package com.example.doctorapp.InApp

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentFeatureDoctorBookingBinding
class FeatureDoctorBookingFragment : Fragment() {
   lateinit var binding :FragmentFeatureDoctorBookingBinding
   val args:FeatureDoctorBookingFragment by navArgs()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeatureDoctorBookingBinding.inflate(layoutInflater,container,false)
        val doctorName = arguments?.getString("name")
        val doctorWork = arguments?.getString("work")
        val doctorImage = arguments?.getString("image")

        val price = arguments?.getString("GetPrice")
        binding.name.text = doctorName
        binding.work.text = doctorWork
      val uriString:String = doctorImage.toString()
        val uri:Uri = Uri.parse(uriString)
        Glide.with(requireContext()).load(uri).into(binding.imageView)
        binding.price.text = price
        return binding.root

    }
}