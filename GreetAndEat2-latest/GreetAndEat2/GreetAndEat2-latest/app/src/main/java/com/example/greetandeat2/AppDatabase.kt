package com.example.greetandeat2.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.greetandeat2.data.CartItem
import com.example.greetandeat2.data.LocalOrder

@Database(
    entities = [CartItem::class, LocalOrder::class], // Use LocalOrder here
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "greet_eat_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}