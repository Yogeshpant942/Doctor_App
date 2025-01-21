package com.example.doctorapp.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doctorapp.Model.PopularDoctor
import com.example.doctorapp.databinding.PopularDoctorBinding

class PopularDoctorAdapter(
    private val context: Context,
    private val itemList:ArrayList<PopularDoctor>,

    private val onListClick:(PopularDoctor)->Unit
):RecyclerView.Adapter<PopularDoctorAdapter.AddItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = PopularDoctorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }
    fun sortByrating(list: ArrayList<PopularDoctor>) {
        itemList.sortByDescending {
            it.rating.toDoubleOrNull()?:0.0
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }
    inner class AddItemViewHolder(private val binding: PopularDoctorBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.apply {
                val item : PopularDoctor = itemList[position]
                 name.text = item.Name
                 work.text = item.post
                 val uriString :String = item.image.toString()
                val uri:Uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(image)
                root.setOnClickListener {
                    onListClick
                }

            }
        }

    }
    fun updateData(newDoctorList:List<PopularDoctor>){
        itemList.clear()
        itemList.addAll(newDoctorList)
        notifyDataSetChanged()
    }
}