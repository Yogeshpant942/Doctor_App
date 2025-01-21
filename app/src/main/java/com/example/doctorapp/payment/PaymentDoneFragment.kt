package com.example.doctorapp.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.Adapter.PaymentAdapter
import com.example.doctorapp.Model.paymentDone
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentPaymentDoneBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PaymentDoneFragment : Fragment() {
    lateinit var binding:FragmentPaymentDoneBinding
    lateinit var list:ArrayList<paymentDone>
    val database:FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentDoneBinding.inflate(layoutInflater,container,false)
        fetchDataFromFirebase()
        return binding.root
    }

    private fun fetchDataFromFirebase() {
        val ref:DatabaseReference = database.reference.child("payment")
        ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(dataSnapshot in snapshot.children){
                    val paymentRef:paymentDone? = dataSnapshot.getValue(paymentDone::class.java)
                    paymentRef?.let {
                        list.add(paymentRef)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setAdapter() {
        val adapter = PaymentAdapter(requireContext(),list)
        binding.RvPayment.layoutManager = LinearLayoutManager(requireContext())
        binding.RvPayment.adapter = adapter
    }


}