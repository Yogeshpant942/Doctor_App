package com.example.doctorapp.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doctorapp.Model.AllDoctor
import com.example.doctorapp.databinding.AllDoctorLayoutBinding

class AlldoctorAdapter(
    private val context:Context,
    private val list: MutableList<AllDoctor>
):RecyclerView.Adapter<AlldoctorAdapter.AddItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = AllDoctorLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }
    override fun getItemCount(): Int {
      return  list.size
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }
    inner class AddItemViewHolder(private val binding:AllDoctorLayoutBinding):RecyclerView.ViewHolder(binding.root) {
           fun bind(position: Int){
              binding.apply {
                  val doc:AllDoctor = list[position]
                   name.text = doc.name
                  work.text = doc.work
                  rating.text = doc.rating
                  var uriStrin:String = doc.image.toString()
                   val uri :Uri = Uri.parse(uriStrin)
                  Glide.with(context).load(uri).into(imageView)
              }
          }
    }

}