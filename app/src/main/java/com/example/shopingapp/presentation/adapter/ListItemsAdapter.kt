package com.example.shopingapp.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopingapp.data.model.ItemsModel
import com.example.shopingapp.databinding.ViewholderRecommendedBinding
import com.example.shopingapp.presentation.activity.RecommendDetailActivity
import java.util.*
import kotlin.collections.ArrayList

class ListItemsAdapter(private var items: List<ItemsModel>) :
    RecyclerView.Adapter<ListItemsAdapter.ViewHolder>(), Filterable {

    private var filteredItems: List<ItemsModel> = ArrayList(items)

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
        val item = filteredItems[position]

        with(holder.binding) {
            tittleTxt.text = item.title
            priceTxt.text = "TL${item.price}"
            ratingTxt.text = item.rating.toString()

            Glide.with(holder.itemView.context)
                .load(item.picUrl.getOrNull(0))  // Null pointer riskine karşı getOrNull kullanımı
                .into(pic)

            root.setOnClickListener {
                val intent = Intent(holder.itemView.context, RecommendDetailActivity::class.java).apply {
                    putExtra("object", item)  // items modelini Parcelable olarak yolluyoruz
                }
                ContextCompat.startActivity(holder.itemView.context, intent, null)
            }
        }
    }

    override fun getItemCount(): Int = filteredItems.size

    fun updateData(newItems: List<ItemsModel>) {
        items = newItems
        filteredItems = newItems
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrEmpty()) {
                    items
                } else {
                    val query = constraint.toString().lowercase(Locale.getDefault())
                    items.filter {
                        it.title.lowercase(Locale.getDefault()).contains(query) ||
                                it.price.toString().contains(query) ||
                                it.rating.toString().contains(query)
                    }
                }
                return FilterResults().apply { values = filteredList }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as List<ItemsModel>
                notifyDataSetChanged()
            }
        }
    }
}
