package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.example.greetandeat2.repository.OfflineRepository
import com.example.greetandeat2.data.AppDatabase
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Home : BaseActivity() {

    private lateinit var tvTotalOrders: TextView
    private lateinit var tvRewardPoints: TextView
    private lateinit var repository: OfflineRepository
    private val auth = FirebaseAuth.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_drawer)

        // Request notification permission FIRST
        requestNotificationPermission()

        // Inflate Home layout into base drawer
        val frame = findViewById<FrameLayout>(R.id.content_frame)
        layoutInflater.inflate(R.layout.activity_home, frame, true)

        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.home)
        setupDrawer(toolbar)

        // Handle edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize repository
        repository = OfflineRepository(AppDatabase.getInstance(this))

        // Initialize UI components
        initializeUI()

        // --- User Authentication Check ---
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, Login::class.java))
            finish()
            return
        }

        // --- Dashboard TextViews ---
        tvTotalOrders = findViewById(R.id.tvOrdersToday)
        tvRewardPoints = findViewById(R.id.tvRewardPoints)

        // --- Update UI ---
        updateTotalOrders(currentUser.uid)
        updateRewardPoints()

        // Test FCM token
        getFCMTokenForTesting()
    }

    private fun initializeUI() {
        // --- Quick Action Cards ---
        findViewById<CardView>(R.id.cardOrderFood)?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        findViewById<CardView>(R.id.cardTrackOrder)?.setOnClickListener {
            startActivity(Intent(this, TrackingActivity::class.java))
        }

        findViewById<CardView>(R.id.cardRewards)?.setOnClickListener {
            startActivity(Intent(this, RewardsActivity::class.java))
        }

        findViewById<CardView>(R.id.cardRecentOrders)?.setOnClickListener {
            startActivity(Intent(this, RecentOrdersActivity::class.java))
        }

        // Check if these views exist before setting click listeners
        findViewById<MaterialCardView>(R.id.cardFoodDash)?.setOnClickListener {
            startActivity(Intent(this, FoodDashGameActivity::class.java))
        }



        // Handle active order card if it exists
        findViewById<CardView>(R.id.activeOrderCard)?.setOnClickListener {
            // Navigate to order details or tracking
            startActivity(Intent(this, TrackingActivity::class.java))
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
    }

    private fun updateTotalOrders(userId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val allOrders = repository.getOrders()
                val userOrders = allOrders.filter { it.userId == userId }

                runOnUiThread {
                    tvTotalOrders.text = userOrders.size.toString()

                    // Show or hide active order card
                    val activeOrderCard = findViewById<CardView>(R.id.activeOrderCard)
                    val tvOrderStatus = findViewById<TextView>(R.id.tvOrderStatus)
                    val activeOrder = userOrders.lastOrNull() // latest order

                    if (activeOrder != null && activeOrderCard != null && tvOrderStatus != null) {
                        activeOrderCard.visibility = CardView.VISIBLE
                        tvOrderStatus.text = activeOrder.status.replace("_", " ").lowercase()
                            .replaceFirstChar { it.uppercase() }
                    } else {
                        activeOrderCard?.visibility = CardView.GONE
                    }
                }
            } catch (e: Exception) {
                // Handle error
                runOnUiThread {
                    tvTotalOrders.text = "0"
                    findViewById<CardView>(R.id.activeOrderCard)?.visibility = CardView.GONE
                }
            }
        }
    }

    private fun updateRewardPoints() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = auth.currentUser
                val points = if (user != null) {
                    // Simple points calculation - adjust based on your actual logic
                    calculateRewardPoints(user.uid)
                } else {
                    "0"
                }

                runOnUiThread {
                    tvRewardPoints.text = points
                }
            } catch (e: Exception) {
                runOnUiThread {
                    tvRewardPoints.text = "0"
                }
            }
        }
    }

    private suspend fun calculateRewardPoints(userId: String): String {
        // Simple reward points calculation
        // Replace this with your actual reward logic
        val allOrders = repository.getOrders()
        val userOrders = allOrders.filter { it.userId == userId }

        // Example: 10 points per order
        return (userOrders.size * 10).toString()
    }

    private fun getFCMTokenForTesting() {
        try {
            com.google.firebase.messaging.FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    android.util.Log.d("FCM_HOME", "FCM Token: $token")
                } else {
                    android.util.Log.e("FCM_HOME", "Failed to get FCM token", task.exception)
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("FCM_HOME", "FCM not initialized", e)
        }
    }

    // Handle permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1001 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Notification permission granted
                    android.util.Log.d("HOME", "Notification permission granted")
                } else {
                    // Notification permission denied
                    android.util.Log.d("HOME", "Notification permission denied")
                }
            }
        }
    }
}