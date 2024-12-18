package com.android.myapplication.my

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.R
import com.android.myapplication.dto.OrderRecycler
import com.bumptech.glide.Glide
import java.text.NumberFormat

class OrderAdapter(private val itemList: List<OrderRecycler>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.date)
        val designer: TextView = itemView.findViewById(R.id.designer_name)
        val name: TextView = itemView.findViewById(R.id.product_name)
        val pointAndCount: TextView = itemView.findViewById(R.id.point_count)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = itemList[position]
        val formattedPointAndCount = "${NumberFormat.getInstance().format(item.point)}포인트 / ${item.count}개"
        holder.date.text = item.date
        holder.designer.text = item.designerName
        holder.name.text = item.name
        holder.pointAndCount.text = formattedPointAndCount

//        holder.image.setImageResource(R.drawable.appicon) // 샘플 이미지
        // Glide로 이미지 로드
        val finalImageUrl = "https://uniflee.alpha.cs.kookmin.ac.kr/uniflee-simple-storage-service/" + item.featuredImageUrl
        Glide.with(holder.itemView.context)
            .load(finalImageUrl) // 이미지 URL
            .placeholder(R.drawable.appicon) // 로딩 중 표시할 이미지
            .into(holder.image)
    }

    override fun getItemCount(): Int = itemList.size
}
