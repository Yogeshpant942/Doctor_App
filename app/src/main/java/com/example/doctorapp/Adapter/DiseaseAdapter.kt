package com.example.doctorapp.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doctorapp.Model.diseaseButton
import com.example.doctorapp.databinding.DiseaseButtonBinding

class DiseaseAdapter(
    private val context:Context,
    private val imageList:ArrayList<diseaseButton>
):RecyclerView.Adapter<DiseaseAdapter.AddItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        var binding  = DiseaseButtonBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return imageList.size
    }
    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
      holder.bind(position)
    }
   inner class AddItemViewHolder(private val binding :DiseaseButtonBinding):RecyclerView.ViewHolder(binding.root) {
     fun bind(position: Int){
         binding.apply {
             val imageItem: diseaseButton = imageList[position]
             var uri : String? = imageItem.image
             var uriString:Uri = Uri.parse(uri)
             Glide.with(context).load(uriString).into(binding.Imagebutto)
         }
     }
    }
}