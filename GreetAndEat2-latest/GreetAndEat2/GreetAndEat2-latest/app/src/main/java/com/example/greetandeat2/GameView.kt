package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import kotlin.random.Random

class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), Runnable {

    private var thread: Thread? = null
    private var isPlaying = false
    private var canvas: Canvas? = null
    private var surfaceHolder: SurfaceHolder = holder

    // Game objects
    private var player: Player
    private var obstacles = mutableListOf<Obstacle>()
    private var tips = mutableListOf<Tip>()
    private var score = 0
    private var gameOver = false

    // Game settings
    private var screenWidth = 0
    private var screenHeight = 0
    private var speed = 10f

    // Paints
    private val roadPaint = Paint().apply {
        color = Color.GRAY
    }

    private val playerPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.primaryColor)
    }

    private val obstaclePaint = Paint().apply {
        color = Color.RED
    }

    private val tipPaint = Paint().apply {
        color = Color.YELLOW
    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 50f
        typeface = Typeface.DEFAULT_BOLD
    }

    // Interface to communicate with activity
    interface GameListener {
        fun updateScore(score: Int)
        fun gameOver(score: Int)
    }

    private var gameListener: GameListener? = null

    fun setGameListener(listener: GameListener) {
        this.gameListener = listener
    }

    init {
        player = Player()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w
        screenHeight = h
        player.x = (screenWidth / 4).toFloat()
        player.y = (screenHeight * 0.8f)
    }

    override fun run() {
        while (isPlaying) {
            update()
            draw()
            sleep()
        }
    }

    private fun update() {
        if (gameOver) return

        // Move obstacles
        obstacles.forEach { it.y += speed }
        obstacles.removeAll { it.y > screenHeight }

        // Move tips
        tips.forEach { it.y += speed }
        tips.removeAll { it.y > screenHeight }

        // Generate new obstacles
        if (Random.nextInt(100) < 3) {
            obstacles.add(Obstacle(
                x = Random.nextInt(screenWidth - 100).toFloat(),
                y = -100f,
                width = 80f,
                height = 80f
            ))
        }

        // Generate new tips
        if (Random.nextInt(100) < 2) {
            tips.add(Tip(
                x = Random.nextInt(screenWidth - 50).toFloat(),
                y = -50f,
                width = 40f,
                height = 40f
            ))
        }

        // Check collisions
        checkCollisions()
    }

    private fun checkCollisions() {
        val playerRect = RectF(player.x, player.y, player.x + player.width, player.y + player.height)

        // Check obstacle collisions
        for (obstacle in obstacles) {
            val obstacleRect = RectF(obstacle.x, obstacle.y, obstacle.x + obstacle.width, obstacle.y + obstacle.height)
            if (playerRect.intersect(obstacleRect)) {
                gameOver = true
                gameListener?.gameOver(score)
                return
            }
        }

        // Check tip collections
        val iterator = tips.iterator()
        while (iterator.hasNext()) {
            val tip = iterator.next()
            val tipRect = RectF(tip.x, tip.y, tip.x + tip.width, tip.y + tip.height)
            if (playerRect.intersect(tipRect)) {
                score += 5
                gameListener?.updateScore(score)
                iterator.remove()
            }
        }

        // Increase score for survival
        score++
        if (score % 100 == 0) {
            gameListener?.updateScore(score)
        }
    }

    private fun draw() {
        if (surfaceHolder.surface.isValid) {
            canvas = surfaceHolder.lockCanvas()
            canvas?.let {
                // Draw background
                it.drawColor(Color.DKGRAY)

                // Draw road
                it.drawRect(0f, 0f, screenWidth.toFloat(), screenHeight.toFloat(), roadPaint)

                // Draw lane markers
                val lanePaint = Paint().apply { color = Color.WHITE }
                for (i in 0 until 3) {
                    val x = (screenWidth / 4) * (i + 1)
                    for (y in 0 until screenHeight step 100) {
                        it.drawRect(x.toFloat() - 5f, y.toFloat(), x.toFloat() + 5f, (y + 50).toFloat(), lanePaint)
                    }
                }

                // Draw player (delivery scooter)
                it.drawRect(player.x, player.y, player.x + player.width, player.y + player.height, playerPaint)

                // Draw obstacles (cars)
                obstacles.forEach { obstacle ->
                    it.drawRect(obstacle.x, obstacle.y, obstacle.x + obstacle.width, obstacle.y + obstacle.height, obstaclePaint)
                }

                // Draw tips (coins)
                tips.forEach { tip ->
                    it.drawCircle(tip.x + tip.width/2, tip.y + tip.height/2, tip.width/2, tipPaint)
                }

                // Draw score
                it.drawText("Score: $score", 50f, 100f, textPaint)

                if (gameOver) {
                    textPaint.textSize = 80f
                    it.drawText("GAME OVER", (screenWidth/2 - 200).toFloat(), (screenHeight/2).toFloat(), textPaint)
                    textPaint.textSize = 50f
                }
            }
            surfaceHolder.unlockCanvasAndPost(canvas!!)
        }
    }

    private fun sleep() {
        try {
            Thread.sleep(16) // ~60 FPS
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun pause() {
        isPlaying = false
        thread?.join()
    }

    fun resume() {
        isPlaying = true
        thread = Thread(this)
        thread?.start()
    }

    fun resetGame() {
        score = 0
        gameOver = false
        obstacles.clear()
        tips.clear()
        player.x = (screenWidth / 4).toFloat()
        gameListener?.updateScore(score)
        if (!isPlaying) {
            resume()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                if (!gameOver) {
                    player.x = event.x - player.width / 2
                    // Keep player within screen bounds
                    if (player.x < 0) player.x = 0f
                    if (player.x > screenWidth - player.width) player.x = (screenWidth - player.width).toFloat()
                }
            }
        }
        return true
    }

    inner class Player {
        var x = 0f
        var y = 0f
        val width = 100f
        val height = 150f
    }

    inner class Obstacle(var x: Float, var y: Float, val width: Float, val height: Float)
    inner class Tip(var x: Float, var y: Float, val width: Float, val height: Float)
}