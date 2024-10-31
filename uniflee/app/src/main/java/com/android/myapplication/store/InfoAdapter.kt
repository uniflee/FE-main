package com.android.myapplication.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.databinding.InfoItemBinding

class InfoAdapter(val items : List<InfoContents>) : RecyclerView.Adapter<InfoAdapter.MyViewHolder>(){

    inner class MyViewHolder(private val binding:InfoItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: InfoContents){
            binding.rvInfoImg.setImageResource(item.InfoImg)
            binding.rvInfoText.text = item.InfoText
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