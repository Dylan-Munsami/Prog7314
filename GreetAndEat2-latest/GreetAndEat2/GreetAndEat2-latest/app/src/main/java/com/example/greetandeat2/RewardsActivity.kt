package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class RewardsActivity : BaseActivity() {

    private lateinit var rewardsRecycler: RecyclerView
    private lateinit var pointsText: TextView
    private lateinit var subscribeBtn: Button
    private var totalPoints = 0
    private lateinit var rewardsAdapter: RewardsAdapter
    private lateinit var rewardsList: List<Reward>
    private val auth = FirebaseAuth.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rewards)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.rewards_title)  // ✅ Translatable
        setupDrawer(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pointsText = findViewById(R.id.points)
        subscribeBtn = findViewById(R.id.subscribeBtn)
        rewardsRecycler = findViewById(R.id.rewardsRecycler)
        rewardsRecycler.layoutManager = GridLayoutManager(this, 2)

        subscribeBtn.setOnClickListener {
            Toast.makeText(
                this,
                getString(R.string.subscribed_pro),
                Toast.LENGTH_SHORT
            ).show()
        }

        refreshRewards()
    }

    override fun onResume() {
        super.onResume()
        refreshRewards()
    }

    private fun refreshRewards() {
        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(
                this,
                getString(R.string.login_required),  // ✅ Translatable
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(this, Login::class.java))
            finish()
            return
        }

        rewardsList = listOf(
            Reward(
                getString(R.string.first_login_title),
                getString(R.string.first_login_desc),
                100,
                R.drawable.ic_login,
                if (RewardsManager.isRewardUnlocked(this, "first_login")) RewardLevel.GOLD else RewardLevel.SILVER
            ),
            Reward(
                getString(R.string.first_order_title),
                getString(R.string.first_order_desc),
                200,
                R.drawable.ic_order,
                if (RewardsManager.isRewardUnlocked(this, "first_order")) RewardLevel.GOLD else RewardLevel.SILVER
            ),
            Reward(
                getString(R.string.loyal_customer_title),
                getString(R.string.loyal_customer_desc),
                300,
                R.drawable.ic_loyal,
                if (RewardsManager.isRewardUnlocked(this, "loyal")) RewardLevel.GOLD else RewardLevel.SILVER
            ),
            Reward(
                getString(R.string.big_spender_title),
                getString(R.string.big_spender_desc),
                250,
                R.drawable.ic_money,
                if (RewardsManager.isRewardUnlocked(this, "big_spender")) RewardLevel.GOLD else RewardLevel.SILVER
            ),
            Reward(
                getString(R.string.daily_checkin_title),
                getString(R.string.daily_checkin_desc),
                120,
                R.drawable.ic_daily,
                if (RewardsManager.isRewardUnlocked(this, "daily")) RewardLevel.GOLD else RewardLevel.SILVER
            )
        )

        totalPoints = rewardsList.filter { it.level == RewardLevel.GOLD }.sumOf { it.points }
        pointsText.text = getString(R.string.points_label, totalPoints)

        rewardsAdapter = RewardsAdapter(rewardsList) { reward ->
            when (reward.title) {
                getString(R.string.first_login_title) ->
                    Toast.makeText(this, getString(R.string.first_login_bonus), Toast.LENGTH_SHORT).show()
                getString(R.string.first_order_title) ->
                    startActivity(Intent(this, MenuActivity::class.java))
                getString(R.string.loyal_customer_title) ->
                    startActivity(Intent(this, TrackingActivity::class.java))
                getString(R.string.big_spender_title) ->
                    startActivity(Intent(this, CartActivity::class.java))
                getString(R.string.daily_checkin_title) ->
                    Toast.makeText(this, getString(R.string.daily_checkin_msg), Toast.LENGTH_SHORT).show()
            }
        }

        rewardsRecycler.adapter = rewardsAdapter
    }
}
