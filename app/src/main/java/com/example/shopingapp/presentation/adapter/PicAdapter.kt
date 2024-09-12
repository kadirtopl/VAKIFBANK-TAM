package com.example.shopingapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopingapp.R
import com.example.shopingapp.databinding.ViewholderPicBinding

class PicAdapter(
    private val items: MutableList<String>,
    private val onImageSelected: (String) -> Unit
) :
    RecyclerView.Adapter<PicAdapter.Viewholder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class Viewholder(val binding: ViewholderPicBinding) :
        RecyclerView.ViewHolder(binding.root) {
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
        val binding =
            ViewholderPicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        holder.binding.pic.loadImage(item)

        if (selectedPosition == position) {
            holder.binding.picLayout.setBackgroundResource(R.drawable.green_bg_selected)
        } else {
            holder.binding.picLayout.setBackgroundResource(R.drawable.grey_bg)
        }
    }

    override fun getItemCount(): Int = items.size

    private fun selectItem(position: Int) {
        val previousSelectedPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousSelectedPosition)
        notifyItemChanged(selectedPosition)
        onImageSelected(items[position])
    }

    private fun ImageView.loadImage(url: String) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }
}
