package com.example.greetandeat2.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.greetandeat2.data.AppDatabase
import com.example.greetandeat2.repository.OfflineRepository
import com.example.greetandeat2.utils.ModelConverter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.greetandeat2.data.CartItem
import com.example.greetandeat2.data.LocalOrder
class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    private val repository = OfflineRepository(AppDatabase.getInstance(context))
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override suspend fun doWork(): Result {
        return try {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                syncCartItems(currentUser.uid)
                syncOrders(currentUser.uid)
            }
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Sync failed: ${e.message}")
            Result.retry()
        }
    }

    private suspend fun syncCartItems(userId: String) {
        val unsyncedItems = repository.getUnsyncedCartItems()

        for (item in unsyncedItems) {
            try {
                // Convert CartItem to a map for Firestore
                val itemMap = mapOf(
                    "id" to item.id,
                    "name" to item.name,
                    "price" to item.price,
                    "quantity" to item.quantity,
                    "restaurantId" to item.restaurantId,
                    "restaurantName" to item.restaurantName,
                    "createdAt" to item.createdAt
                )

                firestore.collection("users")
                    .document(userId)
                    .collection("cart")
                    .document(item.id)
                    .set(itemMap)
                    .await()

                repository.markCartItemAsSynced(item.id)
                Log.d("SyncWorker", "Synced cart item: ${item.name}")
            } catch (e: Exception) {
                Log.e("SyncWorker", "Failed to sync cart item ${item.id}: ${e.message}")
            }
        }
    }

    private suspend fun syncOrders(userId: String) {
        val unsyncedOrders = repository.getUnsyncedOrders()

        for (order in unsyncedOrders) {
            try {
                val orderMap = mapOf(
                    "orderId" to order.orderId,
                    "restaurantName" to order.restaurantName,
                    "totalAmount" to order.totalAmount,
                    "items" to order.items,
                    "status" to order.status,
                    "createdAt" to order.createdAt,
                    "userId" to userId
                )

                firestore.collection("users")
                    .document(userId)
                    .collection("orders")
                    .document(order.orderId)
                    .set(orderMap)
                    .await()

                repository.markOrderAsSynced(order.orderId)
                Log.d("SyncWorker", "Synced order: ${order.orderId}")
            } catch (e: Exception) {
                Log.e("SyncWorker", "Failed to sync order ${order.orderId}: ${e.message}")
            }
        }
    }
}