package com.example.greetandeat2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.greetandeat2.data.CartItem // ✅ Import CartItem

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onItemRemoved: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.tvCartItemName)
        val itemPrice: TextView = itemView.findViewById(R.id.tvCartItemPrice)
        val itemQuantity: TextView = itemView.findViewById(R.id.tvCartItemQuantity)
        val itemRemove: ImageView = itemView.findViewById(R.id.ivRemoveItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cartItems[position]
        holder.itemName.text = item.name
        holder.itemPrice.text = "R${item.price}"
        holder.itemQuantity.text = "Qty: ${item.quantity}"

        holder.itemRemove.setOnClickListener {
            onItemRemoved(item)
        }
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateList(newList: List<CartItem>) {
        cartItems = newList
        notifyDataSetChanged()
    }
}