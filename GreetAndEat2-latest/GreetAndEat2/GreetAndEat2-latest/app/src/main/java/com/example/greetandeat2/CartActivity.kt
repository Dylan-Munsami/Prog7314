package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.greetandeat2.data.AppDatabase
import com.example.greetandeat2.data.CartItem
import com.example.greetandeat2.repository.OfflineRepository
import com.example.greetandeat2.worker.SyncWorker
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class CartActivity : BaseActivity() {

    private lateinit var adapter: CartAdapter
    private lateinit var auth: FirebaseAuth
    private val cartItems = mutableListOf<CartItem>()
    private lateinit var tvTotal: TextView
    private lateinit var repository: OfflineRepository

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_drawer)

        // Initialize repository
        repository = OfflineRepository(AppDatabase.getInstance(this))

        val frame = findViewById<android.widget.FrameLayout>(R.id.content_frame)
        LayoutInflater.from(this).inflate(R.layout.activity_cart, frame, true)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.your_cart)
        setupDrawer(toolbar)

        auth = FirebaseAuth.getInstance()

        // UI
        val recyclerView = findViewById<RecyclerView>(R.id.rvCart)
        tvTotal = findViewById(R.id.tvCartTotal)
        val btnPay = findViewById<Button>(R.id.btnPay)
        val backButton = findViewById<Button>(R.id.btnBack)

        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CartAdapter(cartItems) { itemToRemove ->
            CoroutineScope(Dispatchers.IO).launch {
                repository.removeFromCart(itemToRemove)
                loadCartItems()
            }
            Toast.makeText(this, getString(R.string.item_removed, itemToRemove.name), Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        // Load local cart items
        loadCartItems()

        // Setup periodic sync
        setupSyncWorker()

        // Pay button behavior
        btnPay.setOnClickListener {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, getString(R.string.cart_empty), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentUser = auth.currentUser
            if (currentUser == null) {
                Toast.makeText(this, getString(R.string.login_required_payment), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🚫 BLOCK payment if offline
            if (!isOnline()) {
                Toast.makeText(this, "You must be online to proceed to payment.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // ✅ Allow payment if online
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putParcelableArrayListExtra("cartItems", ArrayList(cartItems))
            startActivity(intent)
        }
    }

    // 🔍 Check if device has internet
    private fun isOnline(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun loadCartItems() {
        CoroutineScope(Dispatchers.IO).launch {
            val items = repository.getCartItems()
            cartItems.clear()
            cartItems.addAll(items)

            runOnUiThread {
                updateTotal()
                adapter.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun updateTotal() {
        val total = cartItems.sumOf { it.price * it.quantity }
        tvTotal.text = getString(R.string.total, total)
    }

    private fun setupSyncWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncWorkRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            repeatInterval = 15,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(syncWorkRequest)
    }

    private fun syncData() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncWorkRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(syncWorkRequest)
    }

    override fun onResume() {
        super.onResume()
        loadCartItems()
    }
}
