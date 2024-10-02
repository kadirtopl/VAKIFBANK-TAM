package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.project1762.Helper.ManagmentCart
import com.example.shopingapp.R
import com.example.shopingapp.data.model.ItemsModel
import com.example.shopingapp.databinding.ActivityRecommendDetailBinding
import com.example.shopingapp.presentation.adapter.PicAdapter
import com.example.shopingapp.presentation.adapter.SelectModelAdapter

class RecommendDetailActivity : BasicActivity() {
    private lateinit var binding: ActivityRecommendDetailBinding
    private lateinit var item: ItemsModel
    private var numberOrder = 1
    private lateinit var managmentCart: ManagmentCart
    private var isFavorite = false // Favori durumunu takip et

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        managmentCart = ManagmentCart(this)

        getBundle()
        initList()
    }

    private fun initList() {
        val modelList = item.model.toMutableList()
        val picList = item.picUrl.toMutableList()

        binding.modelList.adapter = SelectModelAdapter(modelList)
        binding.modelList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        Glide.with(this)
            .load(picList.firstOrNull())
            .into(binding.img)

        binding.picList.adapter = PicAdapter(picList) { selectedImageUrl ->
            Glide.with(this)
                .load(selectedImageUrl)
                .into(binding.img)
        }
        binding.picList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object") ?: run {
            finish() // Eğer item null ise, Activity'yi kapat
            return
        }

        binding.titleTxt.text = item.title
        binding.descriptionTxt.text = item.description
        binding.priceTxt.text = "TL${item.price}"
        binding.ratingTxt.text = "${item.rating} Yıldız"

        binding.addToCartBtn.setOnClickListener {
            item.numberInCart = numberOrder
            managmentCart.insertItem(item)
        }

        binding.backBtn.setOnClickListener { finish() }
        binding.cartBtn.setOnClickListener {
            intent = Intent(this@RecommendDetailActivity, CartActivity::class.java)
            startActivity(intent)
        }
    }


}
