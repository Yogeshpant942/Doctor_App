package com.example.doctorapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorapp.Model.recordClass
import com.example.doctorapp.databinding.MedicalRecordBinding

class MedicalRecordAdapter(
    private val context:Context,
    private val list:ArrayList<recordClass>
):RecyclerView.Adapter<MedicalRecordAdapter.AddItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = MedicalRecordBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return list.size
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
     holder.bind(position)
    }

    inner class AddItemViewHolder(private val binding:MedicalRecordBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.apply {
                val recordItem :recordClass = list[position]
                recordSubtitle.text = recordItem.name
                recordInfo.text = recordItem.fileType.toString()
                dateDay.text = recordItem.date
            }
        }
    }
}