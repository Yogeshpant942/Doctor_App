package com.example.doctorapp.InApp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentRateDoctorBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


class RateDoctorFragment : DialogFragment() {
    lateinit var binding: FragmentRateDoctorBinding
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val count: Int = 0
    private val selectedRating: Int = 0

    // Initialize variables here with default values
    var doctor: String = ""
    var doctorWork: String = ""
    var image: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRateDoctorBinding.inflate(layoutInflater, container, false)

        // Safely access fragment arguments
        doctor = arguments?.getString("doctorName") ?: ""
        doctorWork = arguments?.getString("doctor_work") ?: ""
        image = arguments?.getString("doctor_image") ?: ""

        binding.btnSubmitFeedback.setOnClickListener {
            uploadTestiMonial(doctor) // Correct usage here
            setUpRatingButton()
        }
        return binding.root
    }

    private fun uploadTestiMonial(doctor: String) {
        // Handle uploading of testimonial (currently empty)
    }

    private fun setUpRatingButton() {
        val buttonList = listOf(
            binding.btnRate1,
            binding.btnRate2,
            binding.btnRate3,
            binding.btnRate4,
            binding.btnRate5
        )
        for ((index, button) in buttonList.withIndex()) {
            button.setOnClickListener {
                selectRating(index + 1, buttonList)
                saveRatingToFirebase(doctor, index + 1, doctorWork, image)
            }
        }
    }

    private fun saveRatingToFirebase(doctor: String, rating: Int, doctorWork: String, imageUri: String) {
        val ref: DatabaseReference = database.getReference("Ratings")

        val newItemKey = ref.push().key
        if (newItemKey != null) {
            // Prepare the rating data
            val ratingData = mapOf(
                "doctor" to doctor,
                "rating" to rating
            )

            // Save the rating data to the Ratings node
            ref.child(newItemKey).setValue(ratingData).addOnSuccessListener {
                Log.d("Firebase", "Rating uploaded successfully.")
                // Call function to update average rating
                updateAverageRating(doctor, doctorWork, imageUri)
            }.addOnFailureListener { error ->
                Log.e("FirebaseError", "Failed to upload rating: ${error.message}")
            }
        }
    }

    private fun updateAverageRating(doctor: String, doctorWork: String, imageUri: String) {
        val ratingsRef: DatabaseReference = database.getReference("Ratings")
        val averageRef: DatabaseReference = database.getReference("AverageRatings")

        ratingsRef.orderByChild("doctor").equalTo(doctor).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var totalRating = 0
                var count = 0

                // Loop through all ratings for the doctor
                for (data in snapshot.children) {
                    val rating = data.child("rating").getValue(Int::class.java) ?: 0
                    totalRating += rating
                    count++
                }

                // Calculate the average rating
                if (count > 0) {
                    val averageRating = totalRating / count.toDouble()

                    // Prepare the doctor data with average rating and other details
                    val doctorData = mutableMapOf<String, Any>(
                        "doctor" to doctor,
                        "work" to doctorWork,
                        "averageRating" to averageRating
                    )
                    // If an image URI is provided, upload the image to Firebase Storage
                    imageUri?.let { uri ->
                        val storageRef = FirebaseStorage.getInstance().reference.child("doctor_images/${doctor}.jpg")
                        val uploadTask = storageRef.putFile(Uri.parse(uri))
                        uploadTask.addOnSuccessListener { taskSnapshot ->
                            storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                                doctorData["image"] = downloadUrl.toString()
                                // Save the doctor data to AverageRatings
                                averageRef.child(doctor).setValue(doctorData).addOnSuccessListener {
                                    Log.d("Firebase", "Average rating updated for $doctor")
                                }
                            }
                        }.addOnFailureListener { error ->
                            Log.e("FirebaseError", "Failed to upload image: ${error.message}")
                        }
                    } ?: run {
                        // Save the doctor data without image
                        averageRef.child(doctor).setValue(doctorData).addOnSuccessListener {
                            Log.d("Firebase", "Average rating updated for $doctor")
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Failed to retrieve ratings: ${error.message}")
            }
        })
    }

    private fun selectRating(index: Int, button: List<androidx.appcompat.widget.AppCompatButton>) {
        for (i in button.indices) {
            if (i < index) {
                button[i].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gold))
            } else {
                button[i].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
        }
    }
}
