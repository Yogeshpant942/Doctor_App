package com.example.doctorapp.InApp

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.doctorapp.R
import com.example.doctorapp.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File

class AddRecordBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetLayoutBinding? = null
    private val binding get() = _binding!!

    var onOptionSelected: ((Uri?, String) -> Unit)? = null

    // Camera launcher
    val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { uri ->
//        uri?.let {
//            onOptionSelected?.invoke(uri, "camera")
//        }
//        dismiss() // Dismiss the bottom sheet after selection
    }

    // Gallery launcher
    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            onOptionSelected?.invoke(uri, "gallery")
        }
        dismiss() // Dismiss the bottom sheet after selection
    }

    // File picker launcher
    val fileLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            onOptionSelected?.invoke(uri, "file")
        }
    }

    fun setOnOptionSelectedListener(callback: (Uri?, String) -> Unit) {
        onOptionSelected = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetLayoutBinding.inflate(inflater, container, false)

        // Handling photo capture from camera
        binding.takePhotoOption.setOnClickListener {
            val photoUri = createImageUri()
            cameraLauncher.launch(photoUri)
        }

        // Handling file selection from gallery
        binding.uploadFromGalleryOption.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        // Handling file selection
        binding.uploadFilesOption.setOnClickListener {
            fileLauncher.launch("*/*")
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }

    // Function to create Uri for image storage
    private fun createImageUri(): Uri {
        val contentResolver = requireContext().contentResolver
        val imageFile = File(requireContext().externalCacheDir, "photo_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            imageFile
        )
    }
}
