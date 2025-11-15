package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.button.MaterialButton
import com.example.greetandeat2.repository.OfflineRepository
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
        repository = OfflineRepository(com.example.greetandeat2.data.AppDatabase.getInstance(this))

        // --- Quick Action Cards ---
        findViewById<CardView>(R.id.cardOrderFood).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        findViewById<CardView>(R.id.cardTrackOrder).setOnClickListener {
            startActivity(Intent(this, TrackingActivity::class.java))
        }
        findViewById<CardView>(R.id.cardRewards).setOnClickListener {
            startActivity(Intent(this, RewardsActivity::class.java))
        }
        findViewById<CardView>(R.id.cardRecentOrders).setOnClickListener {
            startActivity(Intent(this, RecentOrdersActivity::class.java))
        }
        findViewById<CardView>(R.id.cardRecentOrders).setOnClickListener {
            startActivity(Intent(this, RecentOrdersActivity::class.java))
        }

        findViewById<MaterialCardView>(R.id.cardFoodDash).setOnClickListener {
            startActivity(Intent(this, FoodDashGameActivity::class.java))
        }


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
    }

    private fun updateTotalOrders(userId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val allOrders = repository.getOrders()
            val userOrders = allOrders.filter { it.userId == userId }

            tvTotalOrders.text = userOrders.size.toString()

            // Show or hide active order card
            val activeOrderCard = findViewById<CardView>(R.id.activeOrderCard)
            val tvOrderStatus = findViewById<TextView>(R.id.tvOrderStatus)
            val activeOrder = userOrders.lastOrNull() // latest order
            if (activeOrder != null) {
                activeOrderCard.visibility = CardView.VISIBLE
                tvOrderStatus.text = activeOrder.status.capitalize()
            } else {
                activeOrderCard.visibility = CardView.GONE
            }
        }
    }

    private fun updateRewardPoints() {
        val user = auth.currentUser
        tvRewardPoints.text = if (user != null) {
            val rewardsList = listOf(
                "first_login",
                "first_order",
                "loyal",
                "big_spender",
                "daily"
            ).map { key ->
                Reward(
                    title = key,
                    description = "",
                    points = when (key) {
                        "first_login" -> 100
                        "first_order" -> 200
                        "loyal" -> 300
                        "big_spender" -> 250
                        "daily" -> 120
                        else -> 0
                    },
                    icon = 0,
                    level = if (RewardsManager.isRewardUnlocked(this, key)) RewardLevel.GOLD else RewardLevel.SILVER
                )
            }
            rewardsList.filter { it.level == RewardLevel.GOLD }.sumOf { it.points }.toString()
        } else "0"
    }
}
