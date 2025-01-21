package com.example.doctorapp.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doctorapp.Model.FavouiteDoctor
import com.example.doctorapp.Model.FindDoctor
import com.example.doctorapp.databinding.FavouriteDoctorBinding

class FavouriteDoctorAdapter(
    private val context: Context,
    private val list:ArrayList<FavouiteDoctor>,
   private val onHeartClicked:(FindDoctor)->Unit,

):RecyclerView.Adapter<FavouriteDoctorAdapter.AddItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = FavouriteDoctorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    inner  class AddItemViewHolder(private val binding: FavouriteDoctorBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.apply {
                val docItem :FavouiteDoctor = list[position]
                 name.text = docItem.name
                 work.text = docItem.work
                val uriString:String = docItem.image.toString()
                val uri :Uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(image)
                heartButton.setOnClickListener {
                    onHeartClicked
                }
            }
        }
    }
    fun updateData(newDoctorList: List<FavouiteDoctor>) {
        list.clear() // Clear the current list
        list.addAll(newDoctorList) // Add the new filtered list
        notifyDataSetChanged() // Notify the adapter to refresh the RecyclerView
    }
}