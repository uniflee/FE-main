package com.android.myapplication.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.databinding.InfoItemBinding
import com.android.myapplication.dto.ItemDescription
import com.bumptech.glide.Glide

class InfoAdapter(val items: MutableList<ItemDescription>) : RecyclerView.Adapter<InfoAdapter.MyViewHolder>(){

    inner class MyViewHolder(private val binding:InfoItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ItemDescription){
            val finalImageUrl = "https://uniflee.alpha.cs.kookmin.ac.kr/uniflee-simple-storage-service/" + item.imageUrl
            Glide.with(binding.root).load(finalImageUrl).into(binding.rvInfoImg)
            binding.rvInfoText.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = InfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}