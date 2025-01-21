package com.example.doctorapp.InApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.Adapter.PopularDoctorAdapter
import com.example.doctorapp.Model.PopularDoctor
import com.example.doctorapp.R
import com.example.doctorapp.ViewModel.PopularDoctorViewModel
import com.example.doctorapp.databinding.FragmentPopularDoctorBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PopularDoctorFragment : Fragment() {
    val doctor = arguments?.getString("doctor_name").toString()
    lateinit var viewModel:PopularDoctorViewModel

   lateinit var binding:FragmentPopularDoctorBinding
   lateinit var adapter :PopularDoctorAdapter

   val database:FirebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var list:ArrayList<PopularDoctor>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularDoctorBinding.inflate(layoutInflater,container,false)

        viewModel.popDoc(doctor)
        viewModel.popularDoc.observe(viewLifecycleOwner, Observer {doctor->
            list.clear()
            list.addAll(doctor)
           adapter.notifyDataSetChanged()

        })
        setAdapter()

        return binding.root
    }
    private fun setAdapter() {
        val adapteru = PopularDoctorAdapter(requireContext(),list, onListClick = {doctor->
            sendData(doctor)
        })
        adapteru.sortByrating(list)
        binding.RvAllPopularDoctors.layoutManager = LinearLayoutManager(requireContext())
        binding.RvAllPopularDoctors.adapter = adapteru
    }

    private fun sendData(doctor: PopularDoctor) {
        val bundle = Bundle().apply {
            putString("name",doctor.Name)
            putString("work",doctor.post)
            putString("rating",doctor.rating)
            putString("image",doctor.image)
        }
        findNavController().navigate(R.id.action_popularDoctorFragment_to_featureDoctorBookingFragment,bundle)

    }

}