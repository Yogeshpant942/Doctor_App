package com.example.doctorapp.InApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentThankYouDialogBinding


class ThankYouDialogFragment : DialogFragment() {
    lateinit var binding:FragmentThankYouDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThankYouDialogBinding.inflate(layoutInflater,container,false)
        binding.btnDone.setOnClickListener {
            //navigate to like fragment
        }
        return binding.root
    }


}