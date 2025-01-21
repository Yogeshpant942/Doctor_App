package com.example.doctorapp.payment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.doctorapp.Model.paymentDone
import com.example.doctorapp.databinding.FragmentPaymentViaUpiBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PaymentViaUpiFragment : Fragment() {
    val amount = arguments?.getString("price")
    val name  = arguments?.getString("doctor Name")
    private lateinit var binding: FragmentPaymentViaUpiBinding
    val database:FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var upiPaymentLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentViaUpiBinding.inflate(layoutInflater, container, false)

        // Retrieve UPI ID from arguments
        binding.textviewUpiId.text = arguments?.getString("Upi_id")


        // Initialize UPI Payment launcher
        upiPaymentLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            handleUpiActivityResult(result.resultCode, result.data)
        }

        // Handle payment button click
        binding.compeleteButton.setOnClickListener {
            if (amount != null) {
                initiateUpiPayment(
                    payeeAddress = "yogeshpant409@okicici",
                    payeeName = "YOGESH PANT",
                    transactionId = "T1234567890",
                    transactionRefId = "ORDER12345",
                    description = "Purchase Payment",
                    amount = amount
                )
            }
        }

        return binding.root
    }

    private fun initiateUpiPayment(
        payeeAddress: String,
        payeeName: String,
        transactionId: String,
        transactionRefId: String,
        description: String,
        amount: String
    ) {
        // Build UPI payment URI
        val uri = Uri.Builder()
            .scheme("upi")
            .authority("pay")
            .appendQueryParameter("pa", payeeAddress) // Payee UPI ID
            .appendQueryParameter("pn", payeeName)    // Payee Name
            .appendQueryParameter("tid", transactionId) // Transaction ID
            .appendQueryParameter("tr", transactionRefId) // Transaction Reference ID
            .appendQueryParameter("tn", description)  // Transaction Note
            .appendQueryParameter("am", amount)      // Amount
            .appendQueryParameter("cu", "INR")       // Currency
            .build()

        // Create UPI Intent
        val upiIntent = Intent(Intent.ACTION_VIEW).apply {
            data = uri
        }

        // Launch the UPI Intent
        upiPaymentLauncher.launch(upiIntent)
    }

    private fun handleUpiActivityResult(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && data != null) {
            val response = data.getStringExtra("response")
            if (!response.isNullOrEmpty()) {
                handleUpiPaymentResponse(response)
            } else {
                Toast.makeText(requireContext(), "Payment canceled by the user.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Payment failed or canceled.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleUpiPaymentResponse(response: String) {
        // Parse the UPI response
        val responseMap = response.split("&").associate {
            val (key, value) = it.split("=")
            key to value
        }

        // Handle payment status
        when (responseMap["Status"]?.uppercase()) {
            "SUCCESS" -> {
                val transactionId = responseMap["txnId"]

                Toast.makeText(
                    requireContext(),
                    "Payment Successful! Transaction ID: $transactionId",

                    Toast.LENGTH_LONG
                ).show()
                uploadPaymentTofireBase(transactionId,name,amount)

            }
            "FAILURE" -> {
                Toast.makeText(requireContext(), "Payment Failed", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(requireContext(), "Payment Canceled", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun uploadPaymentTofireBase(transactionId: String?, name: String?, amount: String?) {
           val ref:DatabaseReference = database.getReference("payment_done")
           val newItemKey = ref.push().key
        if(newItemKey!= null){
            if (transactionId.isNullOrEmpty() || name.isNullOrEmpty() || amount == null ) {
                return
            }

            val data = paymentDone(
                name = name,
                transcationId = transactionId,
                amount = amount
            )
            ref.child(newItemKey).setValue(data)
        }
    }
}
