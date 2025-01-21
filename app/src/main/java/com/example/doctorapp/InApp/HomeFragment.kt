package com.example.doctorapp.InApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.Adapter.FeaturedDoctorAdapter
import com.example.doctorapp.Adapter.PopularDoctorAdapter
import com.example.doctorapp.Model.FeaturedDoctor
import com.example.doctorapp.Model.PopularDoctor
import com.example.doctorapp.R
import com.example.doctorapp.Repositary
import com.example.doctorapp.ViewModel.HomeViewModel
import com.example.doctorapp.ViewModelFactory.HomeFactory
import com.example.doctorapp.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
   lateinit var binding :FragmentHomeBinding
   var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var PopularDoctorList :ArrayList<PopularDoctor>
    lateinit var FeaturedDoctorList :ArrayList<FeaturedDoctor>
    lateinit var viewModel:HomeViewModel
    lateinit var popularAdapter:PopularDoctorAdapter
    lateinit var featuredAdapter:FeaturedDoctorAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val repositary = Repositary(requireContext())
        val factory = HomeFactory(repositary)
        viewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        PopularDoctorList = ArrayList()
        FeaturedDoctorList = ArrayList()
        val doctor = arguments?.getString("doctor_name").toString()

        viewModel.getPopularDoc(doctor)
        viewModel.popDoc.observe(viewLifecycleOwner, Observer {doctors->
            popularAdapter.updateData(PopularDoctorList)
            PopularDoctorList.clear()
            PopularDoctorList.addAll(doctors)
            popularAdapter.sortByrating(PopularDoctorList)
            popularAdapter.notifyDataSetChanged()
        })
        popularAdapter = PopularDoctorAdapter(requireContext(),PopularDoctorList, onListClick = {doctors->
            sendPopularData(doctors)
        })
        binding.RVpopularDoctor.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.RVpopularDoctor.adapter = popularAdapter


        viewModel.getFeaturedDoc()
        viewModel.featureDoc.observe(viewLifecycleOwner, Observer {doctors->
            featuredAdapter.updateData(FeaturedDoctorList)
            FeaturedDoctorList.clear()
            FeaturedDoctorList.addAll(doctors)
            featuredAdapter.notifyDataSetChanged()
        })

     featuredAdapter =   FeaturedDoctorAdapter(requireContext(),FeaturedDoctorList, onClick = {doctor ->
            sendData(doctor)
        })
        binding.RvFeaturedDoctor.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.RvFeaturedDoctor.adapter = featuredAdapter
        
        binding.bottomNavigationBar.setOnItemSelectedListener {item->
            when(item.itemId) {
                R.id.HomeButton -> {
                    findNavController().navigate(R.id.action_homeFragment_self)
                    true
                }
                R.id.FavouriteDoctor -> {
                    findNavController().navigate(R.id.action_homeFragment_to_favouriteDoctorFragment)
                    true
                }
                R.id.appoinment -> {
                    findNavController().navigate(R.id.action_homeFragment_to_futureDoctorAppoinementFragment)
                    true
                }
                R.id.chat -> {
                    findNavController().navigate(R.id.action_homeFragment_to_findDoctorFragment)
                    true
                }
                else -> false
            }
        }
        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_findDoctorFragment)
        }
        binding.seeAllPopular.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_popularDoctorFragment)
        }
        binding.seeAllFeatured.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_featureDoctorBookingFragment)
        }

        return binding.root
    }
    private fun sendData(doctor: FeaturedDoctor) {
       val bundle = Bundle().apply {
        putString("name",doctor.name)
        putString("work",doctor.work)
        putString("price",doctor.price)
        putString("image",doctor.image)
        doctor.liked?.let { putBoolean("liked", it) }
       }
           findNavController().navigate(R.id.action_homeFragment_to_featureDoctorBookingFragment,bundle)
    }
    private fun sendPopularData(doctor: PopularDoctor) {
        val bundle = Bundle().apply {

        putString("name",doctor.Name)
        putString("work",doctor.post)
        doctor.like?.let { putBoolean("liked", it)
        putString("image",doctor.image)
        }
        }
        findNavController().navigate(R.id.action_homeFragment_to_featureDoctorBookingFragment,bundle)
    }
}