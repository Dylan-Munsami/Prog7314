package com.example.greetandeat2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuItem(
    val id: String,
    val name: String,
    val price: Double,
    var quantity: Int = 1,
    val imageRes: Int = android.R.drawable.ic_menu_gallery // default placeholder
) : Parcelable

data class Restaurant(
    val id: String,
    val name: String,
    val menu: List<MenuItem>,
    val imageRes: Int = android.R.drawable.ic_menu_gallery // default placeholder
)

enum class OrderStatus {
    PENDING, CONFIRMED, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
}

data class Order(
    val id: String? = null,
    val userId: String,
    val items: List<MenuItem>,
    val total: Double,
    val status: String = "ORDER_RECEIVED",
    val createdAt: String? = null,
    val estimatedDelivery: String? = null,
    val statusHistory: List<StatusUpdate> = emptyList()
)

data class StatusUpdate(
    val status: String,
    val timestamp: String,
    val message: String
)
