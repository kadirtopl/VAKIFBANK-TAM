package com.example.shopingapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.request.RequestOptions
import com.example.shopingapp.data.model.SliderModel
import com.example.shopingapp.databinding.SliderItemContainerBinding

class SliderAdapter(
    private var sliderItems: List<SliderModel>,
    private val viewPager2: ViewPager2 // Slider'ı göstermek için ViewPager2 bileşeni
) : RecyclerView.Adapter<SliderAdapter.SliderViewholder>() {

    private lateinit var context: Context // Bağlantılı context
    private val runnable = Runnable {
        // Slider öğelerinin tekrar yüklenmesini sağlar
        notifyDataSetChanged()
    }

    class SliderViewholder(private val binding: SliderItemContainerBinding) : RecyclerView.ViewHolder(binding.root) {
        // Slider öğesinin resmini yükler
        fun setImage(sliderItems: SliderModel, context: Context) {
            Glide.with(context)
                .load(sliderItems.url)
                .apply(RequestOptions().transform(CenterInside())) // Resmi ortalar ve boyutlandırır
                .into(binding.imageSlide) // Resmi ImageView'a yükler
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderViewholder {
        context = parent.context // Bağlantılı context'i alır
        val binding = SliderItemContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewholder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewholder, position: Int) {
        holder.setImage(sliderItems[position], context) // Resmi yükler

        // Eğer son pozisyondaysa, slider'ı yeniden yükler
        if (position == sliderItems.lastIndex - 1) {
            viewPager2.post(runnable) // Bir sonraki güncellemeyi yapar
        }
    }

    override fun getItemCount(): Int = sliderItems.size
}
