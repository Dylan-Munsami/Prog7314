package com.example.greetandeat2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey(autoGenerate = true) val restaurantId: Int = 0,  // Primary Key
    val name: String,
    val description: String? = null,
    val logoUrl: String? = null,
    val address: String? = null,
    val rating: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val category: String,
    val isOpen: Boolean = true
)
