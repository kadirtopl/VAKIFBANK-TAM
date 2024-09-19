package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.shopingapp.R
import com.example.shopingapp.data.model.SliderModel
import com.example.shopingapp.databinding.ActivityMainBinding
import com.example.shopingapp.presentation.adapter.CategoryAdapter
import com.example.shopingapp.presentation.adapter.RecommendedAdapter
import com.example.shopingapp.presentation.adapter.SliderAdapter
import com.example.shopingapp.presentation.viewModel.MainViewModel

class MainActivity : BasicActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner()
        initCategory()
        initRecommeded()

        binding.apply {
            cartBtn.setOnClickListener {
                val intent = Intent(this@MainActivity, CartActvivity::class.java)
                startActivity(intent)
            }

            chatBot.setOnClickListener {
                val url = "https://poe.com/VAKIFBANK-BOT"
                val intent = Intent(this@MainActivity, WebViewActivity::class.java)
                intent.putExtra("URL", url)
                startActivity(intent)
            }
            discoverBtn.setOnClickListener{
                val intent=Intent(this@MainActivity,DiscoverActivity::class.java)
                startActivity(intent)
            }
            allTxt.setOnClickListener{
                val intent=Intent(this@MainActivity,DiscoverActivity::class.java)
                startActivity(intent)
            }
            all2Txt.setOnClickListener{
                val intent=Intent(this@MainActivity,DiscoverActivity::class.java)
                startActivity(intent)
            }
            searchBtn.setOnClickListener{
                val intent=Intent(this@MainActivity,SearchActivity::class.java)
                startActivity(intent)
            }
            orderBtn.setOnClickListener{
                val intent=Intent(this@MainActivity,OrderHistoryActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun initRecommeded() {
        binding.progressBarRecommend.visibility = View.VISIBLE
        viewModel.recommended.observe(this, Observer {
            binding.viewRecommendation.layoutManager = GridLayoutManager(this@MainActivity, 2)
            binding.viewRecommendation.adapter = RecommendedAdapter(it)
            binding.progressBarRecommend.visibility = View.GONE
        })
        viewModel.loadRecommended()
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.categories.observe(this, Observer {
            binding.viewCategory.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.viewCategory.adapter = CategoryAdapter(it)
            binding.progressBarCategory.visibility = View.GONE
        })
        viewModel.loadCategory()
    }

    private fun banners(image: List<SliderModel>) {
        binding.viewPager2.adapter = SliderAdapter(image, binding.viewPager2)
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.offscreenPageLimit = 3
        binding.viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.viewPager2.setPageTransformer(compositePageTransformer)
        if (image.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.viewPager2)
        }
    }

    private fun initBanner() {
        binding.progressBarSlider.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer {
            banners(it)
            binding.progressBarSlider.visibility = View.GONE
        })
        viewModel.loadBanners()
    }
}
