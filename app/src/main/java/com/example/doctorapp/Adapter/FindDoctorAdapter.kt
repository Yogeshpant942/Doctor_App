package com.example.doctorapp.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.example.doctorapp.Model.FindDoctor
import com.example.doctorapp.databinding.BookDoctorBinding

class FindDoctorAdapter(
    private val context : Context,
    private val list :ArrayList<FindDoctor>,
    private val onLikeClick:(FindDoctor)->Unit,
    private val onBookClick:(FindDoctor)->Unit
):RecyclerView.Adapter<FindDoctorAdapter.AddItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = BookDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class AddItemViewHolder(private val binding: BookDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val docItem: FindDoctor = list[position]
                name.text = docItem.name
                work.text = docItem.work
                nextAvailableTime.text = docItem.nextTime.toString()
                experience.text = docItem.experience.toString()
                val uriString = docItem.image
                if (uriString != null) {
                    val uri = Uri.parse(uriString)
                    Glide.with(context).load(uri).into(image)
                    heartButton.setOnClickListener {
                        onLikeClick(docItem)
                        if (docItem.liked == true) android.graphics.Color.RED
                        else android.graphics.Color.GRAY

                    }
                    bookNowButton.setOnClickListener {
                        onBookClick(docItem)
                    }

                }
            }
        }


    }
    fun updateData(newDoctorList: ArrayList<FindDoctor>) {
        list.clear()
        list.addAll(newDoctorList)
        notifyDataSetChanged()
    }
}
