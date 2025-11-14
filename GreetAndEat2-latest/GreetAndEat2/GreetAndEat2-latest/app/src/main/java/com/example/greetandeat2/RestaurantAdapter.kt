package com.example.greetandeat2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RestaurantAdapter(
    private val onClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    private var restaurants = listOf<Restaurant>()

    fun submitList(list: List<Restaurant>) {
        restaurants = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int = restaurants.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivImage = itemView.findViewById<ImageView>(R.id.restaurantImage)
        private val tvName = itemView.findViewById<TextView>(R.id.restaurantName)
        private val tvDescription = itemView.findViewById<TextView>(R.id.restaurantDescription)
        private val tvRating = itemView.findViewById<TextView>(R.id.restaurantRating)
        private val tvTime = itemView.findViewById<TextView>(R.id.restaurantTime)

        fun bind(restaurant: Restaurant) {
            tvName.text = restaurant.name
            tvDescription.text = "Delicious food waiting for you!"
            tvRating.text = "⭐ 4.5"
            tvTime.text = "25-30 min"

            // Set restaurant image
            ivImage.setImageResource(restaurant.imageRes)

            itemView.setOnClickListener { onClick(restaurant) }
        }
    }
}
