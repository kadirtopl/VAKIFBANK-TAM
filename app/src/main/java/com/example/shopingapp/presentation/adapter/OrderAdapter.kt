import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopingapp.data.model.OrderModel
import com.example.shopingapp.databinding.ItemOrderBinding

class OrderAdapter(
    private var orders: List<OrderModel>,
    private val onOrderClick: (OrderModel) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private var originalOrders: List<OrderModel> = orders // Orijinal sipariş listesi

    inner class OrderViewHolder(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderModel) {
            binding.orderDateTxt.text = order.orderDate
            binding.tittleTxt.text = order.items.joinToString { it.title }
            binding.totalAmountTxt.text = "Toplam: TL ${order.totalAmount}"
            // Diğer alanları bind et
            binding.root.setOnClickListener { onOrderClick(order) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    // Filtreleme fonksiyonu
    fun filter(query: String) {
        orders = if (query.isEmpty()) {
            originalOrders // Eğer sorgu boşsa orijinal listeyi döndür
        } else {
            originalOrders.filter { order ->
                order.items.any { it.title.contains(query, ignoreCase = true) } || // Ürün ismi
                        order.orderDate.contains(query, ignoreCase = true) // Sipariş tarihi
            }
        }
        notifyDataSetChanged() // Listeyi güncelle
    }
}
