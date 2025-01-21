package com.example.doctorapp.InApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.doctorapp.Adapter.FavouriteDoctorAdapter
import com.example.doctorapp.Model.FavouiteDoctor
import com.example.doctorapp.ViewModel.FavouriteDoctorModel
import com.example.doctorapp.databinding.FragmentFavouriteDoctorBinding
import com.google.firebase.database.FirebaseDatabase

class FavouriteDoctorFragment : Fragment() {
    lateinit var binding :FragmentFavouriteDoctorBinding
    var database:FirebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var list:ArrayList<FavouiteDoctor>
    lateinit var adapter : FavouriteDoctorAdapter
    private lateinit var  viewModel :FavouriteDoctorModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =  ViewModelProvider(this)[FavouriteDoctorModel::class.java]
        binding = FragmentFavouriteDoctorBinding.inflate(layoutInflater,container,false)

        adapter = FavouriteDoctorAdapter(requireContext(),list
        ) { doctor ->
            viewModel.removeFavDoc(doctor.name ?: "", doctor.work ?: "")
        }
        binding.RvFeaturedDoctor.layoutManager = GridLayoutManager(requireContext(),2)
        binding.RvFeaturedDoctor.adapter = adapter

        viewModel.favDoc.observe(viewLifecycleOwner){doctor->
            adapter.updateData(doctor)
        }
        viewModel.removeDoc.observe(viewLifecycleOwner){result->
            if(result){
                Toast.makeText(requireContext(), "Removed from Favourite Doctor", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.fetchFavData()
      return binding.root
    }
}