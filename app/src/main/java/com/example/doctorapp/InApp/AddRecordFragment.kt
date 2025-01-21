package com.example.doctorapp.InApp

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.doctorapp.Model.recordClass
import com.example.doctorapp.ViewModel.AddRecordViewModel
import com.example.doctorapp.databinding.FragmentAddRecordBinding
import com.google.android.gms.tasks.Tasks
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
class AddRecordFragment : Fragment() {
    lateinit var binding: FragmentAddRecordBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var image: Uri? = null
    private var name: String? = null
    private var date: String? = null
    private var file: Uri? = null
    lateinit var viewModel: AddRecordViewModel
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddRecordBinding.inflate(layoutInflater, container, false)
        // Set click listener for the upload button
        binding.btnUploadRecord.setOnClickListener {
            name = binding.tvRecordForValue.text.toString().trim()
            date = binding.tvRecordCreatedValue.text.toString().trim()

            if (name!!.isNotBlank() && date!!.isNotBlank()) {

                viewModel.uploadData(recordClass())
            } else {
                Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.record.observe(viewLifecycleOwner, Observer{isSuccess->
            if(isSuccess){
                Toast.makeText(requireContext(),"data upload",Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(requireContext(), "Failed to upload record.", Toast.LENGTH_SHORT).show()
            }

        })


        binding.addMoreFIleOrimage.setOnClickListener {

        val bottomSheet = AddRecordBottomSheetFragment()
            bottomSheet.setOnOptionSelectedListener{uri,source->
                when(source){
                    "gallery"->{
                        image = uri
                        binding.imgProfile.setImageURI(image)
                        Toast.makeText(requireContext(), "Image selected from gallery", Toast.LENGTH_SHORT).show()

                    }
                    "camera"->{
                        image = uri
                        binding.imgProfile.setImageURI(uri)
                        Toast.makeText(requireContext(), "Image captured from camera", Toast.LENGTH_SHORT).show()
                    }
                    "file"->{
                        file = uri
                        Toast.makeText(requireContext(), "File selected", Toast.LENGTH_SHORT).show()

                    }
                }

            }
        bottomSheet.show(parentFragmentManager,bottomSheet.tag)
        }
        return binding.root
    }

}
