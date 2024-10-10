package com.android.myapplication.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.databinding.CartItemBinding

class CartAdapter(val items: List<CartContents>) : RecyclerView.Adapter<CartAdapter.MyViewHolder>(){

    inner class MyViewHolder(private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: CartContents) {
            binding.rvCheckbox.isChecked = item.CheckBool
            binding.rvDesignerName.text = item.DesignerName
            binding.rvProductImage.setImageResource(item.ProductImage)
            binding.rvProductName.text = item.ProductName
            binding.rvProductPrice.text = item.ProductPrice.toString()
            binding.rvCartCount.text = item.ProductCount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}