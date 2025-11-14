package com.example.greetandeat2

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val onAddClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivImage = itemView.findViewById<ImageView>(R.id.menuItemImage)
        private val tvName = itemView.findViewById<TextView>(R.id.tvMenuItemName)
        private val tvPrice = itemView.findViewById<TextView>(R.id.tvMenuItemPrice)
        private val btnAdd = itemView.findViewById<Button>(R.id.btnAddToCart)

        fun bind(menuItem: MenuItem) {
            tvName.text = menuItem.name
            tvPrice.text = "R${menuItem.price}"
            ivImage.setImageResource(menuItem.imageRes)

            btnAdd.setOnClickListener {
                val context = itemView.context
                val quantities = (1..10).map { it.toString() }.toTypedArray()
                var selectedQty = 1

                AlertDialog.Builder(context)
                    .setTitle("Select Quantity")
                    .setSingleChoiceItems(quantities, 0) { _, which ->
                        selectedQty = quantities[which].toInt()
                    }
                    .setPositiveButton("Add to Cart") { _, _ ->
                        val selectedItem = menuItem.copy(quantity = selectedQty)
                        onAddClick(selectedItem)
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }
    }
}
