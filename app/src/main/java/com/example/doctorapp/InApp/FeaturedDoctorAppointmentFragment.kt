package com.example.doctorapp.InApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.doctorapp.Model.PatientNameAndContact
import com.example.doctorapp.ViewModel.FeaturedDoctorBookingViewModel
import com.example.doctorapp.databinding.FragmentFeaturedDoctorAppointmentBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FeaturedDoctorAppointmentFragment : Fragment() {

lateinit var binding:FragmentFeaturedDoctorAppointmentBinding
private val database:FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var viewModel:FeaturedDoctorBookingViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[FeaturedDoctorBookingViewModel::class.java]
        binding = FragmentFeaturedDoctorAppointmentBinding.inflate(layoutInflater,container,false)
       binding.next.setOnClickListener {
           val patientName = binding.PetientName.text.toString().trim()
           val patientNo = binding.PetientContactNo.text.toString().trim()
           if(patientNo.isNotBlank() && patientName.isNotBlank()){
              val detail = PatientNameAndContact(patientName,patientNo)
               viewModel.saveData(detail)
           }
       }
        viewModel.featuredBookingData.observe(viewLifecycleOwner){result->
            if (result) {
                Toast.makeText(requireContext(), "Please Choose The Appointment Date and Time", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Failed to Book Doctor", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}