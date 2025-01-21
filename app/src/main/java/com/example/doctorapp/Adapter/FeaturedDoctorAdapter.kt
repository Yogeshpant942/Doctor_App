package com.example.doctorapp.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doctorapp.Model.FeaturedDoctor
import com.example.doctorapp.databinding.FeaturedDoctorBinding
import com.google.firebase.database.FirebaseDatabase

class FeaturedDoctorAdapter(
    private val context: Context,
    private val list:ArrayList<FeaturedDoctor>,
    private val onClick:(FeaturedDoctor)->Unit
):RecyclerView.Adapter<FeaturedDoctorAdapter.AddItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = FeaturedDoctorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

   inner class AddItemViewHolder(val binding: FeaturedDoctorBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.apply {
                val item : FeaturedDoctor = list[position]
                 name.text = item.name
                 rating.text = item.rating
                 price.text = item.price
                val uriString:String? = item.image
                val uri:Uri = Uri.parse(uriString)
                Glide.with(context).load(uriString).into(binding.image)

                root.setOnClickListener {
                    onClick
                }


            }
        }
    }

    fun updateData(newDoctor:List<FeaturedDoctor>){
        list.clear()
        list.addAll(newDoctor)
        notifyDataSetChanged()
    }
}