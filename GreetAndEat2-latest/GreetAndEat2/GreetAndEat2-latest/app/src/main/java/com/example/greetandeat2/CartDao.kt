package com.example.greetandeat2.data

import androidx.room.*

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items ORDER BY createdAt DESC")
    suspend fun getAllCartItems(): List<CartItem>

    @Query("SELECT * FROM cart_items WHERE isSynced = 0")
    suspend fun getUnsyncedItems(): List<CartItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("UPDATE cart_items SET isSynced = 1 WHERE id = :itemId")
    suspend fun markAsSynced(itemId: String)
}