package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class FoodDashGameActivity : BaseActivity() {

    private lateinit var gameView: GameView
    private lateinit var tvScore: TextView
    private lateinit var tvHighScore: TextView
    private lateinit var btnPlayAgain: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_drawer)

        val frame = findViewById<FrameLayout>(R.id.content_frame)
        layoutInflater.inflate(R.layout.activity_food_dash, frame, true)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.food_dash_game)
        setupDrawer(toolbar)

        tvScore = findViewById(R.id.tvScore)
        tvHighScore = findViewById(R.id.tvHighScore)
        btnPlayAgain = findViewById(R.id.btnPlayAgain)

        gameView = findViewById(R.id.gameView)
        gameView.setGameListener(object : GameView.GameListener {
            override fun updateScore(score: Int) {
                this@FoodDashGameActivity.updateScore(score)
            }

            override fun gameOver(score: Int) {
                this@FoodDashGameActivity.gameOver(score)
            }
        })

        // Load high score
        val prefs = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        val highScore = prefs.getInt("high_score", 0)
        tvHighScore.text = getString(R.string.high_score, highScore)

        btnPlayAgain.setOnClickListener {
            gameView.resetGame()
            btnPlayAgain.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }

    fun updateScore(score: Int) {
        tvScore.text = getString(R.string.score, score)
    }

    fun gameOver(score: Int) {
        val prefs = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        val highScore = prefs.getInt("high_score", 0)

        if (score > highScore) {
            prefs.edit().putInt("high_score", score).apply()
            tvHighScore.text = getString(R.string.high_score, score)
        }

        btnPlayAgain.visibility = View.VISIBLE
    }
}