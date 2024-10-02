package com.example.shopingapp.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopingapp.data.model.ItemsModel
import com.example.shopingapp.databinding.ViewholderRecommendedBinding
import com.example.shopingapp.presentation.activity.RecommendDetailActivity

class RecommendedAdapter(private val items: List<ItemsModel>) :
    RecyclerView.Adapter<RecommendedAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewholderRecommendedBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderRecommendedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        with(holder.binding) {
            tittleTxt.text = item.title
            priceTxt.text = "TL${item.price} "
            ratingTxt.text = item.rating.toString()

            Glide.with(holder.itemView.context)
                .load(item.picUrl.getOrNull(0))  // Null pointer riskine karşı getOrNull kullanımı
                .into(pic)

            root.setOnClickListener {
                val intent = Intent(holder.itemView.context, RecommendDetailActivity::class.java).apply {
                    putExtra("object", item)
                }
                ContextCompat.startActivity(holder.itemView.context, intent, null)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
