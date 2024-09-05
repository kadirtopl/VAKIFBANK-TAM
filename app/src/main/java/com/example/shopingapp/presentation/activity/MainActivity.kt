package com.example.shopingapp.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.shopingapp.R
import com.example.shopingapp.data.model.SliderModel
import com.example.shopingapp.databinding.ActivityMainBinding
import com.example.shopingapp.presentation.adapter.SliderAdapter
import com.example.shopingapp.presentation.viewModel.MainViewModel

class MainActivity : BasicActivity() {
    private lateinit var binding :ActivityMainBinding
    private  val viewModel= MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBanner()

        }
    private fun banners(image:List<SliderModel>) {

        binding.viewPager2.adapter=SliderAdapter(image,binding.viewPager2)
        binding.viewPager2.clipToPadding=false
        binding.viewPager2.clipChildren=false
        binding.viewPager2.offscreenPageLimit=3
        binding.viewPager2.getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer=CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.viewPager2.setPageTransformer(compositePageTransformer)
        if(image.size>1){
            binding.dotIndicator.visibility= View.VISIBLE
            binding.dotIndicator.attachTo(binding.viewPager2)
        }
    }
     private  fun initBanner( ){
        binding.progressBarSlider.visibility=View.VISIBLE
         viewModel.banners.observe(this, Observer {
             banners(it)
             binding.progressBarSlider.visibility=View.GONE
         })
    viewModel.loadBanners()

     }



    }
