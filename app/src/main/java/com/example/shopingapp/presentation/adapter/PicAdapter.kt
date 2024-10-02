package com.example.shopingapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopingapp.R
import com.example.shopingapp.databinding.ViewholderPicBinding


class PicAdapter(
    private val items: MutableList<String>, // Resim URL'lerini içeren liste
    private val onImageSelected: (String) -> Unit // Bir resim seçildiğinde çağrılacak geri çağırma fonksiyonu
) :
    RecyclerView.Adapter<PicAdapter.Viewholder>() {

    // Seçili olan öğenin pozisyonu
    private var selectedPosition = RecyclerView.NO_POSITION

    inner class Viewholder(val binding: ViewholderPicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // ViewHolder'ın kök görünümüne tıklama olay dinleyicisi ekler
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectItem(position) // Resim seçildiğinde selectItem metodunu çağırır
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding =
            ViewholderPicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    /**
     * Belirli bir pozisyondaki resmi ViewHolder'a bağlar ve görünümünü günceller.
     */
    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        holder.binding.pic.loadImage(item) // Resmi yükler

        // Seçili pozisyona göre arka planı ayarla
        if (selectedPosition == position) {
            holder.binding.picLayout.setBackgroundResource(R.drawable.green_bg_selected) // Seçili öğe için yeşil arka plan
        } else {
            holder.binding.picLayout.setBackgroundResource(R.drawable.grey_bg) // Seçili olmayan öğeler için gri arka plan
        }
    }


    override fun getItemCount(): Int = items.size

    //Bir resmi seçer ve geri çağırma fonksiyonunu çağırır.

    private fun selectItem(position: Int) {
        val previousSelectedPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousSelectedPosition) // Önceki seçili öğeyi güncelle
        notifyItemChanged(selectedPosition) // Yeni seçili öğeyi güncelle
        onImageSelected(items[position]) // Seçilen resim için geri çağırma fonksiyonunu çağır
    }


     //ImageView'a resmi yükler.

    private fun ImageView.loadImage(url: String) {
        Glide.with(this.context)
            .load(url)
            .into(this) // Glide ile resmi yükler
    }
}
