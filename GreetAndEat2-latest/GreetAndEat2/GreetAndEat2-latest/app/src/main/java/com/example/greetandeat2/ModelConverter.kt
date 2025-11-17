package com.example.greetandeat2.utils

import com.example.greetandeat2.MenuItem
import com.example.greetandeat2.data.CartItem

object ModelConverter {

    fun menuItemToCartItem(
        menuItem: MenuItem,
        restaurantId: String,
        restaurantName: String
    ): CartItem {
        return CartItem(
            id = menuItem.id,
            name = menuItem.name,
            price = menuItem.price,
            quantity = menuItem.quantity,
            restaurantId = restaurantId,
            restaurantName = restaurantName,
            imageRes = menuItem.imageRes
        )
    }

    fun cartItemToMenuItem(cartItem: CartItem): MenuItem {
        return MenuItem(
            id = cartItem.id,
            name = cartItem.name,
            price = cartItem.price,
            quantity = cartItem.quantity,
            imageRes = cartItem.imageRes ?: android.R.drawable.ic_menu_gallery
        )
    }
}