package com.android.myapplication.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.databinding.ProductItemBinding

class ProductAdapter(val items: List<ProductContents>) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>(){

    inner class MyViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ProductContents) {
            binding.rvImage.setImageResource(item.ProductImage)
            binding.rvStore.text = item.StoreName
            binding.rvProduct.text = item.ProductName
            binding.rvPrice.text = item.ProductPrice.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}