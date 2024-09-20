package com.example.shopingapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopingapp.data.model.OrderModel
import com.example.shopingapp.databinding.ItemOrderBinding

class OrderAdapter(
    private val orders: List<OrderModel>,
    private val onOrderClick: (OrderModel) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderModel) {
            binding.tittleTxt.text = order.items.joinToString { it.title }
            binding.ratingTxt.text = "5" // Rating bilgisini uygun bir şekilde ayarlayın
            binding.priceTxt.text = "TL${order.totalAmount}"

            // İlk ürünün görselini yükle
            if (order.items.isNotEmpty()) {
                Glide.with(binding.pic.context)
                    .load(order.items[0].picUrl) // İlk ürün görseli
                    .into(binding.pic)
            }

            binding.root.setOnClickListener {
                onOrderClick(order) // Siparişe tıklandığında
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size
}
