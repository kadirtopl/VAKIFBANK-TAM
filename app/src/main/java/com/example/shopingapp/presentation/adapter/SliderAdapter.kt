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

class SliderAdapter(private var sliderItems:List<SliderModel>,private  val viewPager2: ViewPager2):
    RecyclerView.Adapter<SliderAdapter.SliderViewholder>() {
        private  lateinit var  context:Context
        private val runnable= Runnable {
            sliderItems=sliderItems
            notifyDataSetChanged()
        }
    class SliderViewholder(private val binding:SliderItemContainerBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun setImage(sliderItems:SliderModel,context: Context){
            Glide.with(context)
                .load(sliderItems.url)
                .apply { RequestOptions().transform(CenterInside()) }
                .into(binding.imageSlide)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderAdapter.SliderViewholder {
context=parent.context
        val binding=SliderItemContainerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return  SliderViewholder(binding)

    }

    override fun onBindViewHolder(holder: SliderAdapter.SliderViewholder, position: Int) {

    }

    override fun getItemCount(): Int =sliderItems.size
}