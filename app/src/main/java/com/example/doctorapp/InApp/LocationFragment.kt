package com.example.doctorapp.InApp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.doctorapp.databinding.FragmentLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
class LocationFragment : Fragment() {
    private lateinit var binding: FragmentLocationBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<String>
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View {
        binding = FragmentLocationBinding.inflate(layoutInflater, container, false)
        // Initialize fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted->
            if(isGranted){
                saveLocationToFirebase()
            }
            else{
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
        // Handle button click
        binding.btnAddRecord.setOnClickListener {
            checkAndRequestPermission()
        }
        return binding.root
    }
    private fun checkAndRequestPermission() {
       val permissionStatus = ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)

        if(permissionStatus == PackageManager.PERMISSION_GRANTED){
            saveLocationToFirebase()
        }
        else{
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    @SuppressLint("MissingPermission")
    private fun saveLocationToFirebase() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    // Retrieve the current user's UID
                    val userId = auth.currentUser?.uid ?: return@addOnSuccessListener

                    val userLocation = mapOf(
                        "latitude" to latitude,
                        "longitude" to longitude
                    )

                    // Save location to Firestore
                    firestore.collection("Users").document(userId).update("Location", userLocation)
                        .addOnSuccessListener {
                            Toast.makeText(
                                requireContext(),
                                "Location is saved ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                requireContext(),
                                "Failed to save location ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    Toast.makeText(requireContext(), "Unable to get location", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to get location", Toast.LENGTH_SHORT).show()
            }
    }
}
