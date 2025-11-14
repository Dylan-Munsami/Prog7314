package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : BaseActivity() {

    private lateinit var adapter: RestaurantAdapter


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_drawer)



        val frame = findViewById<android.widget.FrameLayout>(R.id.content_frame)
        LayoutInflater.from(this).inflate(R.layout.activity_main, frame, true)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.restaurants)
        setupDrawer(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.rvRestaurants)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val restaurants = listOf(
            Restaurant("1", "Burger King", listOf(
                MenuItem("1", "Whopper", 60.0, imageRes = R.drawable.whopper),
                MenuItem("2", "Cheese Burger", 50.0, imageRes = R.drawable.cheeseburger),
                MenuItem("3", "Chicken Nuggets", 40.0, imageRes = R.drawable.chicken_nuggets),
                MenuItem("4", "Fries", 25.0, imageRes = R.drawable.fries),
                MenuItem("5", "Coke", 15.0, imageRes = R.drawable.coke),
                MenuItem("6", "Ice Cream", 20.0, imageRes = R.drawable.ice_cream)
            ), imageRes = R.drawable.burger_king),

            Restaurant("2", "Pizza Hut", listOf(
                MenuItem("1", "Margherita Pizza", 55.0, imageRes = R.drawable.margherita_pizza),
                MenuItem("2", "Pepperoni Pizza", 65.0, imageRes = R.drawable.pepperoni_pizza),
                MenuItem("3", "Veggie Pizza", 50.0, imageRes = R.drawable.veggie_pizza),
                MenuItem("4", "Garlic Bread", 25.0, imageRes = R.drawable.garlic_bread),
                MenuItem("5", "Coke", 15.0, imageRes = R.drawable.coke),
                MenuItem("6", "Cheesy Sticks", 30.0, imageRes = R.drawable.cheesy_sticks)
            ), imageRes = R.drawable.pizza_hut),

            Restaurant("3", "Tiagos", listOf(
                MenuItem("1", "Spaghetti Bolognese", 70.0, imageRes = R.drawable.spaghetti),
                MenuItem("2", "Lasagna", 75.0, imageRes = R.drawable.lasagna),
                MenuItem("3", "Carbonara", 65.0, imageRes = R.drawable.carbonara),
                MenuItem("4", "Garlic Bread", 20.0, imageRes = R.drawable.garlic_bread),
                MenuItem("5", "Salad", 35.0, imageRes = R.drawable.salad),
                MenuItem("6", "Tiramisu", 30.0, imageRes = R.drawable.tiramisu)
            ), imageRes = R.drawable.tiagos),

            Restaurant("4", "KFC", listOf(
                MenuItem("1", "Original Chicken", 65.0, imageRes = R.drawable.original_chicken),
                MenuItem("2", "Zinger Burger", 55.0, imageRes = R.drawable.zinger_burger),
                MenuItem("3", "Popcorn Chicken", 40.0, imageRes = R.drawable.popcorn_chicken),
                MenuItem("4", "Fries", 25.0, imageRes = R.drawable.fries),
                MenuItem("5", "Coke", 15.0, imageRes = R.drawable.coke),
                MenuItem("6", "Mashed Potato", 20.0, imageRes = R.drawable.mashed_potato)
            ), imageRes = R.drawable.kfc),

            Restaurant("5", "Roccomamas", listOf(
                MenuItem("1", "Bacon Burger", 70.0, imageRes = R.drawable.bacon_burger),
                MenuItem("2", "Roccomamas Fries", 30.0, imageRes = R.drawable.fries),
                MenuItem("3", "Cheese Burger", 60.0, imageRes = R.drawable.cheeseburger),
                MenuItem("4", "Onion Rings", 25.0, imageRes = R.drawable.onion_rings),
                MenuItem("5", "Milkshake", 30.0, imageRes = R.drawable.milkshake),
                MenuItem("6", "Chicken Wings", 50.0, imageRes = R.drawable.chicken_wings)
            ), imageRes = R.drawable.roccomamas),

            Restaurant("6", "Simply Asia", listOf(
                MenuItem("1", "Pad Thai", 60.0, imageRes = R.drawable.pad_thai),
                MenuItem("2", "Green Curry", 65.0, imageRes = R.drawable.green_curry),
                MenuItem("3", "Fried Rice", 50.0, imageRes = R.drawable.fried_rice),
                MenuItem("4", "Spring Rolls", 25.0, imageRes = R.drawable.spring_rolls),
                MenuItem("5", "Coke", 15.0, imageRes = R.drawable.coke),
                MenuItem("6", "Mango Sticky Rice", 35.0, imageRes = R.drawable.mango_sticky_rice)
            ), imageRes = R.drawable.simply_asia)
        )

        adapter = RestaurantAdapter { restaurant ->
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("restaurantName", restaurant.name)
            intent.putExtra("restaurantId", restaurant.id)
            intent.putParcelableArrayListExtra("menu", ArrayList(restaurant.menu))
            startActivity(intent)
        }


        adapter.submitList(restaurants)
        recyclerView.adapter = adapter
    }
}