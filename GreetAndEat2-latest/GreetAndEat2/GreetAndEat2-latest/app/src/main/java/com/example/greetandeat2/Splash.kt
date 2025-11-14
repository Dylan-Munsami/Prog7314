package com.example.greetandeat2

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

class Splash : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 2000 // reduced to 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_splash)

        val splashImage = findViewById<ImageView>(R.id.splash_image)
        val welcomeText = findViewById<TextView>(R.id.welcome_text)

        // Gradient text effect (async after layout)
        welcomeText.post {
            val textShader = LinearGradient(
                0f, 0f, welcomeText.width.toFloat(), welcomeText.textSize,
                intArrayOf(0xFFFFD200.toInt(), 0xFFDD2476.toInt()), // gold → pink
                null,
                Shader.TileMode.CLAMP
            )
            welcomeText.paint.shader = textShader
        }

        // Fade in logo (no scaling)
        splashImage.alpha = 0f
        splashImage.animate()
            .alpha(1f)
            .setDuration(1000)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()

        // Fade in welcome text
        welcomeText.alpha = 0f
        welcomeText.animate()
            .alpha(1f)
            .setDuration(1000)
            .setStartDelay(600)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()

        // Transition after short delay
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, Login::class.java))
            finish()
        }, SPLASH_DELAY)
    }
}
