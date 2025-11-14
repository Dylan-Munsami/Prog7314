package com.example.greetandeat2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.greetandeat2.repository.OfflineRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentOrdersActivity : AppCompatActivity() {

    private lateinit var cardListLayout: LinearLayout
    private lateinit var repository: OfflineRepository
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_orders)

        cardListLayout = findViewById(R.id.cardListLayout)
        repository = OfflineRepository(com.example.greetandeat2.data.AppDatabase.getInstance(this))

        // Back button to Home
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            startActivity(Intent(this, Home::class.java))
            finish()
        }

        loadRecentOrders()
    }

    private fun loadRecentOrders() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            // Fetch orders for this user only
            val orders = repository.getOrders().filter { it.userId == currentUser.uid }

            cardListLayout.removeAllViews()
            if (orders.isEmpty()) {
                val emptyText = TextView(this@RecentOrdersActivity)
                emptyText.text = "No recent orders found."
                emptyText.textSize = 16f
                emptyText.setTextColor(resources.getColor(android.R.color.white))
                cardListLayout.addView(emptyText)
            } else {
                orders.forEach { order ->
                    val cardView = CardView(this@RecentOrdersActivity).apply {
                        radius = 20f
                        cardElevation = 8f
                        setCardBackgroundColor(resources.getColor(R.color.purple_500))
                        useCompatPadding = true
                        setContentPadding(24, 24, 24, 24)
                    }

                    val layout = LinearLayout(this@RecentOrdersActivity)
                    layout.orientation = LinearLayout.VERTICAL

                    val restaurantText = TextView(this@RecentOrdersActivity).apply {
                        text = "Restaurant: ${order.restaurantName}"
                        textSize = 16f
                        setTextColor(resources.getColor(android.R.color.white))
                    }

                    val itemsText = TextView(this@RecentOrdersActivity).apply {
                        text = "Items: ${order.items}"
                        textSize = 14f
                        setTextColor(resources.getColor(android.R.color.white))
                    }

                    val totalText = TextView(this@RecentOrdersActivity).apply {
                        text = "Total: R${order.totalAmount}"
                        textSize = 14f
                        setTextColor(resources.getColor(android.R.color.white))
                    }

                    val statusText = TextView(this@RecentOrdersActivity).apply {
                        text = "Status: Completed"  // ✅ Force status to Completed
                        textSize = 14f
                        setTextColor(resources.getColor(android.R.color.white))
                    }


                    layout.addView(restaurantText)
                    layout.addView(itemsText)
                    layout.addView(totalText)
                    layout.addView(statusText)

                    cardView.addView(layout)

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply { setMargins(0, 16, 0, 16) }

                    cardListLayout.addView(cardView, params)
                }
            }
        }
    }
}
