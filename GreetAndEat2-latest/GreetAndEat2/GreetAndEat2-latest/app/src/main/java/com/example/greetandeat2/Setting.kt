package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar

class Setting : BaseActivity() {

    private var isSpinnerInitialized = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_drawer)

        // Inflate the specific layout
        val frame = findViewById<FrameLayout>(R.id.content_frame)
        LayoutInflater.from(this).inflate(R.layout.activity_setting, frame, true)

        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Settings"
        setupDrawer(toolbar)

        // === UI Elements ===
        val editProfileTop = findViewById<TextView>(R.id.editProfile)
        val editProfileButton = findViewById<Button>(R.id.editProfileButton)
        val changePasswordButton = findViewById<Button>(R.id.changePasswordButton)
        val paymentMethodsButton = findViewById<Button>(R.id.managePaymentsButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)
        val themeSwitch = findViewById<Switch>(R.id.themeSwitch)
        val notificationSwitch = findViewById<Switch>(R.id.notificationSwitch)
        val languageSpinner = findViewById<Spinner>(R.id.languageSpinner)
        val userEmail = findViewById<TextView>(R.id.userEmail)

        // Update button texts
        editProfileButton.text = getString(R.string.edit)
        changePasswordButton.text = getString(R.string.change)
        paymentMethodsButton.text = getString(R.string.manage)
        logoutButton.text = getString(R.string.logout)

        // Load User Info
        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val email = prefs.getString("lastUserEmail", "guest@example.com")
        userEmail.text = email
        userEmail.isSelected = true

        // Load Current Theme
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        themeSwitch.isChecked = currentNightMode == Configuration.UI_MODE_NIGHT_YES

        // === Edit Profile ===
        editProfileButton.setOnClickListener {
            Toast.makeText(this, getString(R.string.edit_profile), Toast.LENGTH_SHORT).show()
        }
        editProfileTop.setOnClickListener {
            Toast.makeText(this, getString(R.string.edit_profile), Toast.LENGTH_SHORT).show()
        }

        // === Change Password ===
        changePasswordButton.setOnClickListener {
            Toast.makeText(this, getString(R.string.change_password), Toast.LENGTH_SHORT).show()
        }

        // === Payment Methods (REALISTIC) ===
        paymentMethodsButton.setOnClickListener {
            val intent = Intent(this, PaymentMethodsActivity::class.java)
            startActivity(intent)
        }

        // === Logout ===
        logoutButton.setOnClickListener {
            prefs.edit().clear().apply()
            Toast.makeText(this, "${getString(R.string.logout)} successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // === Dark/Light Mode ===
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // === Notifications Toggle ===
        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "${getString(R.string.notifications)} Enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "${getString(R.string.notifications)} Disabled", Toast.LENGTH_SHORT).show()
            }
        }

        // === Language Selection ===
        val languages = arrayOf("English", "Afrikaans", "isiXhosa")
        languageSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages)
        val sharedPrefLang = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val savedLang = sharedPrefLang.getString("app_lang", "en")
        val position = when (savedLang) {
            "en" -> 0
            "af" -> 1
            "xh" -> 2
            else -> 0
        }
        isSpinnerInitialized = true
        languageSpinner.setSelection(position)

        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                if (isSpinnerInitialized) {
                    when (position) {
                        0 -> setLocale("en")
                        1 -> setLocale("af")
                        2 -> setLocale("xh")
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setLocale(languageCode: String) {
        val sharedPref = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val currentLang = sharedPref.getString("app_lang", "en")
        if (currentLang != languageCode) {
            val locale = java.util.Locale(languageCode)
            java.util.Locale.setDefault(locale)
            val resources = baseContext.resources
            val config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
            with(sharedPref.edit()) {
                putString("app_lang", languageCode)
                apply()
            }
            val intent = Intent(this, Setting::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
