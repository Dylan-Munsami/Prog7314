package com.example.greetandeat2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "orders")
data class LocalOrder(
    @PrimaryKey val orderId: String,
    val restaurantName: String,
    val totalAmount: Double,
    val items: String, // JSON string of cart items
    val status: String = "pending",
    val createdAt: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false,
    val userId: String? = null
) : Parcelable