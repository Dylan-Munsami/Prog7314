package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class TrackingActivity : BaseActivity() {

    private lateinit var tvStatus: TextView
    private lateinit var tvEstimatedTime: TextView
    private lateinit var tvOrderId: TextView
    private lateinit var tvStatusHistory: TextView
    private lateinit var btnRefresh: Button
    private lateinit var btnGoHome: Button

    private var currentStatusIndex = 0
    private val statuses = listOf(
        R.string.order_received,
        R.string.preparing,
        R.string.ready_for_pickup,
        R.string.out_for_delivery,
        R.string.delivered
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_drawer)

        val frame = findViewById<android.widget.FrameLayout>(R.id.content_frame)
        LayoutInflater.from(this).inflate(R.layout.activity_tracking, frame, true)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.tracking)  // ✅ Translated
        setupDrawer(toolbar)

        tvStatus = findViewById(R.id.tvTrackingStatus)
        tvEstimatedTime = findViewById(R.id.tvEstimatedTime)
        tvOrderId = findViewById(R.id.tvOrderId)
        tvStatusHistory = findViewById(R.id.tvStatusHistory)
        btnRefresh = findViewById(R.id.btnRefresh)
        btnGoHome = findViewById(R.id.btnGoHome)

        btnRefresh.text = getString(R.string.refresh)  // ✅ Translated
        btnGoHome.text = getString(R.string.go_home)  // ✅ Translated

        btnRefresh.setOnClickListener { progressToNextStatus() }
        btnGoHome.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        tvStatus.text = getString(R.string.status, getString(statuses[0]))  // ✅ Translated
        tvEstimatedTime.text = getString(R.string.estimated_delivery)  // ✅ Translated
        tvOrderId.text = getString(R.string.order_id)  // ✅ Translated
        tvStatusHistory.text = getString(R.string.order_summary_placeholder)  // ✅ Translated
    }

    private fun progressToNextStatus() {
        if (currentStatusIndex >= statuses.size - 1) return
        currentStatusIndex++
        val newStatus = getString(statuses[currentStatusIndex])
        tvStatus.text = getString(R.string.status, newStatus)  // ✅ Translated
        Toast.makeText(this, getString(R.string.status_updated, newStatus), Toast.LENGTH_SHORT).show()  // ✅ Translated
    }
}