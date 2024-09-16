package com.example.shopingapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopingapp.R
import com.example.shopingapp.databinding.ViewholderModelBinding

class SelectModelAdapter(val items: MutableList<String>) :
    RecyclerView.Adapter<SelectModelAdapter.Viewholder>() {

    private var selectedPosition = RecyclerView.NO_POSITION // Seçilen pozisyonu takip eder
    private lateinit var context: Context // Bağlantılı context

    // ViewHolder sınıfı, model öğelerinin görünümünü temsil eder
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
        context = parent.context // Bağlantılı context'i alır
        val binding = ViewholderModelBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.binding.modelTxt.text = items[position] // Model metnini ayarlar

        // Eğer pozisyon seçiliyse arka planı ve metin rengini günceller
        if (selectedPosition == position) {
            holder.binding.modelLayout.setBackgroundResource(R.drawable.green_bg_selected)
            holder.binding.modelTxt.setTextColor(context.resources.getColor(R.color.yellow))
        } else {
            // Seçili değilse  varsayılan yapar
            holder.binding.modelLayout.setBackgroundResource(R.drawable.grey_bg)
            holder.binding.modelTxt.setTextColor(context.resources.getColor(R.color.black))
        }
    }


    override fun getItemCount(): Int = items.size

    // Belirli bir pozisyondaki öğeyi seçili olarak işaretler
    private fun selectItem(position: Int) {
        val previousSelectedPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousSelectedPosition) // Önceki seçili öğeyi günceller
        notifyItemChanged(selectedPosition) // Yeni seçili öğeyi günceller
    }
}
