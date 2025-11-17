package com.example.greetandeat2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class RewardsAdapter(
    private val rewards: List<Reward>,
    private val onClick: (Reward) -> Unit
) : RecyclerView.Adapter<RewardsAdapter.RewardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reward, parent, false)
        return RewardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
        val reward = rewards[position]
        holder.bind(reward)
        holder.itemView.setOnClickListener {
            onClick(reward)
        }
    }

    override fun getItemCount(): Int = rewards.size

    inner class RewardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.rewardTitle)
        private val description = itemView.findViewById<TextView>(R.id.rewardDescription)
        private val icon = itemView.findViewById<ImageView>(R.id.rewardIcon)

        fun bind(reward: Reward) {
            title.text = reward.title
            description.text = reward.description
            icon.setImageResource(reward.icon)

            // Set background color based on reward level
            val colorRes = when (reward.level) {
                RewardLevel.SILVER -> R.color.silver
                RewardLevel.BRONZE -> R.color.bronze
                RewardLevel.GOLD -> R.color.gold
            }
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, colorRes))
        }
    }
}
