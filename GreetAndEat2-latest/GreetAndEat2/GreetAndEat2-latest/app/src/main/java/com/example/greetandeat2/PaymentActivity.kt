package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.greetandeat2.data.CartItem
import com.example.greetandeat2.data.LocalOrder
import com.example.greetandeat2.repository.OfflineRepository
import com.example.greetandeat2.utils.NetworkUtils
import com.example.greetandeat2.worker.SyncWorker
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

        val frame = findViewById<FrameLayout>(R.id.content_frame)
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

        // Payment method UI
        val rbCash = findViewById<RadioButton>(R.id.rbCash)
        val rbCard = findViewById<RadioButton>(R.id.rbCard)
        val cardSection = findViewById<LinearLayout>(R.id.cardSection)
        val rgSavedCards = findViewById<RadioGroup>(R.id.rgSavedCards)

        val etCardName = findViewById<EditText>(R.id.etCardName)
        val etCardNumber = findViewById<EditText>(R.id.etCardNumber)
        val etExpiry = findViewById<EditText>(R.id.etExpiry)
        val etCVV = findViewById<EditText>(R.id.etCVV)

        // Toggle card form
        rbCash.setOnCheckedChangeListener { _, isChecked -> if (isChecked) cardSection.visibility = View.GONE }
        rbCard.setOnCheckedChangeListener { _, isChecked ->
            cardSection.visibility = if (isChecked) View.VISIBLE else View.GONE
            if (isChecked) loadSavedCards()
        }

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

            val isCard = rbCard.isChecked
            var selectedCard: String? = null

            if (isCard) {
                // Check saved cards selection
                val checkedId = rgSavedCards.checkedRadioButtonId
                selectedCard = if (checkedId != -1) {
                    val rb = findViewById<RadioButton>(checkedId)
                    rb.tag as String
                } else {
                    // fallback to manual entry
                    val number = etCardNumber.text.toString()
                    val name = etCardName.text.toString()
                    val expiry = etExpiry.text.toString()
                    val cvv = etCVV.text.toString()
                    if (number.length < 8 || name.isBlank() || expiry.isBlank() || cvv.length < 3) {
                        Toast.makeText(this, "Please enter valid card details or select a saved card.", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    "$number|$name|$expiry"
                }
            }

            val paymentMethod = if (isCard) "Card Payment" else "Cash on Delivery"
            Toast.makeText(this, "Processing $paymentMethod...", Toast.LENGTH_SHORT).show()

            // Unlock rewards
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

                if (NetworkUtils.isOnline(this@PaymentActivity)) {
                    val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
                        .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                        .build()
                    WorkManager.getInstance(this@PaymentActivity).enqueue(syncRequest)
                }

                // Show local notification on main thread
                runOnUiThread {
                    showOrderNotification(orderId, total.toInt())

                }
            }

            Toast.makeText(this, "Payment successful! Order placed.", Toast.LENGTH_LONG).show()

            val intent = Intent(this, TrackingActivity::class.java)
            intent.putExtra("orderId", orderId)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun loadSavedCards() {
        val userPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val currentUserEmail = userPrefs.getString("lastUserEmail", "") ?: return
        val safeEmail = currentUserEmail.replace(".", "_")
        val prefs = getSharedPreferences("user_cards_$safeEmail", Context.MODE_PRIVATE)
        val cardsSet = prefs.getStringSet("cards", emptySet()) ?: emptySet()

        val rgSavedCards = findViewById<RadioGroup>(R.id.rgSavedCards)
        rgSavedCards.removeAllViews()

        if (cardsSet.isEmpty()) {
            val emptyText = TextView(this)
            emptyText.text = "No saved cards. Enter card details below."
            rgSavedCards.addView(emptyText)
        } else {
            cardsSet.forEach { card ->
                val parts = card.split("|")
                val number = parts.getOrElse(0) { "" }
                val name = parts.getOrElse(1) { "" }
                val expiry = parts.getOrElse(2) { "" }

                val rb = RadioButton(this)
                rb.text = "**** **** **** ${number.takeLast(4)} | $name | Exp: $expiry"
                rb.tag = card
                rgSavedCards.addView(rb)
            }
        }
    }

    // ===== Push Notification Methods =====

    @SuppressLint("MissingPermission")
    private fun showOrderNotification(orderId: String, total: Int) {
        val notificationId = (1000..9999).random()

        val intent = Intent(this, TrackingActivity::class.java).apply {
            putExtra("orderId", orderId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = android.app.PendingIntent.getActivity(
            this, notificationId, intent,
            android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "greet_and_eat_order_notifications"
        createNotificationChannel(channelId)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(getNotificationIcon())
            .setContentTitle("Order Placed!")
            .setContentText("Your order (R$total) has been successfully placed.")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        NotificationManagerCompat.from(this).notify(notificationId, notificationBuilder.build())
    }

    private fun createNotificationChannel(channelId: String) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                channelId,
                "Order Notifications",
                android.app.NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for placed orders"
                enableLights(true)
                enableVibration(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun getNotificationIcon(): Int {
        return if (resources.getIdentifier("ic_notification", "drawable", packageName) != 0) {
            R.drawable.ic_notification
        } else android.R.drawable.ic_dialog_info
    }
}
