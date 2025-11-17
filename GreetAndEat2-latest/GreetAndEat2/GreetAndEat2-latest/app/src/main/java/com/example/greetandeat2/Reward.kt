package com.example.greetandeat2

import androidx.annotation.DrawableRes

enum class RewardLevel { SILVER, BRONZE, GOLD }

data class Reward(
    val title: String,
    val description: String,
    val points: Int,
    @DrawableRes val icon: Int,
    var level: RewardLevel = RewardLevel.SILVER, // starts as silver
    var completed: Boolean = false // track if user has completed it
)
