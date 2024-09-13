package com.example.shopingapp.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopingapp.data.model.CategoryModel
import com.example.shopingapp.data.model.ItemsModel
import com.example.shopingapp.data.model.SliderModel
import com.google.firebase.database.*

// ViewModel sınıfı, kullanıcı arayüzü verilerini yönetir ve Firebase ile etkileşim sağlar
class MainViewModel() : ViewModel() {
    // Firebase Realtime Database örneği
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    // Canlı veriler için MutableLiveData nesneleri
    private val _banner = MutableLiveData<List<SliderModel>>()
    private val _category = MutableLiveData<MutableList<CategoryModel>>()
    private val _recommended = MutableLiveData<MutableList<ItemsModel>>()

    // Canlı verilerin dışarıya sunulan görünümleri
    val banners: LiveData<List<SliderModel>> = _banner
    val categories: LiveData<MutableList<CategoryModel>> = _category
    val recommended: LiveData<MutableList<ItemsModel>> = _recommended

    // Belirli bir kategoriye ait öğeleri yükler
    fun loadFiltered(id: String) {
        val ref = firebaseDatabase.getReference("Items")

        // `id` değerini `Double` türüne dönüştürür
        val idDouble: Double? = id.toDoubleOrNull()

        // Dönüşüm başarılıysa uygun bir sorgu oluşturur, aksi takdirde varsayılan bir değer kullanır
        val query: Query = if (idDouble != null) {
            ref.orderByChild("categoryId").equalTo(idDouble)
        } else {
            ref.orderByChild("categoryId").equalTo(0.0) // Varsayılan bir değer kullanılır
        }

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Gelen verileri işleyerek MutableList oluşturur
                val lists = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(ItemsModel::class.java)
                    if (item != null) {
                        lists.add(item)
                    }
                }
                // Verileri LiveData'ya atar
                _recommended.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                // Hata durumunda yapılacak işlemler
            }
        })
    }

    // Önerilen öğeleri yükler
    fun loadRecommended() {
        val ref = firebaseDatabase.getReference("Items")
        val query: Query = ref.orderByChild("showRecommended").equalTo(true)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Gelen verileri işleyerek MutableList oluşturur
                val lists = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemsModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                // Verileri LiveData'ya atar
                _recommended.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                // Hata durumunda yapılacak işlemler
            }
        })
    }

    // Kategorileri yükler
    fun loadCategory() {
        val ref = firebaseDatabase.getReference("Category")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Gelen verileri işleyerek MutableList oluşturur
                val lists = mutableListOf<CategoryModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(CategoryModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                // Verileri LiveData'ya atar
                _category.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                // Hata durumunda yapılacak işlemler
            }
        })
    }

    // Banner'ları yükler
    fun loadBanners() {
        val ref = firebaseDatabase.getReference("Banner")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Gelen verileri işleyerek MutableList oluşturur
                val lists = mutableListOf<SliderModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(SliderModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                // Verileri LiveData'ya atar
                _banner.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                // Hata durumunda yapılacak işlemler
            }
        })
    }
}
