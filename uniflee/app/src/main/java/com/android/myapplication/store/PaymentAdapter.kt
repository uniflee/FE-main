package com.android.myapplication.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.databinding.PaymentItemBinding

class PaymentAdapter(val items: List<PaymentContents>) : RecyclerView.Adapter<PaymentAdapter.MyViewHolder>(){

    inner class MyViewHolder(private val binding: PaymentItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: PaymentContents) {
            binding.rvProductImage.setImageResource(item.ProductImage)
            binding.rvDesignerName.text = item.DesignerName
            binding.rvProductName.text = item.ProductName
            binding.rvProductPrice.text = item.ProductPrice.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PaymentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}