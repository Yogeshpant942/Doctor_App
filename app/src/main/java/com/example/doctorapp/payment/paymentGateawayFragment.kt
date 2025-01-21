package com.example.doctorapp.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentPaymentGateawayBinding


class paymentGateawayFragment : Fragment() {

    lateinit var binding:FragmentPaymentGateawayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentGateawayBinding.inflate(layoutInflater,container,false)



        val upiId = binding.upiIdInput.text.toString()

        val bundle = Bundle()
        bundle.putString("Upi_id",upiId)
        val recieverFragment = PaymentViaUpiFragment()
        recieverFragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,PaymentViaUpiFragment())
            .addToBackStack(null)
            .commit()

        return binding.root


    }

}