package com.example.greetandeat2

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Send token to your server if needed
        sendRegistrationToServer(token)
    }

    private fun getCurrentUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Log the incoming message for debugging
        android.util.Log.d("FCM_DEBUG", "Message received: ${remoteMessage.data}")

        // Handle different types of notifications
        when {
            remoteMessage.data.isNotEmpty() -> {
                // Data message - handle background processing
                handleDataMessage(remoteMessage.data)
            }
            remoteMessage.notification != null -> {
                // Notification message - handle display
                val title = remoteMessage.notification?.title ?: getString(R.string.app_name)
                val body = remoteMessage.notification?.body ?: ""
                val imageUrl = remoteMessage.notification?.imageUrl

                sendNotification(title, body, imageUrl?.toString(), remoteMessage.data)
            }
        }
    }

    private fun handleDataMessage(data: Map<String, String>) {
        val type = data["type"]
        val title = data["title"] ?: getString(R.string.app_name)
        val body = data["body"] ?: ""
        val orderId = data["orderId"]
        val status = data["status"]

        android.util.Log.d("FCM_DEBUG", "Handling data message - Type: $type, Title: $title")

        when (type) {
            "order_status" -> {
                // Update local database with new order status
                updateOrderStatus(orderId, status)
                sendNotification(title, body, null, data)
            }
            "promotion" -> {
                sendPromotionNotification(title, body, data)
            }
            "delivery" -> {
                sendDeliveryNotification(title, body, orderId, data)
            }
            else -> {
                sendNotification(title, body, null, data)
            }
        }
    }

    private fun updateOrderStatus(orderId: String?, status: String?) {
        if (orderId != null && status != null) {
            // Update order status in local database
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repository = com.example.greetandeat2.repository.OfflineRepository(
                        com.example.greetandeat2.data.AppDatabase.getInstance(this@MyFirebaseMessagingService)
                    )
                    repository.updateOrderStatus(orderId, status)
                    android.util.Log.d("FCM_DEBUG", "Order status updated: $orderId -> $status")
                } catch (e: Exception) {
                    android.util.Log.e("FCM_DEBUG", "Failed to update order status", e)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(title: String, message: String, imageUrl: String?, data: Map<String, String>) {
        val notificationId = Random.nextInt(1000, 9999)

        // Create different intents based on notification type
        val intent = when (data["type"]) {
            "order_status" -> {
                Intent(this, TrackingActivity::class.java).apply {
                    putExtra("orderId", data["orderId"])
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            }
            "promotion" -> {
                Intent(this, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            }
            else -> {
                Intent(this, Home::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            }
        }

        val pendingIntent = PendingIntent.getActivity(
            this, notificationId, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = when (data["priority"] ?: "default") {
            "high" -> "greet_and_eat_high_priority"
            else -> "greet_and_eat_default"
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(getNotificationIcon()) // Use safe icon method
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        // Add large icon if image URL is provided
        imageUrl?.let {
            try {
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.delivery_4)
                notificationBuilder.setLargeIcon(bitmap)
            } catch (e: Exception) {
                android.util.Log.e("FCM_DEBUG", "Failed to set large icon", e)
            }
        }

        // Add actions based on notification type
        when (data["type"]) {
            "order_status" -> {
                val trackIcon = if (hasDrawable(R.drawable.ic_money)) R.drawable.ic_money else android.R.drawable.ic_menu_mylocation
                notificationBuilder.addAction(
                    trackIcon,
                    "Track Order",
                    pendingIntent
                )
            }
            "promotion" -> {
                val dealsIcon = if (hasDrawable(R.drawable.ic_order)) R.drawable.ic_order else android.R.drawable.ic_menu_view
                notificationBuilder.addAction(
                    dealsIcon,
                    "View Deals",
                    pendingIntent
                )
            }
        }

        // Create notification channel for Android O and above
        createNotificationChannel(channelId, data["priority"] ?: "default")

        try {
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, notificationBuilder.build())
                android.util.Log.d("FCM_DEBUG", "Notification displayed: $title")
            }
        } catch (e: Exception) {
            android.util.Log.e("FCM_DEBUG", "Failed to show notification", e)
        }
    }

    @SuppressLint("MissingPermission")
    private fun sendPromotionNotification(title: String, message: String, data: Map<String, String>) {
        val notificationId = Random.nextInt(1000, 9999)

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("promotion", true)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, notificationId, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, "greet_and_eat_promotions")
            .setSmallIcon(getNotificationIcon())
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        createNotificationChannel("greet_and_eat_promotions", "Promotions")

        try {
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, notificationBuilder.build())
            }
        } catch (e: Exception) {
            android.util.Log.e("FCM_DEBUG", "Failed to show promotion notification", e)
        }
    }

    @SuppressLint("MissingPermission")
    private fun sendDeliveryNotification(title: String, message: String, orderId: String?, data: Map<String, String>) {
        val notificationId = Random.nextInt(1000, 9999)

        val intent = Intent(this, TrackingActivity::class.java).apply {
            putExtra("orderId", orderId)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, notificationId, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, "greet_and_eat_high_priority")
            .setSmallIcon(getNotificationIcon())
            .setContentTitle("🚚 $title")
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        createNotificationChannel("greet_and_eat_high_priority", "High Priority")

        try {
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, notificationBuilder.build())
            }
        } catch (e: Exception) {
            android.util.Log.e("FCM_DEBUG", "Failed to show delivery notification", e)
        }
    }

    private fun createNotificationChannel(channelId: String, importance: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importanceLevel = when (importance) {
                "high" -> NotificationManager.IMPORTANCE_HIGH
                else -> NotificationManager.IMPORTANCE_DEFAULT
            }

            val channel = NotificationChannel(
                channelId,
                when (channelId) {
                    "greet_and_eat_high_priority" -> "Order Updates"
                    "greet_and_eat_promotions" -> "Promotions"
                    else -> "General Notifications"
                },
                importanceLevel
            ).apply {
                description = when (channelId) {
                    "greet_and_eat_high_priority" -> "Order status and delivery updates"
                    "greet_and_eat_promotions" -> "Special offers and promotions"
                    else -> "General notifications"
                }
                // Enable lights, vibration, etc.
                enableLights(true)
                enableVibration(true)
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendRegistrationToServer(token: String) {
        val userId = getCurrentUserId()

        // If you don't have a server endpoint, just log the token
        android.util.Log.d("FCM_DEBUG", "FCM Token: $token")
        android.util.Log.d("FCM_DEBUG", "User ID: $userId")

        // If you want to send to your server, implement this:
        /*
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Your API call here
                // Example: ApiClient.service.registerFCMToken(userId, token)
                android.util.Log.d("FCM_DEBUG", "Token sent to server successfully")
            } catch (e: Exception) {
                android.util.Log.e("FCM_DEBUG", "Failed to send token to server", e)
            }
        }
        */
    }

    // Helper function to check if a drawable exists
    private fun hasDrawable(resId: Int): Boolean {
        return try {
            resources.getDrawable(resId, null)
            true
        } catch (e: Exception) {
            false
        }
    }

    // Safe method to get notification icon
    private fun getNotificationIcon(): Int {
        return if (hasDrawable(R.drawable.ic_notification)) {
            R.drawable.ic_notification
        } else {
            android.R.drawable.ic_dialog_info
        }
    }
}