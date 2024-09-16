package com.example.shopingapp.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopingapp.data.model.CategoryModel
import com.example.shopingapp.data.model.ItemsModel
import com.example.shopingapp.data.model.SliderModel
import com.google.firebase.database.*

// ViewModel sınıfı, kullanıcı arayüzü verilerini yönetir ve Firebase ile etkileşim sağlar
class MainViewModel : ViewModel() {

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val _banner = MutableLiveData<List<SliderModel>>()
    private val _category = MutableLiveData<List<CategoryModel>>()
    private val _recommended = MutableLiveData<List<ItemsModel>>()

    val banners: LiveData<List<SliderModel>> get() = _banner
    val categories: LiveData<List<CategoryModel>> get() = _category
    val recommended: LiveData<List<ItemsModel>> get() = _recommended

    // Belirli bir kategoriye ait öğeleri yükler
    fun loadFiltered(id: String) {
        val ref = firebaseDatabase.getReference("Items")
        val idDouble: Double? = id.toDoubleOrNull()
        val query: Query = idDouble?.let {
            ref.orderByChild("categoryId").equalTo(it)
        } ?: ref.orderByChild("categoryId").equalTo(0.0)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = snapshot.children.mapNotNull { it.getValue(ItemsModel::class.java) }
                _recommended.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                _recommended.value = emptyList() // Hata durumunda boş bir liste döner
            }
        })
    }

    // Önerilen öğeleri yükler
    fun loadRecommended() {
        val ref = firebaseDatabase.getReference("Items")
        val query = ref.orderByChild("showRecommended").equalTo(true)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = snapshot.children.mapNotNull { it.getValue(ItemsModel::class.java) }
                _recommended.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                _recommended.value = emptyList() // Hata durumunda boş bir liste döner
            }
        })
    }

    // Kategorileri yükler
    fun loadCategory() {
        val ref = firebaseDatabase.getReference("Category")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = snapshot.children.mapNotNull { it.getValue(CategoryModel::class.java) }
                _category.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                _category.value = emptyList() // Hata durumunda boş bir liste döner
            }
        })
    }

    // Banner'ları yükler
    fun loadBanners() {
        val ref = firebaseDatabase.getReference("Banner")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = snapshot.children.mapNotNull { it.getValue(SliderModel::class.java) }
                _banner.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                _banner.value = emptyList() // Hata durumunda boş bir liste döner
            }
        })
    }

    // Örnek fonksiyon: Tüm ürünleri yükler
    fun loadAllItems() {
        val ref = firebaseDatabase.getReference("Items")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = snapshot.children.mapNotNull { it.getValue(ItemsModel::class.java) }
                _recommended.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                _recommended.value = emptyList() // Hata durumunda boş bir liste döner
            }
        })
    }
}
