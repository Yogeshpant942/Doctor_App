package com.example.doctorapp

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.doctorapp.Model.AllDoctor
import com.example.doctorapp.Model.FavouiteDoctor
import com.example.doctorapp.Model.FeaturedDoctor
import com.example.doctorapp.Model.FindDoctor
import com.example.doctorapp.Model.PatientNameAndContact
import com.example.doctorapp.Model.PopularDoctor
import com.example.doctorapp.Model.recordClass
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

class Repositary(private val context:Context) {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val favDoc: DatabaseReference = database.reference.child("Liked_doctor")
    val patientRef :DatabaseReference = database.getReference("PatientDataFeaturDoctor")
    val patientRecord:DatabaseReference = database.reference.child("Patient_record")
    val Likedref:DatabaseReference = database.getReference("Liked_doctor")
    val allDoctor:DatabaseReference = database.reference.child("All_doctor")
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val storageRef = FirebaseStorage.getInstance()

    suspend fun getFavouriteDoctor(): List<FavouiteDoctor> {
        val doctors = mutableListOf<FavouiteDoctor>()
        return try {
            val snapshot = favDoc.get().await()
            for (data in snapshot.children) {
                val doctor = data.getValue(FavouiteDoctor::class.java)
                if (doctor != null) {
                    doctors.add(doctor)

                }
            }
            doctors
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun removeFavDoc(name: String, work: String): Boolean {
        return try {
            val snapshot = favDoc.orderByChild("name").equalTo(name).get().await()
            for (data in snapshot.children) {
                val doctor = data.getValue(FavouiteDoctor::class.java)
                if (doctor?.work == work) {
                    data.ref.removeValue().await()
                    return true
                }
            }
            false
        } catch (e: Exception) {
            false
        }
    }
    @SuppressLint("SuspiciousIndentation")
    suspend fun uploadPatientDetailForFeaturedDoctor(PatientDetail:PatientNameAndContact):Boolean {
        return try {
        val newItemKey = patientRef.push().key
            if (newItemKey != null) {
                patientRef.child(newItemKey).setValue(PatientDetail).await()
            }
            true
        } catch (e: Exception) {
            false
        }
    }
    suspend fun uploadPatientRecord(record:recordClass):Boolean{
//        return try{
//            if(record.image == null ||record.file == null){
//                throw IllegalArgumentException("Please select both an image and a file!")
//            }
//            val newItemKey = patientRecord.push().key
//
//            val imgRef:StorageReference = storageRef.reference.child("patient_image/${newItemKey}.jpg")
//            val fileRef:StorageReference = storageRef.reference.child("patient_file/$newItemKey.pdf")
//
//            imgRef.putFile(record.image!!).await()
//            fileRef.putFile(record.file!!).await()
//            val imgUrl = imgRef.downloadUrl.await().toString()
//            val fileUrl = fileRef.downloadUrl.await().toString()
//
//            newItemKey?.let {
//                patientRecord.child(newItemKey).setValue(record).await()
//            }
//            true
//        }catch (e:Exception){
//            false
//        }
        return true
    }


    suspend fun uploadLikedDoctor(doctorName:String,doctorWork:String):Boolean{
        return try {
            val newItemKey:String = Likedref.push().key.toString()
            val a = hashMapOf(
                "doctor" to doctorName,
                "work" to doctorWork
            )
            newItemKey.let {
                Likedref.child(newItemKey).setValue(a).await()
            }
            true
        }catch (e:Exception){
            false
        }
    }
    @SuppressLint("SuspiciousIndentation")
    suspend fun retrieveAllData(category: String):List<AllDoctor>{
        val doctor = mutableListOf<AllDoctor>()
        return try{
            val snapshot = allDoctor.get().await()
            for(data in snapshot.children){
                val docRef: AllDoctor? = data.getValue(AllDoctor::class.java)

                    if(docRef?.work == category){
                        doctor.add(docRef)
                    }
            }
            doctor
        }catch (e:Exception){
            emptyList()
        }
    }
    suspend fun retrieveAllDataDoctor():List<FindDoctor>{
        val doctor = mutableListOf<FindDoctor>()
        return try{
            val snapshot = allDoctor.get().await()
            for(data in snapshot.children){
                val docRef: FindDoctor? = data.getValue(FindDoctor::class.java)
                if(docRef!= null){
                    doctor.add(docRef)
                    Log.d("DoctorData", "Data snapshot: ${snapshot.value}")
                }
            }
            doctor
        }catch (e:Exception){
            Log.e("DoctorData", "Error retrieving data: ${e.message}", e)
            emptyList()
        }
    }
    suspend fun popularDoctor(doctor: String):List<PopularDoctor>{
        val popularDoc:DatabaseReference = database.reference.child("AverageRating").child(doctor)

        val doctors = mutableListOf<PopularDoctor>()
        return try{
            val snapshot = popularDoc.get().await()
            for(data in snapshot.children){
                val docRef:PopularDoctor? = data.getValue(PopularDoctor::class.java)
                docRef?.let {
                    if(docRef.rating.toDouble()>3.5){
                    doctors.add(docRef)}
                }
            }
            doctors
        }catch (e:Exception){
            emptyList()
        }
    }
    suspend fun retrievePatientRecord():List<recordClass>{
        val doctor = mutableListOf<recordClass>()
        return try{
            val snapshot = patientRecord.get().await()
            for(data in snapshot.children) {
                val docRef: recordClass? = data.getValue(recordClass::class.java)
                docRef?.let {
                    doctor.add(docRef)
                }
            }
                doctor
        }catch (e:Exception){
            emptyList()
        }
    }
    @SuppressLint("MissingPermission")
    suspend fun updateLocation(): Boolean {
        return try {
            val location = fusedLocationClient.lastLocation.await()

            if (location != null) {
                val longitude = location.longitude
                val latitude = location.latitude

             val userId = auth.currentUser?.uid ?: return false
                val userLocation = mapOf(
                    "longitude" to longitude,
                    "latitude" to latitude
                )
                firestore.collection("Users")
                    .document(userId)
                    .update("Location", userLocation)
                    .await()
                true
            } else {
                false 
            }
        } catch (e: Exception) {

            false
        }
    }

    suspend fun getFeaturedDoc():List<FeaturedDoctor>{
        val doctor = mutableListOf<FeaturedDoctor>()
        return try{
            val snapshot = allDoctor.get().await()
            for(data in snapshot.children){
                val docRef:FeaturedDoctor? = data.getValue(FeaturedDoctor::class.java)
                docRef?.let {
                    doctor.add(docRef)
                }
            }
            doctor
        } catch (e:Exception){
            emptyList()
        }
    }


}