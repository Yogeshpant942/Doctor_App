package com.example.doctorapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorapp.Model.paymentDone
import com.example.doctorapp.databinding.PaymentLayoutBinding

class PaymentAdapter(
    private val context:Context,
    private val list:ArrayList<paymentDone>
):RecyclerView.Adapter<PaymentAdapter.AddViewItemHolder>() {
  inner  class AddViewItemHolder(private val binding :PaymentLayoutBinding):RecyclerView.ViewHolder(binding.root) {
     fun bind(position: Int){
         binding.apply {
             val data:paymentDone = list[position]
             doctorName.text = data.name
             transcationId.text = data.transcationId
             price.text = data.amount

         }
     }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddViewItemHolder {
        val binding = PaymentLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddViewItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AddViewItemHolder, position: Int) {
        holder.bind(position)
    }
}