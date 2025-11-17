package com.example.greetandeat2.data

import androidx.room.*

@Dao
interface OrderDao {

    @Query("SELECT * FROM orders ORDER BY createdAt DESC")
    suspend fun getAllOrders(): List<LocalOrder>

    @Query("SELECT * FROM orders WHERE isSynced = 0")
    suspend fun getUnsyncedOrders(): List<LocalOrder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: LocalOrder)

    @Update
    suspend fun updateOrder(order: LocalOrder)

    @Query("UPDATE orders SET isSynced = 1 WHERE orderId = :orderId")
    suspend fun markOrderAsSynced(orderId: String)

    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    suspend fun getOrderById(orderId: String): LocalOrder?


    @Query("SELECT * FROM orders WHERE userId = :userId ORDER BY createdAt DESC")
    suspend fun getOrdersForUser(userId: String): List<LocalOrder>
}
