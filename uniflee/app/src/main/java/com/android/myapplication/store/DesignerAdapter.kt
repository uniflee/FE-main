package com.android.myapplication.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.databinding.CartItemBinding
import com.android.myapplication.databinding.DesignerItemBinding
import com.android.myapplication.databinding.ProductItemBinding

class DesignerAdapter(val items: List<DesignerContents>) : RecyclerView.Adapter<DesignerAdapter.MyViewHolder>(){

    inner class MyViewHolder(private val binding: DesignerItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: DesignerContents) {
            binding.rvDesignerImage.setImageResource(item.Image)
            binding.rvRank.text = item.Rank.toString()
            binding.rvDesignerName.text = item.DesignerName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DesignerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}