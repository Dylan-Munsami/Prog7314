package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import com.example.greetandeat2.data.AppDatabase
import com.example.greetandeat2.data.LocalOrder
import kotlinx.coroutines.launch
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

    private val REQUEST_NOTIFICATION_PERMISSION = 1001

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_drawer)

        // Check notification permission for Android 13+
        checkNotificationPermission()

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

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) !=
                android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), REQUEST_NOTIFICATION_PERMISSION)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                Toast.makeText(this, "Notification permission denied. You may miss order updates.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun loadCurrentOrder() {
        val db = AppDatabase.getInstance(this)
        val orderDao = db.orderDao()
        val orders = orderDao.getAllOrders()

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
        cardNoOrder.visibility = android.view.View.VISIBLE
        btnRefresh.isEnabled = false
        btnRefresh.text = "No Active Order"
        btnRefresh.alpha = 0.6f
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setupTrackingUI(order: LocalOrder) {
        cardNoOrder.visibility = android.view.View.GONE

        tvOrderId.text = "Order #${order.orderId.takeLast(6).uppercase()}"
        tvStatus.text = statusDescriptions[currentStatusIndex]
        tvEstimatedTime.text = "🕐 Estimated: ${getEstimatedTime(currentStatusIndex)}"

        val progress = ((currentStatusIndex + 1) / statuses.size.toFloat()) * 100
        progressBar.progress = progress.toInt()

        val history = StringBuilder()
        val timeFormat = SimpleDateFormat("HH:mm")

        for (i in 0..currentStatusIndex) {
            val timeText = if (i == currentStatusIndex) "Just now" else timeFormat.format(Date())
            history.append("• ${statuses[i]} - $timeText\n")
        }

        tvStatusHistory.text = history.toString().trim()
        btnRefresh.isEnabled = true
        btnRefresh.alpha = 1f

        if (currentStatusIndex < statuses.size - 1) {
            btnRefresh.text = "Next: ${statuses[currentStatusIndex + 1]}"
        } else {
            btnRefresh.text = "✅ Order Complete"
            btnRefresh.isEnabled = false
            btnRefresh.alpha = 0.6f

            lifecycleScope.launch {
                kotlinx.coroutines.delay(3000)
                markOrderAsDelivered(order.orderId)
                showNoOrderUI()
            }
        }

        // Initial notification for current status
        showStatusNotification(order.orderId, statuses[currentStatusIndex], statusDescriptions[currentStatusIndex])
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun progressToNextStatus() {
        if (currentStatusIndex >= statuses.size - 1) return

        currentStatusIndex++

        tvStatus.text = statusDescriptions[currentStatusIndex]
        tvEstimatedTime.text = "🕐 Estimated: ${getEstimatedTime(currentStatusIndex)}"

        val progress = ((currentStatusIndex + 1) / statuses.size.toFloat()) * 100
        progressBar.progress = progress.toInt()

        val timeFormat = SimpleDateFormat("HH:mm")
        val currentTime = timeFormat.format(Date())
        val currentHistory = tvStatusHistory.text.toString()
        tvStatusHistory.text = "$currentHistory\n• ${statuses[currentStatusIndex]} - $currentTime"

        if (currentStatusIndex < statuses.size - 1) {
            btnRefresh.text = "Next: ${statuses[currentStatusIndex + 1]}"
        } else {
            btnRefresh.text = "✅ Order Complete"
            btnRefresh.isEnabled = false
            btnRefresh.alpha = 0.6f

            currentOrder?.let { order ->
                lifecycleScope.launch {
                    markOrderAsDelivered(order.orderId)
                    kotlinx.coroutines.delay(3000)
                    showNoOrderUI()
                }
            }
        }

        // Notification for status update
        currentOrder?.let { order ->
            showStatusNotification(order.orderId, statuses[currentStatusIndex], statusDescriptions[currentStatusIndex])
        }

        Toast.makeText(
            this,
            "Status updated: ${statuses[currentStatusIndex]}",
            Toast.LENGTH_SHORT
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

    // ===== Push Notification Methods =====

    private fun showStatusNotification(orderId: String, status: String, description: String) {
        val notificationId = (1000..9999).random()

        val intent = Intent(this, TrackingActivity::class.java).apply {
            putExtra("orderId", orderId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = android.app.PendingIntent.getActivity(
            this, notificationId, intent,
            android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "greet_and_eat_tracking_notifications"
        createNotificationChannel(channelId)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(getNotificationIcon())
            .setContentTitle("Order Update: $status")
            .setContentText(description)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        // Only show if permission granted
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) ==
            android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(this).notify(notificationId, notificationBuilder.build())
        }
    }

    private fun createNotificationChannel(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                channelId,
                "Order Tracking Notifications",
                android.app.NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for order status updates"
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

// References:
// Firebase Firestore/Room database access for local order tracking: https://developer.android.com/training/data-storage/room
// Android Notification channels: https://developer.android.com/training/notify-user/channels
// NotificationCompat usage: https://developer.android.com/reference/androidx/core/app/NotificationCompat
// Android lifecycleScope and coroutines: https://developer.android.com/kotlin/coroutines
// Handling notification permissions (Android 13+): https://developer.android.com/about/versions/13/behavior-changes-13#notifications

