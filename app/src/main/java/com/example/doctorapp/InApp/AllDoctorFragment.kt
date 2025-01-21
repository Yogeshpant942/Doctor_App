package com.example.doctorapp.InApp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.Adapter.AlldoctorAdapter
import com.example.doctorapp.Model.AllDoctor
import com.example.doctorapp.ViewModel.AllDoctorViewModel
import com.example.doctorapp.databinding.FragmentAllDoctorBinding
import com.google.firebase.database.FirebaseDatabase



class AllDoctorFragment : Fragment() {
    lateinit var binding: FragmentAllDoctorBinding
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var list: ArrayList<AllDoctor> = ArrayList()
    lateinit var viewModel: AllDoctorViewModel
    lateinit var adapter: AlldoctorAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllDoctorBinding.inflate(layoutInflater, container, false)
        val category = arguments?.getString("Category")

        if (category != null) {
        viewModel.getData(category)
        }
        viewModel.allDoc.observe(viewLifecycleOwner, Observer { result->
            list.clear()
            list.addAll(result)
            adapter.notifyDataSetChanged()
        })
        return binding.root
    }

    private fun setAdapter() {
        val adapter = AlldoctorAdapter(requireContext(), list)
        binding.rvDoctor.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDoctor.adapter = adapter
    }
}
