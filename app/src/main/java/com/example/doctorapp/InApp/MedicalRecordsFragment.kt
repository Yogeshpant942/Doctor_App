package com.example.doctorapp.InApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.Adapter.MedicalRecordAdapter
import com.example.doctorapp.Model.recordClass
import com.example.doctorapp.R
import com.example.doctorapp.ViewModel.AddRecordViewModel
import com.example.doctorapp.databinding.FragmentMedicalRecordsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MedicalRecordsFragment : Fragment() {
    lateinit var binding:FragmentMedicalRecordsBinding
    var database:FirebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var list:ArrayList<recordClass>
lateinit var viewModel:AddRecordViewModel
lateinit var adapeter:MedicalRecordAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicalRecordsBinding.inflate(layoutInflater,container,false)

       viewModel.getData()
       viewModel.getRecord.observe(viewLifecycleOwner, Observer {record->
           if(record.isNotEmpty()){
               list.clear()
               list.addAll(record)
               adapeter.notifyDataSetChanged()
           }

       })

        setAdapter()
        return binding.root
    }


    private fun setAdapter() {
      var adapter = MedicalRecordAdapter(requireContext(),list)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }
}