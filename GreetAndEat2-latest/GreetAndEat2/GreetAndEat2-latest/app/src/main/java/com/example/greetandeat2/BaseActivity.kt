package com.example.greetandeat2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar

open class BaseActivity : AppCompatActivity() {

    protected lateinit var drawerLayout: DrawerLayout
    protected lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setupDrawer(toolbar: Toolbar) {
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        // Hamburger setup
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.open, R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Handle menu item clicks
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_game -> startActivity(Intent(this, FoodDashGameActivity::class.java))
                R.id.nav_home -> startActivity(Intent(this, Home::class.java))
                R.id.nav_menu -> startActivity(Intent(this, MainActivity::class.java))
                R.id.nav_rewards -> startActivity(Intent(this, RewardsActivity::class.java))
                R.id.nav_settings -> startActivity(Intent(this, Setting::class.java))
                R.id.nav_tracking -> startActivity(Intent(this, TrackingActivity::class.java))
                R.id.nav_logout -> {
                    startActivity(Intent(this, Login::class.java))
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}
