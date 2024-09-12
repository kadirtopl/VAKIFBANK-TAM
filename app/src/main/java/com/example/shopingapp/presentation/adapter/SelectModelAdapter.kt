package com.example.shopingapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopingapp.R
import com.example.shopingapp.databinding.ViewholderModelBinding

class SelectModelAdapter(val items: MutableList<String>) :
    RecyclerView.Adapter<SelectModelAdapter.Viewholder>() {

    private var selectedPosition = RecyclerView.NO_POSITION
    private lateinit var context: Context

    inner class Viewholder(val binding: ViewholderModelBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectItem(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val binding = ViewholderModelBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.binding.modelTxt.text = items[position]

        if (selectedPosition == position) {
            holder.binding.modelLayout.setBackgroundResource(R.drawable.green_bg_selected)
            holder.binding.modelTxt.setTextColor(context.resources.getColor(R.color.green))
        } else {
            holder.binding.modelLayout.setBackgroundResource(R.drawable.grey_bg)
            holder.binding.modelTxt.setTextColor(context.resources.getColor(R.color.black))
        }
    }

    override fun getItemCount(): Int = items.size

    private fun selectItem(position: Int) {
        val previousSelectedPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousSelectedPosition)
        notifyItemChanged(selectedPosition)
    }
}
