package com.android.myapplication.store

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.databinding.ProductItemBinding
import com.android.myapplication.dto.ItemResponseDto
import com.bumptech.glide.Glide

class ProductAdapter(val items: MutableList<ItemResponseDto>) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>(){

    inner class MyViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ItemResponseDto) {
            val finalImageUrl = "http://localhost:9000/unique-storage-service" + item.featuredImageUrl
            Glide.with(binding.rvImage.context).load(finalImageUrl).into(binding.rvImage)
            binding.rvStore.text = item.designerName
            binding.rvProduct.text = item.name
            binding.rvPrice.text = item.price.toString()

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, StoreProductDetailsActivity::class.java)
                intent.putExtra("ITEM_ID", item.id)
                context.startActivity(intent)
            }
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

    fun updateItems(newItems: MutableList<ItemResponseDto>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

}