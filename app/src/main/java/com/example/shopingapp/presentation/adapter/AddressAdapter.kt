package com.example.shopingapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopingapp.R

class AddressAdapter(
    private val addresses: List<String>,
    private val onAddressSelected: (String) -> Unit
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    private var selectedIndex: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(addresses[position])
        holder.itemView.setBackgroundResource(if (position == selectedIndex) R.drawable.grey_bg_selected else R.drawable.grey_bg)

        holder.itemView.setOnClickListener {
            selectedIndex = position
            notifyDataSetChanged()
            onAddressSelected(addresses[position])
        }
    }

    override fun getItemCount(): Int = addresses.size

    inner class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(address: String) {
            itemView.findViewById<TextView>(R.id.addressTextView).text = address // Adres TextView'ının ID'sini doğru ayarladığından emin ol.
        }
    }
}
