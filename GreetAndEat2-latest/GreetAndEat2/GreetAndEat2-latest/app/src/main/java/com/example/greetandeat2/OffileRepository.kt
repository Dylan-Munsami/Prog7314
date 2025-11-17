package com.example.greetandeat2.repository

import com.example.greetandeat2.data.CartItem
import com.example.greetandeat2.data.LocalOrder
import com.example.greetandeat2.data.AppDatabase

class OfflineRepository(private val database: AppDatabase) {

    private val cartDao = database.cartDao()
    private val orderDao = database.orderDao()

    // --- Cart operations ---
    suspend fun addToCart(cartItem: CartItem) {
        cartDao.insertCartItem(cartItem)
    }

    suspend fun removeFromCart(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }

    suspend fun getCartItems(): List<CartItem> {
        return cartDao.getAllCartItems()
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    suspend fun getUnsyncedCartItems(): List<CartItem> {
        return cartDao.getUnsyncedItems()
    }

    suspend fun markCartItemAsSynced(itemId: String) {
        cartDao.markAsSynced(itemId)
    }

    // --- Order operations ---
    suspend fun placeOrder(order: LocalOrder) {
        orderDao.insertOrder(order)
    }

    suspend fun getOrders(): List<LocalOrder> {
        return orderDao.getAllOrders()
    }

    suspend fun getOrdersForUser(userId: String): List<LocalOrder> { // ✅ New
        return orderDao.getOrdersForUser(userId)
    }

    suspend fun getUnsyncedOrders(): List<LocalOrder> {
        return orderDao.getUnsyncedOrders()
    }

    suspend fun markOrderAsSynced(orderId: String) {
        orderDao.markOrderAsSynced(orderId)
    }

    suspend fun updateOrderStatus(orderId: String, status: String) {
        val order = orderDao.getOrderById(orderId)
        order?.let {
            val updatedOrder = it.copy(status = status)
            orderDao.updateOrder(updatedOrder)
        }
    }
}
