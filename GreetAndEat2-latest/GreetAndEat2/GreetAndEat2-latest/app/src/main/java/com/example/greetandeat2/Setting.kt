package com.example.greetandeat2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

class Setting : BaseActivity() {

    private var isSpinnerInitialized = false
    private val auth = FirebaseAuth.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_drawer)

        // Inflate the specific layout
        val frame = findViewById<FrameLayout>(R.id.content_frame)
        layoutInflater.inflate(R.layout.activity_setting, frame, true)

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
        editProfileButton.setOnClickListener { showEditProfileDialog() }
        editProfileTop.setOnClickListener { showEditProfileDialog() }

        // === Change Password ===
        changePasswordButton.setOnClickListener { showChangePasswordDialog() }

        // === Payment Methods ===
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
            if (isChecked) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        // === Notifications Toggle ===
        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) Toast.makeText(this, "${getString(R.string.notifications)} Enabled", Toast.LENGTH_SHORT).show()
            else Toast.makeText(this, "${getString(R.string.notifications)} Disabled", Toast.LENGTH_SHORT).show()
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
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, pos: Int, id: Long) {
                if (isSpinnerInitialized) {
                    when (pos) {
                        0 -> setLocale("en")
                        1 -> setLocale("af")
                        2 -> setLocale("xh")
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    // === Edit Profile Dialog ===
    private fun showEditProfileDialog() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val input = EditText(this)
        input.hint = "Enter new display name"
        input.setText(currentUser.displayName ?: "")

        AlertDialog.Builder(this)
            .setTitle("Edit Profile")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newName = input.text.toString().trim()
                if (newName.isNotEmpty()) {
                    val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                        .setDisplayName(newName)
                        .build()
                    currentUser.updateProfile(profileUpdates).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // === Change Password Dialog ===
    private fun showChangePasswordDialog() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val oldPassword = EditText(this)
        oldPassword.hint = "Current Password"
        oldPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        val newPassword = EditText(this)
        newPassword.hint = "New Password"
        newPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        layout.addView(oldPassword)
        layout.addView(newPassword)
        layout.setPadding(50, 40, 50, 10)

        AlertDialog.Builder(this)
            .setTitle("Change Password")
            .setView(layout)
            .setPositiveButton("Change") { _, _ ->
                val newPass = newPassword.text.toString()
                if (newPass.length < 6) {
                    Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val email = currentUser.email ?: return@setPositiveButton
                val credential = com.google.firebase.auth.EmailAuthProvider.getCredential(email, oldPassword.text.toString())
                currentUser.reauthenticate(credential).addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        currentUser.updatePassword(newPass).addOnCompleteListener { passTask ->
                            if (passTask.isSuccessful) {
                                Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Failed to change password", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
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
