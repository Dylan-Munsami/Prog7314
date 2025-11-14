package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.greetandeat2.data.LocalOrder
import com.example.greetandeat2.data.CartItem
import com.example.greetandeat2.repository.OfflineRepository
import com.example.greetandeat2.utils.NetworkUtils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentActivity : BaseActivity() {

    private lateinit var repository: OfflineRepository
    private val auth = FirebaseAuth.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_drawer)

        repository = OfflineRepository(com.example.greetandeat2.data.AppDatabase.getInstance(this))

        val frame = findViewById<android.widget.FrameLayout>(R.id.content_frame)
        LayoutInflater.from(this).inflate(R.layout.activity_payment, frame, true)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Payment"
        setupDrawer(toolbar)

        val cartItems = intent.getParcelableArrayListExtra<CartItem>("cartItems") ?: arrayListOf()
        val total = cartItems.sumOf { it.price * it.quantity }

        val tvAmount = findViewById<TextView>(R.id.tvAmount)
        val tvSummary = findViewById<TextView>(R.id.tvSummary)
        val btnPay = findViewById<Button>(R.id.btnPay)
        val backButton = findViewById<Button>(R.id.btnBack)

        backButton.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val summary = if (cartItems.isNotEmpty()) {
            cartItems.joinToString("\n") { "${it.name} x${it.quantity} - R${it.price * it.quantity}" }
        } else "No items in cart."

        tvSummary.text = summary
        tvAmount.text = "Total: R$total"

        btnPay.setOnClickListener {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentUser = auth.currentUser ?: run {
                Toast.makeText(this, "Please log in to pay!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ Unlock rewards
            if (!RewardsManager.isRewardUnlocked(this, "first_order")) {
                RewardsManager.unlockReward(this, "first_order")
                Toast.makeText(this, "🎉 You unlocked the 'First Order' reward!", Toast.LENGTH_LONG).show()
            }

            if (total > 500 && !RewardsManager.isRewardUnlocked(this, "big_spender")) {
                RewardsManager.unlockReward(this, "big_spender")
                Toast.makeText(this, "💰 You unlocked the 'Big Spender' reward!", Toast.LENGTH_LONG).show()
            }

            // Create local order
            val orderId = "ORD${System.currentTimeMillis()}"
            val order = LocalOrder(
                orderId = orderId,
                restaurantName = cartItems.firstOrNull()?.restaurantName ?: "Multiple",
                totalAmount = total,
                items = cartItems.joinToString("|") { "${it.name}x${it.quantity}" },
                userId = currentUser.uid
            )

            CoroutineScope(Dispatchers.IO).launch {
                repository.placeOrder(order)
                repository.clearCart()

                // Sync immediately if online
                if (NetworkUtils.isOnline(this@PaymentActivity)) {
                    val syncWorkRequest = androidx.work.OneTimeWorkRequestBuilder<com.example.greetandeat2.worker.SyncWorker>()
                        .setConstraints(androidx.work.Constraints.Builder().setRequiredNetworkType(androidx.work.NetworkType.CONNECTED).build())
                        .build()
                    androidx.work.WorkManager.getInstance(this@PaymentActivity).enqueue(syncWorkRequest)
                }
            }

            Toast.makeText(this, "Payment successful! Order placed.", Toast.LENGTH_LONG).show()

            // Navigate to TrackingActivity
            val intent = Intent(this, TrackingActivity::class.java)
            intent.putExtra("orderId", orderId)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
