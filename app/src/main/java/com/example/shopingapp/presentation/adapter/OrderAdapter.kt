package com.example.shopingapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopingapp.R
import com.example.shopingapp.data.model.OrderModel
import com.example.shopingapp.databinding.ItemOrderBinding

class OrderAdapter(
    private var orders: List<OrderModel>,
    private val onOrderClick: (OrderModel) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private var originalOrders: List<OrderModel> = orders // Orijinal sipariş listesi

    inner class OrderViewHolder(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderModel) {
            binding.orderDateTxt.text = order.orderDate
            binding.tittleTxt.text = order.items.joinToString { it.title }
            binding.totalAmountTxt.text = "Toplam: TL ${order.totalAmount}"

            // Resmi yükle
            val imageUrl = order.items.firstOrNull()?.picUrl?.firstOrNull()
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(binding.root.context)
                    .load(imageUrl)
                    .into(binding.pic)
            } else {
                binding.pic.setImageResource(R.drawable.cash) // Yer tutucu resim
            }

            binding.root.setOnClickListener { onOrderClick(order) }
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

    // Filtreleme fonksiyonu
    fun filter(query: String) {
        orders = if (query.isEmpty()) {
            originalOrders // Eğer sorgu boşsa orijinal listeyi döndür
        } else {
            originalOrders.filter { order ->
                order.items.any { it.title.contains(query, ignoreCase = true) } || // Ürün ismi
                        order.orderDate.contains(query, ignoreCase = true) // Sipariş tarihi
            }
        }
        notifyDataSetChanged() // Listeyi güncelle
    }
}
