package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greetandeat2.data.AppDatabase
import com.example.greetandeat2.data.CartItem
import com.example.greetandeat2.repository.OfflineRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuActivity : BaseActivity() {

    private lateinit var adapter: MenuAdapter
    private lateinit var repository: OfflineRepository

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_drawer)

        // Initialize repository
        repository = OfflineRepository(AppDatabase.getInstance(this))

        val frame = findViewById<android.widget.FrameLayout>(R.id.content_frame)
        LayoutInflater.from(this).inflate(R.layout.activity_menu, frame, true)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val restaurantName = intent.getStringExtra("restaurantName") ?: "Menu"
        toolbar.title = restaurantName
        setupDrawer(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.rvMenu)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val menuItems = intent.getParcelableArrayListExtra<MenuItem>("menu") ?: arrayListOf()

        adapter = MenuAdapter(menuItems) { menuItem ->
            addToCart(menuItem, restaurantName)
        }

        recyclerView.adapter = adapter

        // ✅ ADD BUTTON CLICK LISTENERS
        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnCheckout = findViewById<Button>(R.id.btnCheckout)

        // Back button - go back to previous activity
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Checkout button - go to cart activity
        btnCheckout.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addToCart(menuItem: MenuItem, restaurantName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val restaurantId = intent.getStringExtra("restaurantId") ?: "unknown"

            val cartItem = CartItem(
                id = "${restaurantId}_${menuItem.id}", // Create unique ID
                name = menuItem.name,
                price = menuItem.price,
                quantity = menuItem.quantity,
                restaurantId = restaurantId,
                restaurantName = restaurantName,
                imageRes = menuItem.imageRes
            )

            repository.addToCart(cartItem)

            runOnUiThread {
                Toast.makeText(this@MenuActivity, "${menuItem.name} added to cart", Toast.LENGTH_SHORT).show()
            }
        }
    }
}