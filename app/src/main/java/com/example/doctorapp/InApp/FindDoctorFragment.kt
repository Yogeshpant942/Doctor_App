package com.example.doctorapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.Adapter.FindDoctorAdapter
import com.example.doctorapp.InApp.FeatureDoctorBookingFragment
import com.example.doctorapp.Model.FindDoctor
import com.example.doctorapp.R
import com.example.doctorapp.ViewModel.FindDoctorViewModel
import com.example.doctorapp.ViewModelFactory.AllDoctorFactory
import com.example.doctorapp.databinding.FragmentFindDoctorBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class FindDoctorFragment : Fragment() {
    lateinit var binding: FragmentFindDoctorBinding
    private var counter = 0
    val fragment = FeatureDoctorBookingFragment()
    private val list = ArrayList<FindDoctor>()
    private val originalList = ArrayList<FindDoctor>()
    lateinit var viewModel: FindDoctorViewModel
    lateinit var adapter: FindDoctorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFindDoctorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = Repositary(requireContext())
        val factory = AllDoctorFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(FindDoctorViewModel::class.java)

        adapter = FindDoctorAdapter(
            requireContext(),
            originalList,
            onLikeClick = { doctor ->
                val doctorName = doctor.name
                val doctorWork = doctor.work
                counter++
                if (counter % 2 == 0) {
                    doctor.liked = false
                    viewModel.removeDoc(doctorName!!, doctorWork!!)
                } else {
                    doctor.liked = true
                    viewModel.removeDoc(doctorName!!, doctorWork!!)
                }
            },
            onBookClick = { doctor ->
                val bundle = Bundle().apply {
                    putString("name", doctor.name)
                    putString("work", doctor.work)
                    putString("image", doctor.image)
                    putString("experience",doctor.experience)
                }
                findNavController().navigate(
                    R.id.action_findDoctorFragment_to_featureDoctorBookingFragment,
                    bundle
                )
            }
        )
        viewModel.retrieveDoctor()
        viewModel.allDoc.observe(viewLifecycleOwner, Observer { doctors ->
            originalList.clear()
            originalList.addAll(doctors)
            list.clear()
            list.addAll(doctors)
            adapter.updateData(list)
            adapter.notifyDataSetChanged()

        })
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.likedDoctor.observe(viewLifecycleOwner, Observer { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "Added to favourite", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.remLikedDoc.observe(viewLifecycleOwner, Observer { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "removed from favourite", Toast.LENGTH_SHORT)
                    .show()
            }
        })
            setUpSearchView()
    }
    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterDoctorItem(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterDoctorItem(newText)
                return true
            }
        })
    }
    private fun filterDoctorItem(query: String?) {
        if(query == null){
            list.clear()
            list.addAll(originalList)
        }
        else{
        val filteredItem = list.filter { doctor ->
            doctor.work?.contains(query ?: "", ignoreCase = true) == true ||
                    doctor.name?.contains(query ?: "", ignoreCase = true) == true
        }
        if (filteredItem.isNotEmpty()) {
            adapter.updateData(ArrayList(filteredItem))
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(requireContext(), "No doctors found", Toast.LENGTH_LONG).show()
        }
    }}
}
