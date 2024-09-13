package com.example.shopingapp.presentation.adapter

import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopingapp.R
import com.example.shopingapp.data.model.CategoryModel
import com.example.shopingapp.databinding.ViewholderCategoryBinding
import com.example.shopingapp.presentation.activity.ListItemsActivity


class CategoryAdapter(private val items: List<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    private var selectedPosition = RecyclerView.NO_POSITION


    inner class ViewHolder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tittleText.text = item.title
        Glide.with(holder.itemView.context)
            .load(item.picUrl)
            .into(holder.binding.pic)

        //
        val currentPosition = holder.adapterPosition
        if (selectedPosition == currentPosition) {
            holder.binding.pic.setBackgroundResource(R.drawable.green_button_bg) // Seçili öğe için yeşil arka plan
            ImageViewCompat.setImageTintList(
                holder.binding.pic,
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.black
                    )
                )
            )
            holder.binding.tittleText.visibility = View.GONE // Seçili öğede başlık gizlenir
            holder.binding.tittleText.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
        } else {
            holder.binding.pic.setBackgroundResource(R.drawable.grey_bg) // Seçili olmayan öğeler için gri arka plan
            ImageViewCompat.setImageTintList(
                holder.binding.pic,
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.black
                    )
                )
            )
            holder.binding.tittleText.visibility =
                View.VISIBLE // Seçili olmayan öğelerde başlık görünür
            holder.binding.tittleText.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
        }

        // Kategori öğesine tıklandığında yapılacak işlemler
        holder.binding.root.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                // Seçili pozisyonu güncelle ve listeyi yeniden yükle
                selectedPosition = position
                notifyDataSetChanged() // Bu tüm listeyi yeniler, alternatif olarak notifyItemChanged() kullanılabilir

                // Seçilen kategoriye ait detaylar için yeni bir Activity başlat
                val intent = Intent(holder.itemView.context, ListItemsActivity::class.java).apply {
                    putExtra("id", item.id.toString()) // Kategori ID'sini ekle
                    putExtra("title", item.title) // Kategori başlığını ekle
                }
                ContextCompat.startActivity(holder.itemView.context, intent, null)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
