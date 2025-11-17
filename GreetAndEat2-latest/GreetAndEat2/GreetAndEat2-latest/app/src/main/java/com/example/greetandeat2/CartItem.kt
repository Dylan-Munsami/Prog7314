package com.example.greetandeat2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val id: String,
    val name: String,
    val price: Double,
    val quantity: Int,
    val restaurantId: String,
    val restaurantName: String,
    val imageRes: Int? = null,
    val isSynced: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable