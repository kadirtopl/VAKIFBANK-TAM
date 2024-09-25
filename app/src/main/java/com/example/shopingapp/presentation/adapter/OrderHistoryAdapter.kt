package com.example.shopingapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopingapp.R
import com.example.shopingapp.data.model.OrderModel

class OrderHistoryAdapter(private val orderList: List<OrderModel>) :
    RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderDetailsTxt: TextView = view.findViewById(R.id.orderDetailsTxt)
        val totalAmountTxt: TextView = view.findViewById(R.id.totalAmountTxt)
        val orderDateTxt: TextView = view.findViewById(R.id.orderDateTxt)
        val pic: ImageView = view.findViewById(R.id.pic) // Görseli ekle
        val titleTxt: TextView = view.findViewById(R.id.tittleTxt) // Başlık
        val ratingTxt: TextView = view.findViewById(R.id.ratingTxt) // Rating
        val priceTxt: TextView = view.findViewById(R.id.priceTxt) // Fiyat
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_item_layout, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.orderDetailsTxt.text = "Sipariş: ${order.items.joinToString(", ") { it.title }}"

        holder.totalAmountTxt.text = "Toplam: TL${order.totalAmount}"
        holder.orderDateTxt.text = "Tarih: ${order.orderDate}"

        // Görsel ve diğer alanlar için verileri doldur
        holder.titleTxt.text = "Başlık" // Başlık verisi
        holder.priceTxt.text = "TL${order.totalAmount}" // Fiyat
        holder.ratingTxt.text = "5" // Örnek rating
        // Görseli yükle (örneğin bir placeholder kullan)
        holder.pic.setImageResource(R.drawable.cash) // Placeholder ekleyebilirsin
    }

    override fun getItemCount(): Int = orderList.size
}
