package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.greetandeat2.data.AppDatabase
import com.example.greetandeat2.data.LocalOrder
import java.text.SimpleDateFormat
import java.util.*

class TrackingActivity : BaseActivity() {

    private lateinit var tvStatus: TextView
    private lateinit var tvEstimatedTime: TextView
    private lateinit var tvOrderId: TextView
    private lateinit var tvStatusHistory: TextView
    private lateinit var btnRefresh: Button
    private lateinit var btnGoHome: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var cardNoOrder: CardView

    private var currentStatusIndex = 0
    private val statuses = listOf(
        "Order Received",
        "Preparing Food",
        "Ready for Pickup",
        "Out for Delivery",
        "Delivered"
    )

    private val statusDescriptions = listOf(
        "We've received your order and are getting started",
        "Our kitchen is preparing your delicious meal",
        "Your order is ready for pickup",
        "Your order is on the way to you",
        "Order delivered! Enjoy your meal!"
    )

    private var currentOrder: LocalOrder? = null

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_drawer)

        val frame = findViewById<android.widget.FrameLayout>(R.id.content_frame)
        LayoutInflater.from(this).inflate(R.layout.activity_tracking, frame, true)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Order Tracking"
        setupDrawer(toolbar)

        // Initialize views
        tvOrderId = findViewById(R.id.tvOrderId)
        tvStatus = findViewById(R.id.tvTrackingStatus)
        tvEstimatedTime = findViewById(R.id.tvEstimatedTime)
        tvStatusHistory = findViewById(R.id.tvStatusHistory)
        btnRefresh = findViewById(R.id.btnRefresh)
        btnGoHome = findViewById(R.id.btnGoHome)
        progressBar = findViewById(R.id.progressBar)
        cardNoOrder = findViewById(R.id.cardNoOrder)

        // Set up button listeners
        btnRefresh.setOnClickListener { progressToNextStatus() }

        btnGoHome.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // Load current order
        lifecycleScope.launch {
            loadCurrentOrder()
        }
    }

    private suspend fun loadCurrentOrder() {
        val db = AppDatabase.getInstance(this)
        val orderDao = db.orderDao()
        val orders = orderDao.getAllOrders()

        // Find the most recent non-delivered order
        currentOrder = orders
            .sortedByDescending { it.createdAt }
            .firstOrNull { it.status != "DELIVERED" && it.status != "CANCELLED" }

        if (currentOrder == null) {
            showNoOrderUI()
        } else {
            setupTrackingUI(currentOrder!!)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showNoOrderUI() {
        // Hide order views, show no order message
        findViewById<androidx.cardview.widget.CardView>(R.id.cardNoOrder).visibility = android.view.View.VISIBLE
        findViewById<androidx.cardview.widget.CardView>(R.id.cardNoOrder).visibility = android.view.View.VISIBLE // This is the order card
        btnRefresh.isEnabled = false
        btnRefresh.text = "No Active Order"
        btnRefresh.alpha = 0.6f

        // Update the no order card visibility
        cardNoOrder.visibility = android.view.View.VISIBLE
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setupTrackingUI(order: LocalOrder) {
        // Show order views, hide no order message
        cardNoOrder.visibility = android.view.View.GONE

        tvOrderId.text = "Order #${order.orderId.takeLast(6).toUpperCase()}"
        tvStatus.text = statusDescriptions[currentStatusIndex]
        tvEstimatedTime.text = "🕐 Estimated: ${getEstimatedTime(currentStatusIndex)}"

        // Update progress bar
        val progress = ((currentStatusIndex + 1) / statuses.size.toFloat()) * 100
        progressBar.progress = progress.toInt()

        // Build status history
        val history = StringBuilder()
        val timeFormat = SimpleDateFormat("HH:mm")

        for (i in 0..currentStatusIndex) {
            val timeText = if (i == currentStatusIndex) "Just now" else timeFormat.format(Date())
            history.append("• ${statuses[i]} - $timeText\n")
        }

        tvStatusHistory.text = history.toString().trim()
        btnRefresh.isEnabled = true
        btnRefresh.alpha = 1f

        // Update button text based on current status
        if (currentStatusIndex < statuses.size - 1) {
            btnRefresh.text = "Next: ${statuses[currentStatusIndex + 1]}"
        } else {
            btnRefresh.text = "✅ Order Complete"
            btnRefresh.isEnabled = false
            btnRefresh.alpha = 0.6f

            // Automatically clear the order after a delay when delivered
            lifecycleScope.launch {
                kotlinx.coroutines.delay(3000) // 3 seconds delay
                markOrderAsDelivered(order.orderId)
                showNoOrderUI()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun progressToNextStatus() {
        if (currentStatusIndex >= statuses.size - 1) return

        currentStatusIndex++

        // Update UI
        tvStatus.text = statusDescriptions[currentStatusIndex]
        tvEstimatedTime.text = "🕐 Estimated: ${getEstimatedTime(currentStatusIndex)}"

        // Update progress
        val progress = ((currentStatusIndex + 1) / statuses.size.toFloat()) * 100
        progressBar.progress = progress.toInt()

        // Add to history
        val timeFormat = SimpleDateFormat("HH:mm")
        val currentTime = timeFormat.format(Date())
        val currentHistory = tvStatusHistory.text.toString()
        tvStatusHistory.text = "$currentHistory\n• ${statuses[currentStatusIndex]} - $currentTime"

        // Update button
        if (currentStatusIndex < statuses.size - 1) {
            btnRefresh.text = "Next: ${statuses[currentStatusIndex + 1]}"
        } else {
            btnRefresh.text = "✅ Order Complete"
            btnRefresh.isEnabled = false
            btnRefresh.alpha = 0.6f

            // Mark as delivered and clear after delay
            currentOrder?.let { order ->
                lifecycleScope.launch {
                    markOrderAsDelivered(order.orderId)
                    // Wait 3 seconds then show no order
                    kotlinx.coroutines.delay(3000)
                    showNoOrderUI()
                }
            }
        }

        android.widget.Toast.makeText(
            this,
            "Status updated: ${statuses[currentStatusIndex]}",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }

    private suspend fun markOrderAsDelivered(orderId: String) {
        val db = AppDatabase.getInstance(this)
        val orderDao = db.orderDao()
        val order = orderDao.getOrderById(orderId)
        order?.let {
            val updatedOrder = it.copy(status = "DELIVERED")
            orderDao.updateOrder(updatedOrder)
        }
    }

    private fun getEstimatedTime(statusIndex: Int): String {
        return when (statusIndex) {
            0 -> "20-25 minutes"
            1 -> "15-20 minutes"
            2 -> "10-15 minutes"
            3 -> "5-10 minutes"
            else -> "Delivered"
        }
    }
}