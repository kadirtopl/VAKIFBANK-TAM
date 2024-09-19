package com.example.shopingapp.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopingapp.data.model.OrderModel
import com.example.shopingapp.databinding.ViewholderOrderBinding
import com.example.shopingapp.presentation.activity.OrderDetailActivity

class OrderAdapter(private val orders: List<OrderModel>) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewholderOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]

        with(holder.binding) {
            orderDetailsTxt.text = order.items.joinToString(", ") { it.title }
            totalAmountTxt.text = "Toplam: TL${order.totalAmount}"
            orderDateTxt.text = order.orderDate

            root.setOnClickListener {
                val intent = Intent(holder.itemView.context, OrderDetailActivity::class.java).apply {
                    putExtra("order", order)
                }
                ContextCompat.startActivity(holder.itemView.context, intent, null)
            }
        }
    }

    override fun getItemCount(): Int = orders.size
}
