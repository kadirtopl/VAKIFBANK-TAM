package com.example.shopingapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopingapp.data.model.ItemsModel
import com.example.shopingapp.databinding.ItemOrderBinding

class OrderAdapter(private val orderList: List<ItemsModel>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.binding.titleTxt.text = order.title
        holder.binding.priceTxt.text = "TL${order.price}"
        holder.binding.ratingTxt.text = "${order.rating} Yıldız"

        // Resmi yükle
        if (order.picUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(order.picUrl[0]) // İlk resmi yükle
                .into(holder.binding.pic)
        }
    }

    override fun getItemCount(): Int = orderList.size
}
