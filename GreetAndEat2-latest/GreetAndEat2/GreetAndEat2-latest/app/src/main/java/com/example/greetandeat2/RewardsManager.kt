package com.example.greetandeat2

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

object RewardsManager {
    private const val PREFS_NAME = "rewards_prefs"

    // Generate unique key per user
    private fun getUserKey(context: Context, key: String): String {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "guest"
        return "${uid}_$key"
    }

    // Unlock a reward
    fun unlockReward(context: Context, rewardId: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(getUserKey(context, rewardId), true).apply()
    }

    // Check if a reward is unlocked
    fun isRewardUnlocked(context: Context, rewardId: String): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(getUserKey(context, rewardId), false)
    }

    // Handle daily login streak
    fun handleDailyCheckIn(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "guest"

        val lastLoginKey = "${uid}_last_login_date"
        val streakKey = "${uid}_login_streak"

        val lastLogin = prefs.getString(lastLoginKey, null)
        val streak = prefs.getInt(streakKey, 0)
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())

        if (lastLogin == null || lastLogin != today) {
            val newStreak = if (lastLogin != null && isYesterday(lastLogin, today)) {
                streak + 1
            } else {
                1 // reset streak
            }

            prefs.edit()
                .putString(lastLoginKey, today)
                .putInt(streakKey, newStreak)
                .apply()

            if (newStreak >= 7) {
                unlockReward(context, "daily")
            }
        }
    }

    private fun isYesterday(lastLogin: String, today: String): Boolean {
        val format = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val lastDate = format.parse(lastLogin)
        val todayDate = format.parse(today)
        val diff = (todayDate.time - lastDate.time) / (1000 * 60 * 60 * 24)
        return diff == 1L
    }
}
